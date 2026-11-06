import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        String[] loginLogs = {
            "alice", "bob", "charlie", "alice", "david", "bob", "emma", "frank", "alice"
        };

        System.out.println("Log Entries: " + Arrays.toString(loginLogs));

        // 1. HashSet: fast lookup and counting unique elements
        Set<String> hashSet = new HashSet<>(Arrays.asList(loginLogs));

        // 2. LinkedHashSet: preserves the exact first-seen order
        Set<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(loginLogs));

        System.out.println("\nTotal login events: " + loginLogs.length);
        System.out.println("Unique visitor count (from HashSet): " + hashSet.size());
        System.out.println("All distinct visitors in order of arrival (LinkedHashSet): " + linkedHashSet);

        // Print the first three distinct names
        System.out.println("\nFirst three distinct visitors seen:");
        int count = 0;
        for (String user : linkedHashSet) {
            System.out.println((count + 1) + ". " + user);
            count++;
            if (count == 3) {
                break;
            }
        }
    }
}

