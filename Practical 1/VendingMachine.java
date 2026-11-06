import java.util.Scanner;

public class VendingMachine {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int price = 15;
        int total = 0;
        int coinValue;

        while (total < price) {

            System.out.print("Enter coin (ONE, TWO, FIVE, TEN): ");
            String coin = sc.next().toUpperCase();

            if (coin.equals("ONE")) {
                coinValue = 1;
            } 
            else if (coin.equals("TWO")) {
                coinValue = 2;
            } 
            else if (coin.equals("FIVE")) {
                coinValue = 5;
            } 
            else if (coin.equals("TEN")) {
                coinValue = 10;
            } 
            else {
                System.out.println("Invalid coin");
                continue;
            }

            total = total + coinValue;

            System.out.println("Total so far: " + total);
        }

        int change = total - price;

        System.out.println("Paid. Change: " + change);

        sc.close();
    }
}