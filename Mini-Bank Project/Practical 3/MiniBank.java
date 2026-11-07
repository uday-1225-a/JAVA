public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 3: Object Methods & Nested Classes ===");

        // Step 3: Customer with static nested Address
        Customer.Address addr = new Customer.Address("104, Campus Road", "Anand", "388120");
        Customer cust1 = new Customer("Riya", "riya@charusat.edu.in", "9876543210", addr);
        System.out.println("Created Customer: " + cust1);

        // Step 4: Clone the customer
        try {
            Customer custCloned = (Customer) cust1.clone();
            System.out.println("Cloned Customer:  " + custCloned);
            System.out.println("Are customer objects different in memory? " + (cust1 != custCloned));
            System.out.println("Are addresses different objects? " + (cust1.getAddress() != custCloned.getAddress()));
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not supported: " + e.getMessage());
        }

        // Step 1: Create accounts and verify toString()
        Account a1 = new Account("AC0001", "Riya", 4000);
        Account a2 = new Account("AC0001", "Riya Patel", 6000); // Same account number, different instance/balance
        Account a3 = new Account("AC0002", "Uday", 8000);

        System.out.println("\n--- Step 1: Readable toString() Output ---");
        System.out.println("Account 1: " + a1);
        System.out.println("Account 2: " + a2);
        System.out.println("Account 3: " + a3);

        // Step 2: Compare accounts using equals()
        System.out.println("\n--- Step 2: Account Equality via equals() ---");
        System.out.println("a1.equals(a2) (Same Account Number 'AC0001'): " + a1.equals(a2));
        System.out.println("a1.equals(a3) (Different Account Numbers):   " + a1.equals(a3));

        // Step 5: Check type using instanceof
        System.out.println("\n--- Step 5: Type Checking with instanceof ---");
        Object obj1 = a1;
        Object obj2 = cust1;

        if (obj1 instanceof Account) {
            System.out.println("obj1 is indeed an instance of Account.");
        }
        if (obj2 instanceof Customer) {
            System.out.println("obj2 is indeed an instance of Customer.");
        }
    }
}

