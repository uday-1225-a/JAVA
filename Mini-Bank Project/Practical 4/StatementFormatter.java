public class StatementFormatter {
    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("        MINIBANK ACCOUNT STATEMENT       \n");
        sb.append("========================================\n");
        sb.append("Account Number : ").append(account.getAccountNumber()).append("\n");
        sb.append("Account Holder : ").append(account.getOwnerName()).append("\n");
        sb.append("Account Status : ").append(account.isActive() ? "ACTIVE" : "INACTIVE").append("\n");
        sb.append("Current Balance: ₹").append(account.getBalance()).append("\n");
        sb.append("========================================\n");
        sb.append("Generated on system runtime.\n");
        return sb.toString();
    }
}

