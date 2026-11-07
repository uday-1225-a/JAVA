import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 12: Collections Framework & Sorting ===");

        AccountStore store = new AccountStore();

        // Step 6: Add accounts with balances 5000, 2000, and 9000
        store.add(new Account("AC0001", "Riya Patel", 5000));
        store.add(new Account("AC0002", "Uday Desai", 2000));
        store.add(new Account("AC0003", "Aarav Shah", 9000));
        store.add(new Account("AC0004", "Pooja Mehta", 15000));

        // Step 1: Look-up by account number
        System.out.println("\n--- Fast Key-Based Lookup (HashMap) ---");
        Account fetched = store.get("AC0002");
        System.out.println("Looked up AC0002: " + fetched);

        // Step 6: Print sorted by balance (Highest first via Comparator)
        System.out.println("\n--- Accounts Sorted by Balance (Highest First) ---");
        System.out.printf("%-8s | %-15s | %s%n", "Acc No", "Owner", "Balance");
        System.out.println("----------------------------------------");
        for (Account a : store.sortedByBalance()) {
            System.out.println(a);
        }

        // Step 4: Natural sorting via Comparable<Account> (Sorted by Account Number)
        System.out.println("\n--- Natural Ordering via Comparable (Account Number) ---");
        var naturalList = store.getAll();
        naturalList.sort(null); // uses compareTo()
        for (Account a : naturalList) {
            System.out.println(a);
        }

        // Step 5: ConcurrentHashMap demonstration for thread safety
        System.out.println("\n--- Step 5: ConcurrentHashMap for Multi-threaded Safety ---");
        Map<String, Account> concurrentStore = new ConcurrentHashMap<>();
        concurrentStore.put(fetched.getAccountNumber(), fetched);
        System.out.println("Concurrent store holding " + concurrentStore.size() + " thread-safe account entry.");
    }
}

