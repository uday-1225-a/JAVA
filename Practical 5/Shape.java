abstract class Shape {
    abstract double area();

    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 8),
            new Circle(3)
        };

        double totalArea = 0;
        Shape largestShape = shapes[0];

        for (Shape s : shapes) {
            double a = s.area();
            System.out.printf("%s Area: %.2f%n", s.getClass().getSimpleName(), a);
            totalArea += a;

            if (a > largestShape.area()) {
                largestShape = s;
            }
        }

        System.out.printf("Total Area: %.2f%n", totalArea);
        System.out.printf("Largest Shape: %s (Area: %.2f)%n", 
            largestShape.getClass().getSimpleName(), largestShape.area());
    }
}

class Circle extends Shape {
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    double area() {
        return length * width;
    }
}

class Triangle extends Shape {
    private double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    double area() {
        return 0.5 * base * height;
    }
}

