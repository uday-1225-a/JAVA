import java.util.HashMap;
import java.util.Map;

class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(String message, int shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addStock(String item, int qty) {
        inventory.put(item, inventory.getOrDefault(item, 0) + qty);
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Requested quantity (" + qty + ") must be greater than zero.");
        }

        int available = inventory.getOrDefault(item, 0);
        if (available < qty) {
            int shortfall = qty - available;
            throw new OutOfStockException("Not enough stock for '" + item + "'. Available: " + available + ", Requested: " + qty, shortfall);
        }

        inventory.put(item, available - qty);
        System.out.println("Issued " + qty + " units of '" + item + "'. Remaining stock: " + (available - qty));
    }
}

public class StockIssue {
    static class Request {
        String item;
        int qty;
        Request(String item, int qty) {
            this.item = item;
            this.qty = qty;
        }
    }

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.addStock("Laptop", 5);
        warehouse.addStock("Mouse", 12);
        warehouse.addStock("Monitor", 2);

        Request[] requests = {
            new Request("Laptop", 3),     // Valid
            new Request("Mouse", -2),     // InvalidQuantityException
            new Request("Monitor", 4),    // OutOfStockException (shortfall: 2)
            new Request("Laptop", 3),     // OutOfStockException (shortfall: 1)
            new Request("Keyboard", 1)    // OutOfStockException (shortfall: 1)
        };

        System.out.println("=== Warehouse Stock Issue Processing ===");
        for (Request req : requests) {
            try {
                System.out.println("\nProcessing request: " + req.qty + "x " + req.item);
                warehouse.issue(req.item, req.qty);
            } catch (InvalidQuantityException e) {
                System.out.println("[Handled Error] " + e.getMessage());
            } catch (OutOfStockException e) {
                System.out.println("[Handled Error] " + e.getMessage() + " | Shortfall: " + e.getShortfall() + " units");
            }
        }
        System.out.println("\nAll requests processed without crashing the warehouse system.");
    }
}

