import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class StudentRecord {
    @Column(name = "student_id")
    private String id;

    @Column(name = "full_name")
    private String name;

    @Column(name = "email_address")
    private String email;

    @Column(name = "department")
    private String department;

    public StudentRecord() {}

    @Override
    public String toString() {
        return "StudentRecord [id=" + id + ", name=" + name + 
               ", email=" + email + ", department=" + department + "]";
    }
}

public class ColumnMapper {
    public static <T> T mapRowToObject(Class<T> clazz, String[] headers, String[] rowData) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            // Create a lookup map for header name -> index
            Map<String, Integer> headerIndexMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                headerIndexMap.put(headers[i].trim().toLowerCase(), i);
            }

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column col = field.getAnnotation(Column.class);
                    String colName = col.name().trim().toLowerCase();

                    field.setAccessible(true);
                    if (headerIndexMap.containsKey(colName)) {
                        int index = headerIndexMap.get(colName);
                        if (index < rowData.length) {
                            field.set(instance, rowData[index]);
                        }
                    } else {
                        System.out.println("Warning: Missing column in header for field '" + 
                                           field.getName() + "' (column: " + col.name() + "). Left as null.");
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Notice "department" is missing in header and data row
        String[] headers = { "student_id", "full_name", "email_address" };
        String[] rowData = { "24CS089", "Uday Dave", "uday@charusat.edu.in" };

        System.out.println("--- Mapping Header & Data Row to Object via @Column ---");
        StudentRecord student = mapRowToObject(StudentRecord.class, headers, rowData);

        System.out.println("\nPopulated Object Result:");
        System.out.println(student);
    }
}

