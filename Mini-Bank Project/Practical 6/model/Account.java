package model;

public abstract class Account implements Transactable, InterestBearing {
    protected final String accountNumber;
    protected String ownerName;
    protected long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    
    @Override
    public long getBalance() { return balance; }

    @Override
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount + " to " + accountNumber + ". New Balance: ₹" + balance);
        }
    }

    public abstract boolean canWithdraw(long amount);

    @Override
    public boolean withdraw(long amount) {
        if (amount > 0 && canWithdraw(amount)) {
            balance -= amount;
            System.out.println("Withdrawn ₹" + amount + " from " + accountNumber + ". Balance: ₹" + balance);
            return true;
        }
        System.out.println("Withdrawal rejected for " + accountNumber);
        return false;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | ₹" + balance;
    }
}

