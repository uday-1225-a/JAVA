import java.io.*;
import java.nio.file.Path;

public class StatePersister {
    public static void save(Account[] accounts, Path file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(accounts);
            System.out.println("[StatePersister] Saved " + accounts.length + " accounts to " + file.getFileName());
        }
    }

    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            Account[] accounts = (Account[]) ois.readObject();
            System.out.println("[StatePersister] Loaded " + accounts.length + " accounts from " + file.getFileName());
            return accounts;
        }
    }
}

