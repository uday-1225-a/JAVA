import exception.BankException;
import model.Account;
import model.CurrentAccount;
import model.SavingsAccount;
import repository.Repository;
import service.BankService;
import util.StatePersister;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MiniBank {
    private static final Path DATA_FILE = Paths.get("minibank_accounts.dat");

    // Step 4: Unit Test Suite
    public static void runSelfTests(BankService service, Repository<Account> repo) {
        System.out.println("\n==============================================");
        System.out.println("       RUNNING AUTOMATED UNIT TESTS           ");
        System.out.println("==============================================");

        // Test 1: A deposit increases the balance
        try {
            long prevBal = repo.findById("SA0001").getBalance();
            service.deposit("SA0001", 1000);
            long newBal = repo.findById("SA0001").getBalance();
            if (newBal == prevBal + 1000) {
                System.out.println("[TEST 1 PASSED]: Deposit successfully increases account balance.");
            } else {
                System.err.println("[TEST 1 FAILED]: Deposit balance mismatch.");
            }
        } catch (Exception e) {
            System.err.println("[TEST 1 FAILED]: Exception: " + e.getMessage());
        }

        // Test 2: A withdrawal over the limit throws an exception
        try {
            service.withdraw("SA0001", 999999);
            System.err.println("[TEST 2 FAILED]: Overdraft withdrawal did not throw exception!");
        } catch (BankException e) {
            System.out.println("[TEST 2 PASSED]: Withdrawal over limit threw expected exception: " + e.getMessage());
        }

        // Test 3: Many concurrent deposits keep the balance correct
        try {
            int threads = 10;
            int depositsPerThread = 500;
            long amount = 1;
            Account testAcc = new Account("TEST01", "Concurrency Test", 0);
            repo.save(testAcc);

            ExecutorService pool = Executors.newFixedThreadPool(4);
            for (int i = 0; i < threads; i++) {
                pool.execute(() -> {
                    for (int j = 0; j < depositsPerThread; j++) {
                        try {
                            testAcc.deposit(amount);
                        } catch (Exception ignored) {}
                    }
                });
            }
            pool.shutdown();
            pool.awaitTermination(5, TimeUnit.SECONDS);

            long expected = threads * depositsPerThread * amount;
            if (testAcc.getBalance() == expected) {
                System.out.println("[TEST 3 PASSED]: Concurrent deposits achieved exact balance: ₹" + testAcc.getBalance());
            } else {
                System.err.println("[TEST 3 FAILED]: Race condition! Expected: " + expected + ", Got: " + testAcc.getBalance());
            }
            repo.delete("TEST01");
        } catch (Exception e) {
            System.err.println("[TEST 3 FAILED]: " + e.getMessage());
        }

        System.out.println("==============================================\n");
    }

    public static void main(String[] args) {
        Repository<Account> repository = new Repository<>();
        BankService service = new BankService(repository);

        // Step 2: Load saved accounts on start-up
        try {
            List<Account> loaded = StatePersister.load(DATA_FILE);
            for (Account a : loaded) {
                repository.save(a);
            }
            if (loaded.isEmpty()) {
                // Initialize default demo accounts
                repository.save(new SavingsAccount("SA0001", "Riya Patel", 10000, 1000));
                repository.save(new CurrentAccount("CA0001", "Uday Enterprises", 25000, 10000));
            }
            System.out.println("Loaded " + repository.count() + " accounts from storage.");
        } catch (Exception e) {
            System.out.println("Initialized fresh repository: " + e.getMessage());
        }

        // Run automated tests
        runSelfTests(service, repository);

        // Interactive Console Menu
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("==================================================");
        System.out.println("        WELCOME TO MINIBANK FINAL APPLICATION     ");
        System.out.println("==================================================");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Print Statement");
            System.out.println("6. List All Accounts");
            System.out.println("7. Exit & Save State");
            System.out.print("Enter choice (1-7): ");

            if (!sc.hasNextInt()) {
                sc.next();
                continue;
            }

            int choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Account Number (e.g. SA0002): ");
                        String accNo = sc.next();
                        System.out.print("Enter Account Holder Name: ");
                        String name = sc.next();
                        System.out.print("Enter Opening Balance: ₹");
                        long bal = sc.nextLong();
                        System.out.print("Enter Minimum Balance: ₹");
                        long minBal = sc.nextLong();
                        service.openAccount(accNo, name, bal, minBal);
                        System.out.println("Account created successfully!");
                    }
                    case 2 -> {
                        System.out.print("Enter Account Number: ");
                        String accNo = sc.next();
                        System.out.print("Enter Deposit Amount: ₹");
                        long amt = sc.nextLong();
                        service.deposit(accNo, amt);
                        System.out.println("Deposit completed!");
                    }
                    case 3 -> {
                        System.out.print("Enter Account Number: ");
                        String accNo = sc.next();
                        System.out.print("Enter Withdrawal Amount: ₹");
                        long amt = sc.nextLong();
                        service.withdraw(accNo, amt);
                        System.out.println("Withdrawal completed!");
                    }
                    case 4 -> {
                        System.out.print("Enter From Account: ");
                        String from = sc.next();
                        System.out.print("Enter To Account: ");
                        String to = sc.next();
                        System.out.print("Enter Transfer Amount: ₹");
                        long amt = sc.nextLong();
                        service.transfer(from, to, amt);
                        System.out.println("Transfer completed successfully!");
                    }
                    case 5 -> {
                        System.out.print("Enter Account Number: ");
                        String accNo = sc.next();
                        System.out.println(service.statement(accNo));
                    }
                    case 6 -> {
                        System.out.println("\n=== ALL REGISTERED ACCOUNTS ===");
                        for (Account a : service.listAccounts()) {
                            System.out.println(a);
                        }
                    }
                    case 7 -> {
                        System.out.println("Saving accounts to persistent storage...");
                        StatePersister.save(service.listAccounts(), DATA_FILE);
                        System.out.println("State saved. Thank you for using MiniBank!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please choose between 1 and 7.");
                }
            } catch (Exception e) {
                System.out.println("[Error]: " + e.getMessage());
            }
        }
        sc.close();
    }
}

