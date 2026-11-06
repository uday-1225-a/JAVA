import java.util.Scanner;

class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean success = false;
        int attempt = 0;

        System.out.println("=== Guarded Calculator ===");

        while (!success) {
            attempt++;
            System.out.println("\n--- Attempt #" + attempt + " ---");
            try {
                System.out.print("Enter first number: ");
                String num1Str = sc.next();
                double a = Double.parseDouble(num1Str);

                System.out.print("Enter operator (+, -, *, /): ");
                char op = sc.next().charAt(0);

                System.out.print("Enter second number: ");
                String num2Str = sc.next();
                double b = Double.parseDouble(num2Str);

                double result;
                switch (op) {
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;
                    case '/':
                        if (b == 0) {
                            throw new DivideByZeroException("Cannot divide by zero!");
                        }
                        result = a / b;
                        break;
                    default:
                        System.out.println("Error: Invalid operator '" + op + "'. Supported operators: +, -, *, /");
                        continue;
                }

                System.out.printf("Result: %.2f %c %.2f = %.2f%n", a, op, b, result);
                success = true;

            } catch (NumberFormatException e) {
                System.out.println("Invalid-Number Error: Please enter a valid numeric value.");
            } catch (DivideByZeroException e) {
                System.out.println("Arithmetic Error: " + e.getMessage());
            } finally {
                System.out.println("[Log] Attempt #" + attempt + " finished execution.");
            }
        }

        System.out.println("Calculation completed successfully. Program exiting.");
        sc.close();
    }
}

