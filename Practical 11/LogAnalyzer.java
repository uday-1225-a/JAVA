import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) {
        String keyword = "ERROR";
        Path logDir = Paths.get("sample_logs");

        try {
            // Create sample logs directory and files for demonstration
            if (!Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }

            Path file1 = logDir.resolve("auth.log");
            Path file2 = logDir.resolve("payment.log");
            Path file3 = logDir.resolve("system.log");

            Files.write(file1, Arrays.asList(
                "10:00:01 INFO User alice logged in",
                "10:02:15 ERROR Authentication failed for user root",
                "10:05:00 INFO User bob logged out"
            ));

            Files.write(file2, Arrays.asList(
                "11:15:30 INFO Payment initiated for order #101",
                "11:15:35 ERROR Gateway timeout on payment #101",
                "11:16:00 ERROR Card declined for order #102",
                "11:18:22 INFO Payment succeeded for order #103"
            ));

            Files.write(file3, Arrays.asList(
                "12:00:00 INFO System health check OK",
                "12:05:10 INFO Memory usage within limits"
            ));

            Path[] logFiles = { file1, file2, file3 };
            int totalLinesAllFiles = 0;
            int totalKeywordMatches = 0;

            System.out.println("=== Log Analyzer (NIO Path & Files) ===");
            System.out.println("Keyword to search: '" + keyword + "'\n");

            for (Path path : logFiles) {
                BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                long fileSize = attrs.size();

                List<String> lines = Files.readAllLines(path);
                int fileLines = lines.size();
                int matchesInFile = 0;

                for (String line : lines) {
                    if (line.contains(keyword)) {
                        matchesInFile++;
                    }
                }

                totalLinesAllFiles += fileLines;
                totalKeywordMatches += matchesInFile;

                System.out.printf("File: %-15s | Size: %4d bytes | Lines: %2d | '%s' Matches: %d%n",
                    path.getFileName(), fileSize, fileLines, keyword, matchesInFile);
            }

            System.out.println("-----------------------------------------------------------------");
            System.out.println("Total lines scanned across all files: " + totalLinesAllFiles);
            System.out.println("Total lines containing '" + keyword + "': " + totalKeywordMatches);

            // Clean up files after demo
            for (Path p : logFiles) Files.deleteIfExists(p);
            Files.deleteIfExists(logDir);

        } catch (IOException e) {
            System.out.println("File I/O error: " + e.getMessage());
        }
    }
}

