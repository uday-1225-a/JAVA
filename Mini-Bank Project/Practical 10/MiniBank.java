public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== MiniBank Practical 10: Thread Pools, Wait/Notify & Deadlock Resolution ===");

        Account acc1 = new Account("AC0001", "Riya Patel", 10000);
        Account acc2 = new Account("AC0002", "Uday Desai", 8000);

        // Step 1 & 2: TransactionProcessor with Thread Pool
        System.out.println("\n--- Steps 1 & 2: Submitting Tasks to ThreadPool TransactionProcessor ---");
        TransactionProcessor processor = new TransactionProcessor();

        for (int i = 1; i <= 6; i++) {
            final int taskNum = i;
            processor.submit(() -> {
                if (taskNum % 2 == 0) {
                    acc1.deposit(500);
                } else {
                    acc1.withdraw(300);
                }
            });
        }

        processor.stop();
        System.out.println("Thread pool completed all transactions. acc1 balance: ₹" + acc1.getBalance());

        // Step 3: Producer-Consumer
        System.out.println("\n--- Step 3: Producer-Consumer with wait() & notify() ---");
        ProducerConsumerQueue pcQueue = new ProducerConsumerQueue(2);
        Thread producer = new Thread(() -> {
            try {
                pcQueue.produce("TXN-101: Deposit ₹1000");
                pcQueue.produce("TXN-102: Withdraw ₹500");
                pcQueue.produce("TXN-103: Transfer ₹200");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(100);
                pcQueue.consume();
                pcQueue.consume();
                pcQueue.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        // Step 4: Deadlock prevention via consistent lock order
        System.out.println("\n--- Step 4: Demonstrating Deadlock Prevention (Smaller ID Lock First) ---");
        Thread t1 = new Thread(() -> DeadlockDemo.transferDeadlockFixed(acc1, acc2, 1000, "Thread-A->B"));
        Thread t2 = new Thread(() -> DeadlockDemo.transferDeadlockFixed(acc2, acc1, 500, "Thread-B->A"));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("\nFinal Balances After Safe Concurrent Transfers:");
        System.out.println(acc1);
        System.out.println(acc2);
    }
}

