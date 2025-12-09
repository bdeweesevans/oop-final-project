import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class MainGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnClear;


    private ExpensesTablePanel expensesTable;
    private PieChartPanel pieChart;
    private TimeSeriesChartPanel timeChart; 

    public MainGUI() {
        //==================================================
        
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ================================================

        JLabel tableHeader = new JLabel("Expenses Table:");
        tableHeader.setBounds(6, 2, 300, 20); 
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        contentPane.add(tableHeader);

        // ================================================

        JLabel chartsHeader = new JLabel("Expenses Charts:");
        chartsHeader.setBounds(618, 2, 300, 20); 
        chartsHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        contentPane.add(chartsHeader);
        
        //==================================================

        int rightX = 600;   
        int baseY = 2;      

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setBounds(rightX - 270, baseY, 60, 20);
        contentPane.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(rightX - 225, baseY, 120, 22);
        contentPane.add(txtSearch);

        btnSearch = new JButton("Go");
        btnSearch.setBounds(rightX - 100, baseY, 45, 22);
        btnSearch.addActionListener(e -> performSearch());
        contentPane.add(btnSearch);

        // CLEAR BUTTON — resets search field and reloads table
        btnClear = new JButton("Clear");
        btnClear.setBounds(rightX - 55, baseY, 60, 22);
        btnClear.addActionListener(e -> clearSearch());
        contentPane.add(btnClear);


        //==================================================

        expensesTable = new ExpensesTablePanel();
        expensesTable.setBounds(6, 22, 600, 515);
        contentPane.add(expensesTable);

        //==================================================

        JSeparator verticalLine = new JSeparator(SwingConstants.VERTICAL);
        verticalLine.setBounds(610, 6, 2, 550);
        contentPane.add(verticalLine);
        
        //==================================================
        
        pieChart = new PieChartPanel();
        pieChart.setBounds(618, 24, 275, 250);
        contentPane.add(pieChart);
        
        //==================================================
        
        timeChart = new TimeSeriesChartPanel();
        timeChart.setBounds(618, 271, 275, 250);
        contentPane.add(timeChart);
        
        //==================================================
        
        refreshContent();

        //==================================================

        JButton btnAddExpense = new JButton("Add");
        btnAddExpense.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddExpenseDialog dialog = new AddExpenseDialog(MainGUI.this);
                dialog.setVisible(true);
                if (dialog.wasCreated()) {
                    refreshContent();
                }
            }
        });
        btnAddExpense.setBackground(new Color(185, 255, 185));
        btnAddExpense.setBounds(446, 537, 75, 29);
        contentPane.add(btnAddExpense);
        
        //==================================================
        
        JButton btnDeleteExpense = new JButton("Delete");
        btnDeleteExpense.setBackground(new Color(255, 200, 200));
        btnDeleteExpense.setBounds(522, 537, 84, 29);
        btnDeleteExpense.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ArrayList<Integer> modelRows = expensesTable.getSelectedModelRows();
                
                // ensure row[s] selected
                if (modelRows.isEmpty()) {
                    JOptionPane.showMessageDialog(MainGUI.this,
                        "No rows selected.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // confirm deletion
                int choice = JOptionPane.showConfirmDialog(
                        MainGUI.this,
                        "Delete selected expense(s)?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);

                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }
                
                // delete from arraylist + save + refresh
                FileManager fm = new FileManager("expenses.dat");
                ArrayList<Expense> expenses = fm.read();
                modelRows.sort((a, b) -> b - a);
                for (int row : modelRows) {
                    if (row >= 0 && row < expenses.size()) {
                        expenses.remove(row);
                    }
                }            
                fm.write(expenses);
                refreshContent();
            }
        });
        contentPane.add(btnDeleteExpense);
        
        //==================================================
        
        JButton btnExit = new JButton("Exit");
        btnExit.setBackground(new Color(180, 205, 255));
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setBounds(6, 537, 94, 29);
        contentPane.add(btnExit);

        //==================================================

        setLocationRelativeTo(null);
    }
    
    // Updates the table and charts on the GUI.   
    public void refreshContent() {
        FileManager fm = new FileManager("expenses.dat");
        ArrayList<Expense> expenses = fm.read();
        
        expensesTable.updateData(expenses);
        pieChart.updateData(expenses);
        timeChart.updateData(expenses);
    }

    private void performSearch() {
        String query = txtSearch.getText().trim().toLowerCase();

        FileManager fm = new FileManager("expenses.dat");
        ArrayList<Expense> all = fm.read();

        if (query.isEmpty()) {
            // If no search term → show everything
            expensesTable.updateData(all);
            pieChart.updateData(all);
            timeChart.updateData(all);
            return;
        }

        ArrayList<Expense> filtered = new ArrayList<>();
        for (Expense exp : all) {
            String name = exp.getName().toLowerCase();
            String desc = exp.getDescription().toLowerCase();  

            if (name.contains(query) || desc.contains(query)) {
                filtered.add(exp);
            }
        }

        expensesTable.updateData(filtered);
        pieChart.updateData(filtered);
        timeChart.updateData(filtered);
    }


    private void clearSearch() {
        txtSearch.setText("");
        refreshContent();
    }


}
