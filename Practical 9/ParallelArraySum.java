import java.util.concurrent.atomic.AtomicLong;

public class ParallelArraySum {
    private static final int SIZE = 1_000_000;
    private static final int NUM_THREADS = 4;

    private static long unsafeSum = 0;
    private static long syncSum = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        int[] data = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            data[i] = 1; // Expected sum = 1,000,000
        }

        int chunkSize = SIZE / NUM_THREADS;

        System.out.println("Array size: " + SIZE + " (Expected Total = " + SIZE + ")");

        // 1. Unsafe: Shared variable with no synchronization (shows wrong result)
        unsafeSum = 0;
        Thread[] threads1 = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            final int start = i * chunkSize;
            final int end = (i == NUM_THREADS - 1) ? SIZE : start + chunkSize;
            threads1[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    unsafeSum += data[j]; // Data race
                }
            });
            threads1[i].start();
        }
        for (Thread t : threads1) t.join();
        System.out.println("1. Unsafe parallel sum: " + unsafeSum + " (Wrong result due to race condition)");

        // 2. Fix 1: Synchronized block per addition
        syncSum = 0;
        long startSync = System.nanoTime();
        Thread[] threads2 = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            final int start = i * chunkSize;
            final int end = (i == NUM_THREADS - 1) ? SIZE : start + chunkSize;
            threads2[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    synchronized (lock) {
                        syncSum += data[j];
                    }
                }
            });
            threads2[i].start();
        }
        for (Thread t : threads2) t.join();
        long syncDuration = System.nanoTime() - startSync;
        System.out.println("2. Fix 1 (Synchronized): " + syncSum + " | Time: " + (syncDuration / 1_000_000.0) + " ms");

        // 3. Fix 2: Local partial sums per thread (Zero lock contention)
        long startPartial = System.nanoTime();
        long[] partialSums = new long[NUM_THREADS];
        Thread[] threads3 = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadIdx = i;
            final int start = i * chunkSize;
            final int end = (i == NUM_THREADS - 1) ? SIZE : start + chunkSize;
            threads3[i] = new Thread(() -> {
                long local = 0;
                for (int j = start; j < end; j++) {
                    local += data[j];
                }
                partialSums[threadIdx] = local;
            });
            threads3[i].start();
        }
        for (Thread t : threads3) t.join();

        long totalPartialSum = 0;
        for (long s : partialSums) totalPartialSum += s;
        long partialDuration = System.nanoTime() - startPartial;

        System.out.println("3. Fix 2 (Local Partial Sums): " + totalPartialSum + " | Time: " + (partialDuration / 1_000_000.0) + " ms");
        System.out.println("\nConclusion: Fix 2 is significantly faster because threads work independently without lock contention.");
    }
}

