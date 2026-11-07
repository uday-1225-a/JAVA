public class Account {
    private final String accountNumber;
    private final String ownerName;
    private long unsafeBalance = 0;
    private long safeBalance = 0;
    private final Object lock = new Object();

    public Account(String accountNumber, String ownerName) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getUnsafeBalance() { return unsafeBalance; }
    public long getSafeBalance() { return safeBalance; }

    // 1. Unsynchronized deposit (causes race condition)
    public void depositUnsafe(long amount) {
        long current = unsafeBalance;
        // Small delay to simulate work and increase likelihood of thread preemption
        try {
            Thread.sleep(0, 500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        unsafeBalance = current + amount;
    }

    // 2. Synchronized deposit (thread-safe)
    public synchronized void deposit(long amount) {
        safeBalance += amount;
    }

    // 2. Synchronized withdraw (thread-safe)
    public synchronized boolean withdraw(long amount) {
        if (amount > 0 && safeBalance >= amount) {
            safeBalance -= amount;
            return true;
        }
        return false;
    }
}

