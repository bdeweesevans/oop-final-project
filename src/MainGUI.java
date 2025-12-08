import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class MainGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private ExpenseManager expenseManager;
    private JPanel contentPane;
    private ExpenseTablePanel expensesTable;
    private PieChartPanel pieChart;
    private TimeSeriesChartPanel timeChart;

    public MainGUI() {
    	expenseManager = new ExpenseManager("expenses.dat");

        //==================================================
        
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ================================================

        JLabel tableHeader = new JLabel("Expense Table:");
        tableHeader.setBounds(6, 2, 140, 20); 
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        contentPane.add(tableHeader);

        // ================================================

        JLabel chartsHeader = new JLabel("Expense Charts:");
        chartsHeader.setBounds(618, 2, 300, 20); 
        chartsHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        contentPane.add(chartsHeader);
        
        //==================================================

        expensesTable = new ExpenseTablePanel();
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
        timeChart.setBounds(618, 287, 275, 250);
        contentPane.add(timeChart);

        //==================================================

        JButton btnAddExpense = new JButton("Add");
        btnAddExpense.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddExpenseDialog dialog = new AddExpenseDialog(MainGUI.this);
                dialog.setVisible(true);
                if (dialog.getCreatedExpense() != null) {
                    expenseManager.addExpense(dialog.getCreatedExpense());
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
            	List<Integer> rows = expensesTable.getSelectedModelRows();
                if (rows.isEmpty()) {
                    JOptionPane.showMessageDialog(MainGUI.this, "No rows selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int choice = JOptionPane.showConfirmDialog(
                    MainGUI.this,
                    "Delete selected expense(s)?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

                if (choice != JOptionPane.YES_OPTION) return;

                expenseManager.deleteExpenses(rows);
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
        
        refreshContent();
        
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
}
