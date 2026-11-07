public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 2: Encapsulation & Account Operations ===");

        // Step 1: Create sample Customer
        Customer c1 = new Customer("Riya Patel", "riya@gmail.com", "9876543210");
        System.out.println("Registered Customer: " + c1.getName() + " (ID: " + c1.getCustomerId() + ")");

        // Step 5: Create three Account objects in an Account[] array
        Account[] accounts = {
            new Account("Riya Patel", 5000),
            new Account("Aarav Shah", 2500),
            new Account("Pooja Mehta") // Opened with zero balance
        };

        System.out.println("\n--- Initial Account Status ---");
        for (Account acc : accounts) {
            System.out.println("Account: " + acc.getAccountNumber() + " | Owner: " + acc.getOwnerName() + " | Balance: ₹" + acc.getBalance());
        }

        System.out.println("\n--- Performing Transactions ---");
        // Deposit to first account
        accounts[0].deposit(2000);

        // Successful withdrawal from first account
        accounts[0].withdraw(3000);

        // Failed withdrawal (exceeding balance)
        accounts[0].withdraw(10000);

        // Transactions on other accounts
        accounts[1].deposit(1500);
        accounts[2].deposit(1000);
        accounts[2].withdraw(500);

        System.out.println("\n--- Final Account Balances ---");
        for (Account acc : accounts) {
            System.out.println("Account: " + acc.getAccountNumber() + " | Owner: " + acc.getOwnerName() + " | Final Balance: ₹" + acc.getBalance());
        }
    }
}

