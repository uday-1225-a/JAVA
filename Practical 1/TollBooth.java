import java.util.Scanner;

class TollBooth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int total = 0;
        int bikes = 0, cars = 0, trucks = 0;

        while (true) {
            System.out.print("Vehicle (bike/car/truck/done): ");
            String type = sc.next().toLowerCase();

            if (type.equals("done"))
                break;

            if (type.equals("bike")) {
                total += 20;
                bikes++;
            } else if (type.equals("car")) {
                total += 50;
                cars++;
            } else if (type.equals("truck")) {
                total += 150;
                trucks++;
            } else {
                System.out.println("Invalid vehicle");
            }
        }

        System.out.println("Total toll: " + total);

        if (bikes > cars && bikes > trucks)
            System.out.println("Most frequent: bike");
        else if (cars > bikes && cars > trucks)
            System.out.println("Most frequent: car");
        else if (trucks > bikes && trucks > cars)
            System.out.println("Most frequent: truck");
        else
            System.out.println("Most frequent: tie");

        sc.close();
    }
}