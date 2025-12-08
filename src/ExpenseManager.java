import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {

    private final FileManager fileManager;

    public ExpenseManager(String filePath) {
        this.fileManager = new FileManager(filePath);
    }

    public List<Expense> getAllExpenses() {
        return fileManager.read();
    }

    public void addExpense(Expense expense) {
        ArrayList<Expense> list = fileManager.read();
        list.add(expense);
        fileManager.write(list);
    }

    public void deleteExpenses(List<Integer> modelRows) {
        ArrayList<Expense> list = fileManager.read();

        // Remove from largest to smallest index
        modelRows.sort((a, b) -> b - a);
        for (int row : modelRows) {
            if (row >= 0 && row < list.size()) {
                list.remove(row);
            }
        }

        fileManager.write(list);
    }
}
