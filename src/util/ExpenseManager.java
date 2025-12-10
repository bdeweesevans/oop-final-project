package util;

import model.Expense;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {

    private final FileManager fileManager;

    public ExpenseManager() {
        this.fileManager = new FileManager();
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
    
    public ArrayList<Expense> searchExpenses(String query) {
        ArrayList<Expense> all = fileManager.read();
        
        if (query == null || query.trim().isEmpty()) {
            return all;
        }
        
        String searchTerm = query.trim().toLowerCase();
        ArrayList<Expense> filtered = new ArrayList<>();
        
        for (Expense exp : all) {
            String name = exp.getName().toLowerCase();
            String desc = exp.getDescription().toLowerCase();
            
            if (name.contains(searchTerm) || desc.contains(searchTerm)) {
                filtered.add(exp);
            }
        }
        
        return filtered;
    }
}
