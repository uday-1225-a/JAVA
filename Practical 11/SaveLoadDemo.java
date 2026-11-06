import java.io.*;

class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String email;
    // Transient field will not be serialized
    private transient String password;

    public UserAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAccount [username=" + username + ", email=" + email + 
               ", password=" + (password == null ? "<null (transient - not saved)>" : password) + "]";
    }
}

public class SaveLoadDemo {
    public static void main(String[] args) {
        String filename = "user_accounts.ser";

        UserAccount[] accountsToSave = {
            new UserAccount("alice", "alice@example.com", "SecretPass1"),
            new UserAccount("bob", "bob@example.com", "SecretPass2"),
            new UserAccount("charlie", "charlie@example.com", "SecretPass3")
        };

        System.out.println("=== 1. Saving Objects to File ===");
        for (UserAccount acc : accountsToSave) {
            System.out.println("Before saving: " + acc);
        }

        // Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(accountsToSave);
            System.out.println("\nSuccessfully serialized array of UserAccount to '" + filename + "'.");
        } catch (IOException e) {
            System.out.println("Error saving objects: " + e.getMessage());
            return;
        }

        // Deserialization
        System.out.println("\n=== 2. Loading Objects from File ===");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            UserAccount[] loadedAccounts = (UserAccount[]) in.readObject();

            for (UserAccount acc : loadedAccounts) {
                System.out.println("Loaded back:   " + acc);
            }

            System.out.println("\nObservation: The transient field 'password' became null as expected!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading objects: " + e.getMessage());
        } finally {
            // Clean up temporary file
            new File(filename).delete();
        }
    }
}

