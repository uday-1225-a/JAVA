import model.Account;
import util.AnnotationValidator;

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 7: Custom Metadata & Reflection Validation ===");

        // Step 5: Account with negative balance
        System.out.println("\n--- Testing Invalid Account (Negative Balance & Exceeded Name Length) ---");
        Account invalidAccount = new Account("AC0001", "Riya Patel From Gujarat India", -100);
        System.out.println("Testing Account: " + invalidAccount);

        String[] errors = AnnotationValidator.validate(invalidAccount);
        System.out.println("Validation Errors Found (" + errors.length + "):");
        for (String err : errors) {
            System.out.println(" - " + err);
        }

        // Test valid account
        System.out.println("\n--- Testing Valid Account ---");
        Account validAccount = new Account("AC0002", "Uday Desai", 5000);
        System.out.println("Testing Account: " + validAccount);

        String[] validErrors = AnnotationValidator.validate(validAccount);
        if (validErrors.length == 0) {
            System.out.println("No validation errors found! All constraints satisfied.");
        } else {
            for (String err : validErrors) {
                System.out.println(" - " + err);
            }
        }
    }
}

