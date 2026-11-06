import java.util.LinkedList;
import java.util.Queue;

class SharedBuffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait(); // Wait until consumer frees space
        }
        queue.add(item);
        System.out.println("[PRODUCED] " + item + " (Buffer size: " + queue.size() + ")");
        notifyAll(); // Wake up consumer
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Wait until producer adds item
        }
        int item = queue.poll();
        System.out.println("  [CONSUMED] " + item + " (Buffer size: " + queue.size() + ")");
        notifyAll(); // Wake up producer
        return item;
    }
}

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        SharedBuffer buffer = new SharedBuffer(3); // Small capacity buffer
        int totalItems = 10;

        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.produce(i);
                    Thread.sleep(50); // Simulating production time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.consume();
                    Thread.sleep(100); // Consumer slightly slower
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        System.out.println("=== Producer-Consumer Coordination with wait() & notify() ===");
        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        System.out.println("\nSuccessfully produced and consumed all " + totalItems + " items in order with none lost.");
    }
}

