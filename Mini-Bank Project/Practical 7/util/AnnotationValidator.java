package util;

import model.annotation.MaxLength;
import model.annotation.Positive;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationValidator {
    public static String[] validate(Object obj) {
        if (obj == null) return new String[] { "Target object is null" };

        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object val = field.get(obj);

                // Check @Positive annotation on numeric fields
                if (field.isAnnotationPresent(Positive.class)) {
                    Positive positive = field.getAnnotation(Positive.class);
                    if (val instanceof Number num) {
                        if (num.longValue() <= 0) {
                            errors.add("Field '" + field.getName() + "': " + positive.message() + " (found: " + num + ")");
                        }
                    }
                }

                // Check @MaxLength annotation on String fields
                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength maxLength = field.getAnnotation(MaxLength.class);
                    if (val instanceof String str) {
                        if (str.length() > maxLength.value()) {
                            errors.add("Field '" + field.getName() + "': exceeds maximum length of " + maxLength.value() + " (length: " + str.length() + ")");
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Unable to access field: " + field.getName());
            }
        }

        return errors.toArray(new String[0]);
    }
}

