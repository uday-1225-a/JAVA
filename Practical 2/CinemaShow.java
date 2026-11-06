class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;

    private static int totalBooked = 0;

    CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        seatsAvailable = capacity;
    }

    CinemaShow(String title) {
        this(title, 100);
    }

    boolean book(int n) {
        if (n > 0 && n <= seatsAvailable) {
            seatsAvailable = seatsAvailable - n;
            totalBooked = totalBooked + n;
            return true;
        }
        return false;
    }

    void cancel(int n) {
        if (n > 0) {
            seatsAvailable = seatsAvailable + n;

            if (seatsAvailable > capacity) {
                seatsAvailable = capacity;
            }
        }
    }

    int getSeatsAvailable() {
        return seatsAvailable;
    }

    static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {

        CinemaShow show = new CinemaShow("Avatar", 50);

        System.out.println("Booking 10 seats: " + show.book(10));
        System.out.println("Seats available: " + show.getSeatsAvailable());

        System.out.println("Booking 45 seats: " + show.book(45));
        System.out.println("Seats available: " + show.getSeatsAvailable());

        show.cancel(5);

        System.out.println("After cancellation: " + show.getSeatsAvailable());

        System.out.println("Total booked: " + CinemaShow.getTotalBooked());
    }
}