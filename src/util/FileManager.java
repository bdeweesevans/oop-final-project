package util;

import model.Expense;
import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private final String filePath;
    
    public FileManager() {
        this.filePath = "data/expenses.dat";
        checkFileIntegrity();
    }

    private void checkFileIntegrity() {
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.createNewFile();
                write(new ArrayList<>());	// initialize w/ empty ArrayList
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write Expense ArrayList to file
    public void write(ArrayList<Expense> expenses) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read Expense ArrayList from file
    @SuppressWarnings("unchecked")
    public ArrayList<Expense> read() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Expense>) in.readObject();
        } catch (EOFException eof) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
