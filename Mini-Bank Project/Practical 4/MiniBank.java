public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 4: Input Validation & Command Parsing ===");

        // Step 1: Test each Validator with one valid and one invalid input
        System.out.println("\n--- Step 1: Testing Regular Expression Validators ---");
        System.out.println("Mobile '9876543210' valid? " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile '12345' valid?      " + Validator.isValidMobile("12345"));

        System.out.println("Email 'user@bank.com' valid? " + Validator.isValidEmail("user@bank.com"));
        System.out.println("Email 'invalid-email' valid? " + Validator.isValidEmail("invalid-email"));

        System.out.println("PAN 'ABCDE1234F' valid?    " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN 'ABC1234' valid?       " + Validator.isValidPan("ABC1234"));

        System.out.println("IFSC 'SBIN0001234' valid?  " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC 'SBIN123' valid?      " + Validator.isValidIfsc("SBIN123"));

        // Step 2 & 3: Parse sample commands
        System.out.println("\n--- Step 2 & 3: Parsing Commands into Record ---");
        String inputLine = "DEPOSIT AC0001 500";
        Command cmd = CommandParser.parse(inputLine);
        System.out.println("Parsed Command String: \"" + inputLine + "\"");
        System.out.println("Transaction Type : " + cmd.type());
        System.out.println("Account Number   : " + cmd.accountNumber());
        System.out.println("Amount           : ₹" + cmd.amount());

        // Step 4: Statement Formatter test
        System.out.println("\n--- Step 4: Generating Statement with StringBuilder ---");
        Account acc = new Account("AC0001", "Riya Patel", 4500);
        String statement = StatementFormatter.buildStatement(acc);
        System.out.println(statement);
    }
}

