import java.util.Scanner;

// Step 3: Record for bank metadata
record BankInfo(String name, String branch) {}

// Step 4: Enum for menu options
enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

// Step 2: Public class MiniBank with main method
public class MiniBank {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Print header using BankInfo record
        BankInfo bankInfo = new BankInfo("MiniBank India", "CHARUSAT Campus Branch");
        System.out.println("==================================================");
        System.out.println(" Welcome to " + bankInfo.name() + " (" + bankInfo.branch() + ")");
        System.out.println("==================================================");

        boolean running = true;

        while (running) {
            // Step 5: Display numbered menu
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Please select an option (1-5): ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.next(); // Consume invalid input
                continue;
            }

            int choice = sc.nextInt();

            // Map choice number to MenuOption
            MenuOption selectedOption = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("Invalid choice! Please select between 1 and 5.");
                continue;
            }

            // Step 6: Switch expression for placeholder / actions
            String response = switch (selectedOption) {
                case OPEN_ACCOUNT -> "Open Account — to be implemented in a later lab.";
                case DEPOSIT      -> "Deposit — to be implemented in a later lab.";
                case WITHDRAW     -> "Withdraw — to be implemented in a later lab.";
                case TRANSFER     -> "Transfer — to be implemented in a later lab.";
                case EXIT         -> "Thank you for banking with MiniBank. Goodbye!";
            };

            System.out.println("\n-> " + response);

            if (selectedOption == MenuOption.EXIT) {
                running = false;
            }
        }

        sc.close();
    }
}

