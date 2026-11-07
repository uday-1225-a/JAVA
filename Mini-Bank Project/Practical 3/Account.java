import java.util.Objects;

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

    // Default constructor generating sequential ID
    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = Math.max(0, balance);
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Constructor with custom account number (used for testing equality)
    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = Math.max(0, balance);
        this.active = true;
    }

    public void deposit(long amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }

    // Step 1: Override toString()
    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    // Step 2: Override equals() and hashCode() based on accountNumber
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}

