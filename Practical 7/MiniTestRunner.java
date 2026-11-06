import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class SampleTestSuite {
    @Run
    public void testLogin() {
        System.out.println(" Executing testLogin()... PASS");
    }

    public void utilityHelper() {
        System.out.println(" Utility method (not a test)");
    }

    @Run
    public void testPayment() {
        System.out.println(" Executing testPayment()... PASS");
    }

    @Run
    public void testLogout() {
        System.out.println(" Executing testLogout()... PASS");
    }

    public void internalSetup() {
        System.out.println(" Setup method (no @Run)");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) {
        SampleTestSuite suite = new SampleTestSuite();
        Class<?> clazz = suite.getClass();

        System.out.println("--- Starting Mini Test Runner for " + clazz.getSimpleName() + " ---");

        int runCount = 0;
        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(Run.class)) {
                try {
                    System.out.println("Found test method: " + m.getName());
                    m.invoke(suite);
                    runCount++;
                } catch (Exception e) {
                    System.out.println("Error running " + m.getName() + ": " + e.getMessage());
                }
            }
        }

        System.out.println("---------------------------------------------");
        System.out.println("Total tests executed: " + runCount);
    }
}

