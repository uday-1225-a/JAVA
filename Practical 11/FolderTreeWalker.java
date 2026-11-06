import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FolderTreeWalker {
    public static void main(String[] args) {
        Path startDir = Paths.get("test_directory");
        Path reportFile = Paths.get("folder_report.txt");

        try {
            // Setup dummy folder structure for testing
            Files.createDirectories(startDir.resolve("subfolder1"));
            Files.createDirectories(startDir.resolve("subfolder2"));
            Files.writeString(startDir.resolve("readme.txt"), "Welcome to project!");
            Files.writeString(startDir.resolve("subfolder1/data.csv"), "id,name\n1,alpha\n2,beta");
            Files.writeString(startDir.resolve("subfolder2/config.json"), "{\"env\": \"dev\"}");

            System.out.println("=== Walking Folder Tree with Java NIO ===");
            List<String> reportLines = new ArrayList<>();
            reportLines.add("================ FOLDER INVENTORY REPORT ================");
            reportLines.add(String.format("%-30s | %-12s | %s", "File Name", "Size (Bytes)", "Last Modified Time"));
            reportLines.add("---------------------------------------------------------");

            try (Stream<Path> stream = Files.walk(startDir)) {
                stream.filter(Files::isRegularFile).forEach(filePath -> {
                    try {
                        BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                        String line = String.format("%-30s | %-12d | %s", 
                            filePath.getFileName(), attrs.size(), attrs.lastModifiedTime());
                        reportLines.add(line);
                        System.out.println(line);
                    } catch (IOException e) {
                        System.err.println("Cannot read attributes for: " + filePath);
                    }
                });
            }

            // Write report to file
            Files.write(reportFile, reportLines);
            System.out.println("\nSummary successfully written to: " + reportFile.toAbsolutePath());

            // Clean up test directories and files
            Files.deleteIfExists(reportFile);
            Files.deleteIfExists(startDir.resolve("readme.txt"));
            Files.deleteIfExists(startDir.resolve("subfolder1/data.csv"));
            Files.deleteIfExists(startDir.resolve("subfolder2/config.json"));
            Files.deleteIfExists(startDir.resolve("subfolder1"));
            Files.deleteIfExists(startDir.resolve("subfolder2"));
            Files.deleteIfExists(startDir);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

