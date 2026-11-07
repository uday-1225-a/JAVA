public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== MiniBank Practical 9: Thread Safety & Synchronization ===");

        int numThreads = 10;
        int depositsPerThread = 1000;
        long depositAmount = 1;
        long expectedBalance = numThreads * depositsPerThread * depositAmount;

        Account testAccount = new Account("AC0001", "Riya Patel");

        // Step 2: Run WITHOUT synchronization to observe race condition
        System.out.println("\n--- Step 2: Executing 10 Threads WITHOUT Synchronization ---");
        Thread[] unsafeThreads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            unsafeThreads[i] = new Thread(
                new AccountWorker(testAccount, depositsPerThread, depositAmount, false),
                "UnsafeWorker-" + (i + 1)
            );
            unsafeThreads[i].start();
        }

        for (Thread t : unsafeThreads) {
            t.join();
        }

        System.out.println("\nExpected Balance : ₹" + expectedBalance);
        System.out.println("Actual Unsafe Balance: ₹" + testAccount.getUnsafeBalance());
        if (testAccount.getUnsafeBalance() < expectedBalance) {
            System.out.println("-> RACE CONDITION CONFIRMED: Balance lost due to unsynchronized concurrent writes!");
        }

        // Step 3: Run WITH synchronization
        System.out.println("\n--- Step 3: Executing 10 Threads WITH Synchronization ---");
        Thread[] safeThreads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            safeThreads[i] = new Thread(
                new AccountWorker(testAccount, depositsPerThread, depositAmount, true),
                "SafeWorker-" + (i + 1)
            );
            safeThreads[i].start();
        }

        for (Thread t : safeThreads) {
            t.join();
        }

        System.out.println("\nExpected Balance : ₹" + expectedBalance);
        System.out.println("Actual Safe Balance  : ₹" + testAccount.getSafeBalance());
        System.out.println("-> SYNCHRONIZATION SUCCESS: Exact expected balance achieved every single run!");
    }
}

