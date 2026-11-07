public abstract class Account {
    protected final String accountNumber;
    protected String ownerName;
    protected long balance;
    protected boolean active;

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

    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount + " to " + accountNumber + ". New Balance: ₹" + balance);
        }
    }

    // Abstract methods to be implemented by subclasses
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    public boolean withdraw(long amount) {
        if (amount > 0 && canWithdraw(amount)) {
            balance -= amount;
            System.out.println("Withdrawn ₹" + amount + " from " + accountNumber + ". Remaining Balance: ₹" + balance);
            return true;
        } else {
            System.out.println("Withdrawal of ₹" + amount + " rejected for " + accountNumber + " by account policy.");
            return false;
        }
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | ₹" + balance + " | " + getClass().getSimpleName();
    }
}

