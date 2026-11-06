public class PasswordChecker {

    public static void checkPassword(String pw) {

        boolean length = pw.length() >= 8;
        boolean upper = pw.matches(".*[A-Z].*");
        boolean digit = pw.matches(".*[0-9].*");
        boolean special = pw.matches(".*[^a-zA-Z0-9].*");

        int count = 0;

        if (length) {
            System.out.println("Length: Passed");
            count++;
        } else {
            System.out.println("Length: Failed");
        }

        if (upper) {
            System.out.println("Uppercase: Passed");
            count++;
        } else {
            System.out.println("Uppercase: Failed");
        }

        if (digit) {
            System.out.println("Digit: Passed");
            count++;
        } else {
            System.out.println("Digit: Failed");
        }

        if (special) {
            System.out.println("Special Character: Passed");
            count++;
        } else {
            System.out.println("Special Character: Failed");
        }

        String strength;

        if (count <= 1) {
            strength = "Weak";
        } else if (count <= 3) {
            strength = "Medium";
        } else {
            strength = "Strong";
        }

        System.out.println("Password Strength: " + strength);
        System.out.println();
    }

    public static void main(String[] args) {

        checkPassword("abc");
        checkPassword("Abcd1234!");
        checkPassword("Hello123");
    }
}