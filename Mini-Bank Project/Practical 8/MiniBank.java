import exception.*;
import model.Account;

// Step 4: Small AutoCloseable resource for banking session
class BankSession implements AutoCloseable {
    private final String sessionToken;

    public BankSession(String token) {
        this.sessionToken = token;
        System.out.println("[Session] Secure Bank Session started. Token: " + sessionToken);
    }

    public void logActivity(String action) {
        System.out.println("[Session Log] Action: " + action);
    }

    @Override
    public void close() {
        System.out.println("[Session] Secure Bank Session cleanly terminated (AutoCloseable).");
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 8: Fault-Tolerant Exception Architecture ===");

        Account a1 = new Account("AC0001", "Riya Patel", 1000);
        Account a2 = new Account("AC0002", "Uday Desai", 500);

        // Step 4: try-with-resources demonstration
        System.out.println("\n--- Step 4: Try-With-Resources with AutoCloseable BankSession ---");
        try (BankSession session = new BankSession("BKS-99482")) {
            session.logActivity("Checking balance for " + a1.getAccountNumber());
            System.out.println("Account Status: " + a1);
        }

        // Test 1: Successful deposit & invalid deposit
        System.out.println("\n--- Testing Deposit Validations ---");
        try {
            a1.deposit(2000);
            a1.deposit(-500); // Throws InvalidAmountException
        } catch (InvalidAmountException e) {
            System.out.println("Caught Expected Error: " + e.getMessage());
        }

        // Test 2: Withdrawal with InsufficientFundsException carrying shortfall
        System.out.println("\n--- Testing Withdrawal with Shortfall (Expected: short by 2000) ---");
        try {
            // Current balance is 3000, attempting 5000 withdrawal
            a1.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: " + e.getMessage());
            System.out.println("Shortfall Amount: ₹" + e.getShortfall());
        } catch (InvalidAmountException e) {
            System.out.println("Caught InvalidAmountException: " + e.getMessage());
        } finally {
            System.out.println("[Finally Block]: Balance verification check -> Account Balance is still ₹" + a1.getBalance());
        }

        // Test 3: Account Transfer and propagation
        System.out.println("\n--- Testing Account Transfer ---");
        try {
            a1.transfer(a2, 1000); // Successful
            a1.transfer(a2, 50000); // Fails (insufficient funds)
        } catch (BankException e) {
            System.out.println("Transfer Failed Safely at Top-Level Catch: " + e.getMessage());
        }

        System.out.println("\nFinal State:");
        System.out.println(a1);
        System.out.println(a2);
    }
}

