class UnsyncedCounter {
    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class SyncedCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class CounterRace {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 10;
        int incrementsPerThread = 1000;
        int expectedTotal = numThreads * incrementsPerThread;

        System.out.println("Expected final count: " + expectedTotal);

        // --- Part 1: Without Synchronization ---
        UnsyncedCounter unsafeCounter = new UnsyncedCounter();
        Thread[] unsafeThreads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    unsafeCounter.increment();
                }
            });
            unsafeThreads[i].start();
        }

        for (Thread t : unsafeThreads) {
            t.join();
        }

        System.out.println("Result without synchronization: " + unsafeCounter.getCount() + 
            (unsafeCounter.getCount() < expectedTotal ? " (Race condition occurred!)" : ""));

        // --- Part 2: With Synchronization ---
        SyncedCounter safeCounter = new SyncedCounter();
        Thread[] safeThreads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    safeCounter.increment();
                }
            });
            safeThreads[i].start();
        }

        for (Thread t : safeThreads) {
            t.join();
        }

        System.out.println("Result WITH synchronization:    " + safeCounter.getCount() + " (Exactly correct!)");
    }
}

