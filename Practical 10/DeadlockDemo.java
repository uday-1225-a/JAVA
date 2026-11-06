class BankAccount {
    private final String id;
    private double balance;

    public BankAccount(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void transferTo(BankAccount target, double amount) {
        this.balance -= amount;
        target.balance += amount;
    }
}

public class DeadlockDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount acc1 = new BankAccount("ACC-001", 1000);
        BankAccount acc2 = new BankAccount("ACC-002", 1000);

        System.out.println("=== Deadlock Scenario & Consistent Lock Order Solution ===");
        System.out.println("Scenario: Two threads transferring money concurrently between ACC-001 and ACC-002.");
        System.out.println("\nProblem (Opposite Lock Order):");
        System.out.println("- Thread 1 locks ACC-001 and waits for ACC-002");
        System.out.println("- Thread 2 locks ACC-002 and waits for ACC-001");
        System.out.println("=> Circular wait leads to DEADLOCK (system hangs forever).\n");

        System.out.println("Solution (Consistent Lock Ordering):");
        System.out.println("- Always lock the account with smaller ID first, regardless of transfer direction.\n");

        // Safe transfer method with consistent lock ordering
        Runnable transferSafe1 = () -> safeTransfer(acc1, acc2, 100, "Thread-1");
        Runnable transferSafe2 = () -> safeTransfer(acc2, acc1, 200, "Thread-2");

        Thread t1 = new Thread(transferSafe1);
        Thread t2 = new Thread(transferSafe2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\nTransfers completed safely without any deadlock!");
        System.out.println("ACC-001 Final Balance: " + acc1.getBalance());
        System.out.println("ACC-002 Final Balance: " + acc2.getBalance());
    }

    public static void safeTransfer(BankAccount from, BankAccount to, double amount, String threadName) {
        // Enforce consistent locking order based on account ID
        BankAccount firstLock = from.getId().compareTo(to.getId()) < 0 ? from : to;
        BankAccount secondLock = from.getId().compareTo(to.getId()) < 0 ? to : from;

        System.out.println("[" + threadName + "] Attempting lock on " + firstLock.getId());
        synchronized (firstLock) {
            System.out.println("[" + threadName + "] Acquired lock on " + firstLock.getId());

            try { Thread.sleep(50); } catch (InterruptedException e) {}

            System.out.println("[" + threadName + "] Attempting lock on " + secondLock.getId());
            synchronized (secondLock) {
                System.out.println("[" + threadName + "] Acquired lock on " + secondLock.getId());
                from.transferTo(to, amount);
                System.out.println("[" + threadName + "] Transferred ₹" + amount + " from " + from.getId() + " to " + to.getId());
            }
        }
    }
}

