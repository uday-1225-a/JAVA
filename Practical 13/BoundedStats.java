public class BoundedStats {
    // Bounded type parameter: only classes that inherit from java.lang.Number are permitted
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }

        double sum = 0.0;
        for (N val : values) {
            sum += val.doubleValue(); // doubleValue() is guaranteed by Number bound
        }

        return sum / values.length;
    }

    public static void main(String[] args) {
        Integer[] intArr = { 10, 20, 30, 40, 50 };
        Double[] doubleArr = { 1.5, 2.5, 3.5, 4.5 };
        Long[] longArr = { 1000L, 2000L, 3000L, 4000L };

        System.out.println("=== Bounded Type Parameter <N extends Number> Demonstration ===");
        System.out.printf("Average of Integer[]: %.2f%n", average(intArr));
        System.out.printf("Average of Double[]:  %.2f%n", average(doubleArr));
        System.out.printf("Average of Long[]:    %.2f%n", average(longArr));

        System.out.println("\nNote on Type Safety:");
        System.out.println("If we try to call average(new String[]{\"A\", \"B\"}), the compiler immediately");
        System.out.println("rejects it with a compile-time error: 'type argument String is not within bounds of type-variable N'.");
        System.out.println("This guarantees that non-numeric types can never be accidentally passed.");
    }
}

