import java.util.Scanner;

@FunctionalInterface
interface DiscountRule {
    double apply(double price);
}

public class DiscountEngine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double[] prices = { 100.0, 250.0, 500.0, 1200.0, 50.0 };

        System.out.println("Select a Discount Rule to apply to prices:");
        System.out.println("1. Flat 10% Festival Discount");
        System.out.println("2. Flat ₹50 Off (Minimum price ₹50)");
        System.out.println("3. 25% Clearance Sale");
        System.out.println("4. Premium Member 30% Off");
        System.out.print("Enter choice (1-4): ");

        int choice = sc.nextInt();
        DiscountRule rule;

        switch (choice) {
            case 1 -> rule = price -> price * 0.90;
            case 2 -> rule = price -> Math.max(0, price - 50);
            case 3 -> rule = price -> price * 0.75;
            case 4 -> rule = price -> price * 0.70;
            default -> {
                System.out.println("Invalid choice. Applying no discount.");
                rule = price -> price;
            }
        }

        System.out.println("\n--- Original vs Discounted Prices ---");
        for (double p : prices) {
            double discounted = rule.apply(p);
            System.out.printf("Original: ₹%.2f -> Discounted: ₹%.2f%n", p, discounted);
        }

        sc.close();
    }
}

