import java.util.*;

public class AccountStore {
    // Step 1: HashMap keyed by accountNumber
    private final Map<String, Account> accounts = new HashMap<>();

    // Step 2: Transaction history in ArrayList
    private final List<String> transactionHistory = new ArrayList<>();

    public void add(Account account) {
        if (account != null) {
            accounts.put(account.getAccountNumber(), account);
            transactionHistory.add("Account Added: " + account.getAccountNumber() + " (" + account.getOwnerName() + ")");
        }
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public void logTransaction(String entry) {
        transactionHistory.add(entry);
    }

    public List<String> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    // Step 3: Returns accounts sorted by balance descending using Comparator
    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> sortedList = new ArrayList<>(accounts.values());
        sortedList.sort(new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                // Highest balance first
                return Long.compare(a2.getBalance(), a1.getBalance());
            }
        });
        return sortedList;
    }
}

