@FunctionalInterface
interface Notifier {
    void send(String message);
}

// Marker Interface (no methods)
interface Urgent {
}

class UrgentNotificationService implements Notifier, Urgent {
    private String channel;

    UrgentNotificationService(String channel) {
        this.channel = channel;
    }

    @Override
    public void send(String message) {
        System.out.println("[" + channel + "] " + message);
    }
}

public class NotificationSender {
    public static void main(String[] args) {
        // Lambdas for regular senders
        Notifier emailSender = msg -> System.out.println("[Email] " + msg);
        Notifier smsSender = msg -> System.out.println("[SMS] " + msg);

        // Urgent sender marked with Urgent interface
        Notifier urgentAlarm = new UrgentNotificationService("Emergency Siren");

        Notifier[] notifiers = { emailSender, smsSender, urgentAlarm };

        String broadcastMessage = "System maintenance scheduled at midnight.";

        System.out.println("--- Broadcasting Message ---");
        for (Notifier notifier : notifiers) {
            if (notifier instanceof Urgent) {
                System.out.println("(Urgent sender detected - sending twice):");
                notifier.send(broadcastMessage);
                notifier.send(broadcastMessage);
            } else {
                notifier.send(broadcastMessage);
            }
        }
    }
}

