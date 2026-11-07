public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 5: Polymorphism & Account Hierarchy ===");

        // Step 6: Create objects of three account types in an Account[] array
        Account[] accounts = {
            new SavingsAccount("SA1001", "Riya Patel", 10000, 1000),
            new CurrentAccount("CA2001", "Apex Tech Solutions", 20000, 5000),
            new FixedDepositAccount("FD3001", "Dinesh Dave", 100000)
        };

        System.out.println("\n--- Polymorphic Interest Rate Display ---");
        for (Account acc : accounts) {
            System.out.printf("Account: %-8s | Type: %-20s | Interest Rate: %.1f%%%n",
                acc.getAccountNumber(), acc.getClass().getSimpleName(), acc.interestRate());
        }

        System.out.println("\n--- Pattern Matching with instanceof for Special Handling ---");
        for (Account acc : accounts) {
            if (acc instanceof SavingsAccount sa) {
                System.out.println("[Special Handle - Savings]: " + sa.getAccountNumber() + 
                                   " has minimum required balance of ₹" + sa.getMinBalance());
            } else if (acc instanceof CurrentAccount ca) {
                System.out.println("[Special Handle - Current]: " + ca.getAccountNumber() + 
                                   " has overdraft limit protection up to ₹" + ca.getOverdraftLimit());
            } else if (acc instanceof FixedDepositAccount) {
                System.out.println("[Special Handle - FixedDeposit]: " + acc.getAccountNumber() + 
                                   " is a locked term deposit with maximum yield.");
            }
        }

        System.out.println("\n--- Testing Polymorphic Withdrawals ---");
        System.out.println("Attempting ₹9500 withdrawal on Savings Account (Balance: 10000, Min: 1000):");
        accounts[0].withdraw(9500); // Should fail (leaves 500 < 1000)

        System.out.println("\nAttempting ₹23000 withdrawal on Current Account (Balance: 20000, Overdraft: 5000):");
        accounts[1].withdraw(23000); // Should succeed (falls to -3000 >= -5000)

        System.out.println("\nAttempting ₹1000 withdrawal on Fixed Deposit Account:");
        accounts[2].withdraw(1000); // Should fail (locked)
    }
}

