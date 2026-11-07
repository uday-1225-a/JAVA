import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MiniBank {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("=== MiniBank Practical 11: Serialization & NIO Audit Reporting ===");

        Path dataDir = Paths.get("data");
        Path logsDir = Paths.get("logs");
        if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
        if (!Files.exists(logsDir)) Files.createDirectories(logsDir);

        Path accountsFile = dataDir.resolve("accounts.dat");
        Path logFile1 = logsDir.resolve("counter1.log");
        Path logFile2 = logsDir.resolve("counter2.log");
        Path reportFile = dataDir.resolve("end_of_day_report.txt");

        // Step 1: Create accounts to persist
        Account[] originalAccounts = {
            new Account("AC0001", "Riya Patel", 7500),
            new Account("AC0002", "Uday Desai", 12000),
            new Account("AC0003", "Aarav Shah", 3000)
        };

        System.out.println("\n--- Step 2: Saving & Reloading Accounts via Serialization ---");
        StatePersister.save(originalAccounts, accountsFile);

        Account[] reloadedAccounts = StatePersister.load(accountsFile);
        System.out.println("Confirming reloaded accounts data integrity:");
        for (Account a : reloadedAccounts) {
            System.out.println(" -> " + a);
        }

        // Step 3: Write transaction logs
        System.out.println("\n--- Step 3: Appending Transaction Logs with NIO ---");
        TransactionLog.append(logFile1, "DEPOSIT AC0001 500");
        TransactionLog.append(logFile1, "WITHDRAW AC0002 200");
        TransactionLog.append(logFile2, "DEPOSIT AC0003 1500");
        TransactionLog.append(logFile2, "WITHDRAW AC0001 100");
        System.out.println("Successfully logged transactions across multiple counters.");

        // Step 4 & 5: Generate and print audit report
        System.out.println("\n--- Step 4 & 5: Generating End-of-Day NIO Report ---");
        ReportGenerator.generateReport(logsDir, reportFile);

        System.out.println("\nReport File Contents:");
        for (String line : Files.readAllLines(reportFile)) {
            System.out.println(line);
        }

        // Clean up generated data files
        Files.deleteIfExists(logFile1);
        Files.deleteIfExists(logFile2);
        Files.deleteIfExists(logsDir);
        Files.deleteIfExists(accountsFile);
        Files.deleteIfExists(reportFile);
        Files.deleteIfExists(dataDir);
    }
}

