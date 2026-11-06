import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
    String message() default "must not be blank";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
    String message() default "exceeds maximum allowed length";
}

class SignupForm {
    @NotBlank
    @MaxLength(8)
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    @MaxLength(10)
    private String password;

    SignupForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

public class FormValidator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object val = field.get(obj);
                String strVal = (val == null) ? "" : val.toString().trim();

                if (field.isAnnotationPresent(NotBlank.class)) {
                    NotBlank nb = field.getAnnotation(NotBlank.class);
                    if (strVal.isEmpty()) {
                        errors.add(field.getName() + ": " + nb.message());
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength ml = field.getAnnotation(MaxLength.class);
                    if (strVal.length() > ml.value()) {
                        errors.add(field.getName() + ": " + ml.message() + " (" + ml.value() + ")");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Could not access field " + field.getName());
            }
        }

        return errors;
    }

    public static void main(String[] args) {
        SignupForm invalidForm = new SignupForm("SuperLongUsername123", "", "pass1234567890");
        System.out.println("Validating Invalid Form:");
        List<String> errors = validate(invalidForm);
        for (String err : errors) {
            System.out.println("- " + err);
        }

        SignupForm validForm = new SignupForm("john_doe", "john@example.com", "secret");
        System.out.println("\nValidating Valid Form:");
        List<String> validErrors = validate(validForm);
        if (validErrors.isEmpty()) {
            System.out.println("All fields passed validation successfully!");
        } else {
            for (String err : validErrors) {
                System.out.println("- " + err);
            }
        }
    }
}

