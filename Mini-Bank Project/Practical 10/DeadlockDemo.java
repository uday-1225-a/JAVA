public class DeadlockDemo {
    public static void transferDeadlockFixed(Account from, Account to, long amount, String threadName) {
        // Enforce consistent locking order: smaller accountNumber locked first
        Account firstLock = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account secondLock = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? to : from;

        System.out.println("[" + threadName + "] Acquiring lock on " + firstLock.getAccountNumber());
        synchronized (firstLock) {
            System.out.println("[" + threadName + "] Acquired lock on " + firstLock.getAccountNumber());

            try { Thread.sleep(50); } catch (InterruptedException e) {}

            System.out.println("[" + threadName + "] Acquiring lock on " + secondLock.getAccountNumber());
            synchronized (secondLock) {
                System.out.println("[" + threadName + "] Acquired lock on " + secondLock.getAccountNumber());
                if (from.withdraw(amount)) {
                    to.deposit(amount);
                    System.out.println("[" + threadName + "] Transferred ₹" + amount + " from " + from.getAccountNumber() + " to " + to.getAccountNumber());
                }
            }
        }
    }
}

