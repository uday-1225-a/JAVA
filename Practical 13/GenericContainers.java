class Box<T> {
    private T item;

    public void set(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }
}

class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

public class GenericContainers {
    // Generic method returning the first element of any array
    public static <T> T getFirst(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Generic Box<T> Demonstrating Type Safety ===");
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Java Generics");
        String str = stringBox.get(); // No typecast required
        System.out.println("String Box Value: " + str);

        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        int num = intBox.get(); // No typecast required
        System.out.println("Integer Box Value: " + num);

        System.out.println("\n=== 2. Generic Pair<A, B> ===");
        Pair<String, Integer> studentRoll = new Pair<>("Uday", 89);
        System.out.println("Student Pair: " + studentRoll);
        System.out.println("Name: " + studentRoll.getFirst() + ", Roll: " + studentRoll.getSecond());

        Pair<Integer, Double> itemPrice = new Pair<>(101, 49.99);
        System.out.println("Item Price Pair: " + itemPrice);

        System.out.println("\n=== 3. Generic Method getFirst(T[]) ===");
        String[] fruits = { "Apple", "Banana", "Cherry" };
        Integer[] numbers = { 10, 20, 30, 40 };

        System.out.println("First Fruit: " + getFirst(fruits));
        System.out.println("First Number: " + getFirst(numbers));
    }
}

