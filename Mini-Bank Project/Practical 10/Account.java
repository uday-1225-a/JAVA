public class Account {
    private final String accountNumber;
    private final String ownerName;
    private long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public synchronized long getBalance() { return balance; }

    public synchronized void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("[" + Thread.currentThread().getName() + "] Deposited ₹" + amount + " to " + accountNumber + ". Balance: ₹" + balance);
        }
    }

    public synchronized boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("[" + Thread.currentThread().getName() + "] Withdrawn ₹" + amount + " from " + accountNumber + ". Balance: ₹" + balance);
            return true;
        }
        System.out.println("[" + Thread.currentThread().getName() + "] Insufficient balance for withdrawal of ₹" + amount + " on " + accountNumber);
        return false;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | ₹" + balance;
    }
}

