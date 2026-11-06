class DatabaseConnection implements AutoCloseable {
    private String connectionName;

    public DatabaseConnection(String name) {
        this.connectionName = name;
        System.out.println("[Resource] Opened connection to: " + connectionName);
    }

    public void executeQuery(String sql) throws Exception {
        System.out.println("[Resource] Executing query: " + sql);
        if (sql.contains("INVALID")) {
            throw new RuntimeException("SQL Execution Failed on: " + sql);
        }
    }

    @Override
    public void close() {
        System.out.println("[Resource] Closing connection: " + connectionName + " (close() invoked automatically)");
    }
}

public class AutoCloseableDemo {
    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Normal Execution ===");
        try (DatabaseConnection conn = new DatabaseConnection("ProductionDB")) {
            conn.executeQuery("SELECT * FROM users");
            System.out.println("[Try Block] Operation finished successfully.");
        } catch (Exception e) {
            System.out.println("[Catch Block] Caught exception: " + e.getMessage());
        }

        System.out.println("\n=== Test Case 2: Exception Thrown Inside Try-With-Resources ===");
        try (DatabaseConnection conn = new DatabaseConnection("BackupDB")) {
            conn.executeQuery("SELECT * FROM valid_table");
            conn.executeQuery("DROP TABLE INVALID_COMMAND");
            System.out.println("[Try Block] This line will never execute.");
        } catch (Exception e) {
            System.out.println("[Catch Block] Verified: Original exception caught -> " + e.getMessage());
        }

        System.out.println("\nResource cleanup and exception reporting confirmed.");
    }
}

