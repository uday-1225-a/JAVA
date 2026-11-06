import java.util.Objects;

public class Fraction {
    private int num, den;

    Fraction(int num, int den) {
        if (den == 0)
            throw new IllegalArgumentException("Denominator cannot be zero");

        int g = gcd(Math.abs(num), Math.abs(den));

        this.num = num / g;
        this.den = den / g;

        if (this.den < 0) {
            this.num = -this.num;
            this.den = -this.den;
        }
    }

    int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public String toString() {
        return num + "/" + den;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Fraction))
            return false;

        Fraction f = (Fraction) obj;

        return num == f.num && den == f.den;
    }

    public int hashCode() {
        return Objects.hash(num, den);
    }

    public static void main(String[] args) {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);

        System.out.println("Fraction 1: " + f1);
        System.out.println("Fraction 2: " + f2);
        System.out.println("Fraction 3: " + f3);

        System.out.println("f1 equals f2: " + f1.equals(f2));
        System.out.println("f2 equals f3: " + f2.equals(f3));
    }
}