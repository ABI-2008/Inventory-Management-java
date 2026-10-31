package inventorymanagement.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {
    private FileManager() { }

    public static void save(String fileName, Object data) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(data);
        }
    }

    public static Object load(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
            return input.readObject();
        }
    }
}
