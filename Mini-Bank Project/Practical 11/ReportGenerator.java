import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    public static void generateReport(Path logsDir, Path reportFile) throws IOException {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        List<String> reportLines = new ArrayList<>();

        reportLines.add("==================================================");
        reportLines.add("             MINIBANK DAILY AUDIT REPORT          ");
        reportLines.add("==================================================");
        reportLines.add(String.format("%-18s | %-10s | %s", "Log File", "Size(B)", "Last Modified"));
        reportLines.add("--------------------------------------------------");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir, "*.log")) {
            for (Path entry : stream) {
                BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                reportLines.add(String.format("%-18s | %-10d | %s", 
                    entry.getFileName(), attrs.size(), attrs.lastModifiedTime()));

                try (BufferedReader reader = Files.newBufferedReader(entry)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");
                        if (parts.length >= 3) {
                            String type = parts[0].toUpperCase();
                            try {
                                long amt = Long.parseLong(parts[2]);
                                if (type.contains("DEPOSIT")) {
                                    totalDeposits += amt;
                                } else if (type.contains("WITHDRAW")) {
                                    totalWithdrawals += amt;
                                }
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            }
        }

        reportLines.add("==================================================");
        reportLines.add("Total Deposits    : ₹" + totalDeposits);
        reportLines.add("Total Withdrawals : ₹" + totalWithdrawals);
        reportLines.add("Net Fund Flow     : ₹" + (totalDeposits - totalWithdrawals));
        reportLines.add("==================================================");

        Files.write(reportFile, reportLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("[ReportGenerator] Audit report generated at: " + reportFile.getFileName());
    }
}

