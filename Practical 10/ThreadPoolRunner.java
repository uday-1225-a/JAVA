import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {
    public static void main(String[] args) {
        int poolSize = 3;
        int totalTasks = 10;

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        System.out.println("Submitting " + totalTasks + " tasks to a fixed thread pool of " + poolSize + " threads...\n");

        for (int i = 1; i <= totalTasks; i++) {
            final int taskId = i;
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Task #" + taskId + " [STARTED] by " + threadName);
                try {
                    // Simulate short work
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task #" + taskId + " [FINISHED] by " + threadName);
            });
        }

        // Initiate shutdown
        executor.shutdown();

        try {
            boolean completed = executor.awaitTermination(5, TimeUnit.SECONDS);
            if (completed) {
                System.out.println("\nAll tasks completed successfully! Note how only 3 threads were reused.");
            } else {
                System.out.println("\nTimed out waiting for tasks to finish.");
            }
        } catch (InterruptedException e) {
            System.out.println("Execution interrupted.");
        }
    }
}

