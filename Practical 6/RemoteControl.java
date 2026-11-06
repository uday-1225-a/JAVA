interface Switchable {
    void on();
    void off();
    boolean isOn();

    default void toggle() {
        if (isOn()) {
            off();
        } else {
            on();
        }
    }
}

class Fan implements Switchable {
    private boolean state = false;

    public void on() {
        state = true;
        System.out.println("Fan is turned ON");
    }

    public void off() {
        state = false;
        System.out.println("Fan is turned OFF");
    }

    public boolean isOn() {
        return state;
    }
}

class Light implements Switchable {
    private boolean state = false;

    public void on() {
        state = true;
        System.out.println("Light is turned ON");
    }

    public void off() {
        state = false;
        System.out.println("Light is turned OFF");
    }

    public boolean isOn() {
        return state;
    }
}

@FunctionalInterface
interface DeviceSchedule {
    boolean canSwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {
        Switchable[] devices = { new Fan(), new Light() };

        System.out.println("--- Toggling all devices ---");
        for (Switchable dev : devices) {
            dev.toggle();
        }

        System.out.println("\n--- Toggling all devices again ---");
        for (Switchable dev : devices) {
            dev.toggle();
        }

        int currentHour = 22; // 10 PM

        // 1. Functional interface using Anonymous Class
        DeviceSchedule nightScheduleAnon = new DeviceSchedule() {
            @Override
            public boolean canSwitchOn(Switchable device, int hour) {
                // Lights allowed anytime, but Fan not allowed after 23 (11 PM)
                if (device instanceof Light) return true;
                return hour < 23;
            }
        };

        // 2. Functional interface using Lambda Expression
        DeviceSchedule nightScheduleLambda = (device, hour) -> {
            // General rule: devices can only switch on between 6 and 23
            return hour >= 6 && hour <= 23;
        };

        System.out.println("\n--- Schedule Checks at hour " + currentHour + " ---");
        System.out.println("Anonymous class check for Fan: " + nightScheduleAnon.canSwitchOn(devices[0], currentHour));
        System.out.println("Lambda expression check for Light: " + nightScheduleLambda.canSwitchOn(devices[1], currentHour));
    }
}

