public class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }

    public void deposit(long amount) { if (amount > 0) balance += amount; }
    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }
}

