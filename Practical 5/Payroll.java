abstract class Employee {
    protected String name;
    protected String id;

    Employee(String name, String id) {
        this.name = name;
        this.id = id;
    }

    abstract double monthlySalary();

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

class FullTime extends Employee {
    private double salary;

    FullTime(String name, String id, double salary) {
        super(name, id);
        this.salary = salary;
    }

    double monthlySalary() {
        return salary;
    }
}

class PartTime extends Employee {
    private int hours;
    private double rate;

    PartTime(String name, String id, int hours, double rate) {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    double monthlySalary() {
        return hours * rate;
    }
}

class Intern extends Employee {
    private double stipend;

    Intern(String name, String id, double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    double monthlySalary() {
        return stipend;
    }
}

public class Payroll {
    public static void main(String[] args) {
        Employee[] employees = {
            new FullTime("Alice", "EMP101", 60000),
            new PartTime("Bob", "EMP102", 80, 400),
            new Intern("Charlie", "INT201", 15000),
            new FullTime("Diana", "EMP103", 75000)
        };

        double totalSalary = 0;

        for (Employee emp : employees) {
            double sal = emp.monthlySalary();
            totalSalary += sal;

            System.out.print(emp.getId() + " - " + emp.getName() + " (" + emp.getClass().getSimpleName() + "): " + sal);

            if (emp instanceof Intern) {
                System.out.print(" [Note: Intern - 3 months evaluation period]");
            }
            System.out.println();
        }

        System.out.println("----------------------------------------");
        System.out.println("Total Payroll: " + totalSalary);
    }
}

