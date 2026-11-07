package repository;

import annotation.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository<T> {
    private final Map<String, T> storage = new HashMap<>();

    // Step 2: save() reads @Id field via reflection
    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity to save cannot be null.");
        }

        String id = extractId(entity);
        storage.put(id, entity);
        return entity;
    }

    // Step 3: @SafeVarargs saveAll
    @SafeVarargs
    public final void saveAll(T... items) {
        if (items != null) {
            for (T item : items) {
                save(item);
            }
        }
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    public int count() {
        return storage.size();
    }

    // Reflection helper to find @Id
    private String extractId(T entity) {
        Class<?> current = entity.getClass();
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        if (val != null) {
                            return val.toString();
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Could not read @Id field: " + field.getName(), e);
                    }
                }
            }
            current = current.getSuperclass();
        }
        throw new IllegalStateException("No field marked with @Id found in " + entity.getClass().getSimpleName());
    }

    // Step 4: Generic method with bounded type parameter
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) return 0.0;
        double sum = 0.0;
        for (N val : values) {
            sum += val.doubleValue();
        }
        return sum / values.length;
    }
}

