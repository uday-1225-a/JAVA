import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerQueue {
    private final Queue<String> buffer = new LinkedList<>();
    private final int capacity;

    public ProducerConsumerQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(String transaction) throws InterruptedException {
        while (buffer.size() == capacity) {
            wait();
        }
        buffer.add(transaction);
        System.out.println("[Producer] Pushed: " + transaction + " (Buffer size: " + buffer.size() + ")");
        notifyAll();
    }

    public synchronized String consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        String transaction = buffer.poll();
        System.out.println("  [Consumer] Processed: " + transaction + " (Buffer size: " + buffer.size() + ")");
        notifyAll();
        return transaction;
    }
}

