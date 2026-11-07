public class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 0;

    private static synchronized String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    // Constructor with opening balance
    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = Math.max(0, balance);
        this.active = true;
    }

    // Constructor with zero opening balance using constructor chaining
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Deposit method
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount + " to " + accountNumber + ". New Balance: ₹" + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Withdraw method with balance check
    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn ₹" + amount + " from " + accountNumber + ". Remaining Balance: ₹" + balance);
            return true;
        } else {
            System.out.println("Withdrawal of ₹" + amount + " failed for " + accountNumber + "! Insufficient balance or invalid amount.");
            return false;
        }
    }

    // Getters (no setter for balance to ensure encapsulation)
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

