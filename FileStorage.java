import java.io.*;
import java.util.HashMap;

public class FileStorage {
    private String fileName;

    public FileStorage(String fileName) {
        this.fileName = fileName;
    }

    // Save URL data to a file
    public void saveData(HashMap<String, String> urlMap) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(urlMap);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load URL data from a file
    @SuppressWarnings("unchecked")
    public HashMap<String, String> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (HashMap<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
