class UnsyncedTicketSystem {
    private int seatsLeft = 5;
    private int successfulBookings = 0;

    public boolean bookSeat() {
        if (seatsLeft > 0) {
            try {
                // Simulating network delay / processing
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            seatsLeft--;
            successfulBookings++;
            return true;
        }
        return false;
    }

    public int getSuccessfulBookings() {
        return successfulBookings;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }
}

class SyncedTicketSystem {
    private int seatsLeft = 5;
    private int successfulBookings = 0;

    public synchronized boolean bookSeat() {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            seatsLeft--;
            successfulBookings++;
            return true;
        }
        return false;
    }

    public synchronized int getSuccessfulBookings() {
        return successfulBookings;
    }

    public synchronized int getSeatsLeft() {
        return seatsLeft;
    }
}

public class SeatBookingRace {
    public static void main(String[] args) throws InterruptedException {
        int totalRequests = 10;
        int availableSeats = 5;

        System.out.println("Available seats: " + availableSeats);
        System.out.println("Total booking attempts: " + totalRequests);

        // 1. Without synchronization (Overselling demonstration)
        System.out.println("\n--- Test 1: Without Synchronization ---");
        UnsyncedTicketSystem unsafeSystem = new UnsyncedTicketSystem();
        Thread[] unsafeThreads = new Thread[totalRequests];

        for (int i = 0; i < totalRequests; i++) {
            final int userId = i + 1;
            unsafeThreads[i] = new Thread(() -> {
                boolean success = unsafeSystem.bookSeat();
                if (success) {
                    System.out.println("User " + userId + " successfully booked a seat.");
                }
            });
            unsafeThreads[i].start();
        }

        for (Thread t : unsafeThreads) {
            t.join();
        }

        System.out.println("Total seats successfully booked: " + unsafeSystem.getSuccessfulBookings() + 
            (unsafeSystem.getSuccessfulBookings() > availableSeats ? " (OVERBOOKED / OVERSELL BUG!)" : ""));
        System.out.println("Remaining seats: " + unsafeSystem.getSeatsLeft());

        // 2. With synchronization
        System.out.println("\n--- Test 2: WITH Synchronization ---");
        SyncedTicketSystem safeSystem = new SyncedTicketSystem();
        Thread[] safeThreads = new Thread[totalRequests];

        for (int i = 0; i < totalRequests; i++) {
            final int userId = i + 1;
            safeThreads[i] = new Thread(() -> {
                boolean success = safeSystem.bookSeat();
                if (success) {
                    System.out.println("User " + userId + " successfully booked a seat.");
                } else {
                    System.out.println("User " + userId + " failed: House Full!");
                }
            });
            safeThreads[i].start();
        }

        for (Thread t : safeThreads) {
            t.join();
        }

        System.out.println("Total seats successfully booked: " + safeSystem.getSuccessfulBookings() + " (Exactly 5 allowed)");
        System.out.println("Remaining seats: " + safeSystem.getSeatsLeft());
    }
}

