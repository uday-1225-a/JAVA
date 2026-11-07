public class CommandParser {
    public static Command parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Command line cannot be empty.");
        }

        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid command format. Expected: <TYPE> <ACCOUNT_NO> <AMOUNT>");
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown transaction type: " + parts[0]);
        }

        String accountNumber = parts[1];
        long amount;
        try {
            amount = Long.parseLong(parts[2]);
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a valid number.");
        }

        return new Command(type, accountNumber, amount);
    }
}

