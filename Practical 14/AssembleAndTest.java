import java.util.ArrayList;
import java.util.List;

// Model Layer
class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " - ₹" + price;
    }
}

// Service Layer
class ProductService {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product p) {
        if (p == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        for (Product existing : products) {
            if (existing.getId().equalsIgnoreCase(p.getId())) {
                throw new IllegalArgumentException("Product with ID " + p.getId() + " already exists.");
            }
        }
        products.add(p);
    }

    public int getProductCount() {
        return products.size();
    }

    public Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Product not found with ID: " + id);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}

// Layered Application & Test Suite
public class AssembleAndTest {

    // Simple Unit Test Assertions
    public static void testAddingIncreasesCount() {
        ProductService service = new ProductService();
        int initialCount = service.getProductCount();

        service.addProduct(new Product("P101", "Wireless Mouse", 599.0));
        service.addProduct(new Product("P102", "Mechanical Keyboard", 2499.0));

        if (service.getProductCount() == initialCount + 2) {
            System.out.println("PASS: testAddingIncreasesCount passed successfully!");
        } else {
            System.err.println("FAIL: testAddingIncreasesCount failed!");
        }
    }

    public static void testInvalidProductThrows() {
        boolean exceptionThrown = false;
        try {
            new Product("P999", "Defective Item", -50.0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }

        if (exceptionThrown) {
            System.out.println("PASS: testInvalidProductThrows passed successfully (Negative price rejected)!");
        } else {
            System.err.println("FAIL: testInvalidProductThrows failed!");
        }
    }

    public static void testFindNonExistentThrows() {
        ProductService service = new ProductService();
        service.addProduct(new Product("P101", "USB Cable", 150.0));

        boolean exceptionThrown = false;
        try {
            service.findById("NON_EXISTING_ID");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }

        if (exceptionThrown) {
            System.out.println("PASS: testFindNonExistentThrows passed successfully (NotFound properly thrown)!");
        } else {
            System.err.println("FAIL: testFindNonExistentThrows failed!");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Running Unit Tests on Layered App ===");
        testAddingIncreasesCount();
        testInvalidProductThrows();
        testFindNonExistentThrows();

        System.out.println("\n=== Interactive Service Demonstration ===");
        ProductService service = new ProductService();
        service.addProduct(new Product("P01", "Headphones", 1299.0));
        service.addProduct(new Product("P02", "Webcam", 1899.0));

        System.out.println("Total products in catalog: " + service.getProductCount());
        for (Product p : service.getAllProducts()) {
            System.out.println(" - " + p);
        }
    }
}

