import model.*;
import service.WithdrawRule;

// Requirement 5: Use one static import
import static util.BankConstants.BANK_NAME;
import static util.BankConstants.CURRENCY_SYMBOL;
import static util.BankConstants.DAILY_ATM_LIMIT;

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== Welcome to " + BANK_NAME + " (Practical 6: Interfaces & Packaging) ===");
        System.out.println("Configured Daily ATM Limit: " + CURRENCY_SYMBOL + DAILY_ATM_LIMIT);

        SavingsAccount sa = new SavingsAccount("SA001", "Riya Patel", 50000, 2000);

        // Step 2: Test InterestBearing default method
        System.out.println("\n--- Step 2: Testing Interface Default Method ---");
        System.out.println("Account: " + sa.getAccountNumber() + " | Balance: " + CURRENCY_SYMBOL + sa.getBalance());
        System.out.println("Applicable Interest Rate: " + sa.interestRate() + "%");
        System.out.println("Calculated Yearly Interest: " + CURRENCY_SYMBOL + sa.yearlyInterest());

        // Step 3: WithdrawRule using Anonymous Class
        System.out.println("\n--- Step 3: WithdrawRule via Anonymous Class ---");
        WithdrawRule atmRuleAnonymous = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                // Rule: amount must not exceed daily ATM limit
                return amount <= DAILY_ATM_LIMIT && account.canWithdraw(amount);
            }
        };
        long testAmt1 = 20000;
        System.out.println("Is withdrawal of " + CURRENCY_SYMBOL + testAmt1 + " allowed by ATM rule? " +
                           atmRuleAnonymous.allow(sa, testAmt1));

        // Step 3: WithdrawRule using Lambda Expression
        System.out.println("\n--- Step 3: WithdrawRule via Lambda Expression ---");
        WithdrawRule fraudDetectionRuleLambda = (account, amount) -> {
            // Rule: allow only if withdrawal does not drain more than 70% of current balance at once
            return amount <= (account.getBalance() * 0.70) && account.canWithdraw(amount);
        };
        long testAmt2 = 40000;
        System.out.println("Is withdrawal of " + CURRENCY_SYMBOL + testAmt2 + " allowed by fraud detection rule? " +
                           fraudDetectionRuleLambda.allow(sa, testAmt2));

        // Step 4: Marker Interface check
        System.out.println("\n--- Step 4: Checking Marker Interface (Premium) ---");
        if (sa instanceof Premium) {
            System.out.println("Account " + sa.getAccountNumber() + " is marked as PREMIUM! Eligible for priority lounge & reduced charges.");
        }
    }
}

