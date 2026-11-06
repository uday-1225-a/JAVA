import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class PersistentProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private double price;

    public PersistentProduct(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentProduct)) return false;
        PersistentProduct that = (PersistentProduct) o;
        return Double.compare(that.price, price) == 0 &&
               Objects.equals(id, that.id) &&
               Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", price=" + price + "]";
    }
}

class ProductRepository {
    public static void saveToFile(List<PersistentProduct> list, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<PersistentProduct> loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<PersistentProduct>) ois.readObject();
        }
    }
}

public class PersistentAppTest {
    public static void testSaveAndReloadUnchanged() {
        String testFile = "product_store.dat";
        List<PersistentProduct> originalList = new ArrayList<>();
        originalList.add(new PersistentProduct("ITM01", "Gaming Monitor", 14999.0));
        originalList.add(new PersistentProduct("ITM02", "Ergonomic Chair", 8499.0));

        System.out.println("Original Products:");
        for (PersistentProduct p : originalList) {
            System.out.println(" - " + p);
        }

        try {
            // Step 1: Save
            ProductRepository.saveToFile(originalList, testFile);

            // Step 2: Reload
            List<PersistentProduct> reloadedList = ProductRepository.loadFromFile(testFile);

            System.out.println("\nReloaded Products:");
            for (PersistentProduct p : reloadedList) {
                System.out.println(" - " + p);
            }

            // Step 3: Verify assertions
            boolean sizeMatch = originalList.size() == reloadedList.size();
            boolean contentMatch = originalList.equals(reloadedList);

            if (sizeMatch && contentMatch) {
                System.out.println("\n[TEST PASSED]: Data successfully saved and reloaded completely unchanged!");
            } else {
                System.err.println("\n[TEST FAILED]: Reloaded data does not match original data.");
            }

        } catch (Exception e) {
            System.err.println("Persistence Test Failed with Exception: " + e.getMessage());
        } finally {
            new File(testFile).delete();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Layered App Persistence & State Integrity ===");
        testSaveAndReloadUnchanged();
    }
}

