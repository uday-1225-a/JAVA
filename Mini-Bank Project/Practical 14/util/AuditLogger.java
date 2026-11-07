package util;

import annotation.Audit;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(Class<?> clazz, String methodName, Object... params) {
        try {
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.getName().equals(methodName) && m.isAnnotationPresent(Audit.class)) {
                    Audit audit = m.getAnnotation(Audit.class);
                    String action = audit.action().isEmpty() ? methodName : audit.action();
                    System.out.println("[AUDIT " + LocalDateTime.now().format(FORMATTER) + "] " + action);
                    break;
                }
            }
        } catch (Exception ignored) {}
    }
}

