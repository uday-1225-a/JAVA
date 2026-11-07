import model.Account;
import model.Customer;
import repository.Repository;

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 13: Generic Repository & Reflection on @Id ===");

        // Step 5: Repository<Account>
        System.out.println("\n--- Step 1 & 5: Repository<Account> Operations ---");
        Repository<Account> accountRepo = new Repository<>();

        Account a1 = new Account("AC0001", "Riya Patel", 5000);
        Account a2 = new Account("AC0002", "Uday Desai", 12000);
        Account a3 = new Account("AC0003", "Aarav Shah", 8500);

        // Save multiple using @SafeVarargs saveAll
        accountRepo.saveAll(a1, a2, a3);
        System.out.println("Saved " + accountRepo.count() + " accounts into Generic Repository.");

        // Fetch one by ID
        Account fetched = accountRepo.findById("AC0002");
        System.out.println("Fetched by ID 'AC0002': " + fetched);

        // Find all
        System.out.println("\nAll accounts in repository:");
        for (Account a : accountRepo.findAll()) {
            System.out.println(" -> " + a);
        }

        // Reusing Repository<Customer> without any changes!
        System.out.println("\n--- Reusing the EXACT same Repository class for Customer ---");
        Repository<Customer> customerRepo = new Repository<>();
        Customer c1 = new Customer("CUST101", "Riya Patel", "riya@gmail.com");
        Customer c2 = new Customer("CUST102", "Uday Desai", "uday@charusat.edu.in");

        customerRepo.saveAll(c1, c2);
        System.out.println("Stored " + customerRepo.count() + " customers.");
        Customer custFound = customerRepo.findById("CUST101");
        System.out.println("Fetched customer by @Id: " + custFound);

        // Step 4: Bounded type parameter average method
        System.out.println("\n--- Step 4: Generic Bounded Method average() ---");
        Long[] accountBalances = { 5000L, 12000L, 8500L };
        Double[] interestRates = { 4.0, 3.5, 7.0, 6.5 };

        System.out.printf("Average Account Balance: ₹%.2f%n", Repository.average(accountBalances));
        System.out.printf("Average Interest Rate  : %.2f%%%n", Repository.average(interestRates));
    }
}

