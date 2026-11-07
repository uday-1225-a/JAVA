package util;

import model.Account;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StatePersister {
    public static void save(List<Account> accounts, Path file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(new ArrayList<>(accounts));
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Account> load(Path file) throws IOException, ClassNotFoundException {
        if (!file.toFile().exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            return (List<Account>) ois.readObject();
        }
    }
}

