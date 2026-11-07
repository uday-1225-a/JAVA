package repository;

import annotation.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Repository<T> {
    private final Map<String, T> storage = new ConcurrentHashMap<>();

    public T save(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null.");
        String id = extractId(entity);
        storage.put(id, entity);
        return entity;
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

    private String extractId(T entity) {
        Class<?> current = entity.getClass();
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        if (val != null) return val.toString();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Error reading @Id", e);
                    }
                }
            }
            current = current.getSuperclass();
        }
        throw new IllegalStateException("Missing @Id in class: " + entity.getClass().getSimpleName());
    }
}

