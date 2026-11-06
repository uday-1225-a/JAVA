abstract class Media {
    protected String title;
    protected int daysLate;

    Media(String title, int daysLate) {
        this.title = title;
        this.daysLate = daysLate;
    }

    public String getTitle() {
        return title;
    }

    public int getDaysLate() {
        return daysLate;
    }

    abstract double computeLateFee();
}

class Book extends Media {
    Book(String title, int daysLate) {
        super(title, daysLate);
    }

    // Books charge ₹2 per day late
    double computeLateFee() {
        return daysLate * 2.0;
    }
}

class DVD extends Media {
    DVD(String title, int daysLate) {
        super(title, daysLate);
    }

    // DVDs charge ₹5 per day late
    double computeLateFee() {
        return daysLate * 5.0;
    }
}

class Magazine extends Media {
    Magazine(String title, int daysLate) {
        super(title, daysLate);
    }

    // Magazines charge ₹1.5 per day late
    double computeLateFee() {
        return daysLate * 1.5;
    }
}

public class MediaFee {
    public static void main(String[] args) {
        Media[] returnedBatch = {
            new Book("Clean Code", 4),
            new DVD("Inception", 2),
            new Magazine("Tech Today", 5),
            new Book("Effective Java", 0),
            new DVD("Interstellar", 3)
        };

        double totalLateFee = 0;

        System.out.println("--- Library Returned Media Late Fee Report ---");
        for (Media item : returnedBatch) {
            double fee = item.computeLateFee();
            totalLateFee += fee;

            System.out.printf("%-15s [%s] - %d days late -> Fee: ₹%.2f%n",
                item.getTitle(), item.getClass().getSimpleName(), item.getDaysLate(), fee);
        }

        System.out.println("----------------------------------------------");
        System.out.printf("Total Late Fees Collected: ₹%.2f%n", totalLateFee);
    }
}

