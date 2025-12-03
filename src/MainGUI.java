import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTable tblExpensesTable;
    private DefaultTableModel expensesModel;
    private PieChartPanel pieChart;
    private TimeSeriesChartPanel timeChart; 

    public MainGUI() {
        //==================================================
        
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        //==================================================

        String[] columnNames = { "Name", "Price", "Type", "Description", "Store", "Date" };

        expensesModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;   // disable cell editing
            }
        };
        
        tblExpensesTable = new JTable(expensesModel);
        tblExpensesTable.setAutoCreateRowSorter(true);

        // column widths
        tblExpensesTable.getColumnModel().getColumn(0).setPreferredWidth(160);	// name
        tblExpensesTable.getColumnModel().getColumn(1).setPreferredWidth(60);	// price
        tblExpensesTable.getColumnModel().getColumn(2).setPreferredWidth(100);	// type
        tblExpensesTable.getColumnModel().getColumn(3).setPreferredWidth(200);	// desc
        tblExpensesTable.getColumnModel().getColumn(4).setPreferredWidth(120);	// store
        tblExpensesTable.getColumnModel().getColumn(5).setPreferredWidth(110);	// date

        JScrollPane spExpensesTable = new JScrollPane(tblExpensesTable);
        spExpensesTable.setBounds(6, 6, 600, 519);
        contentPane.add(spExpensesTable);
        
        //==================================================
        
        pieChart = new PieChartPanel();
        pieChart.setBounds(618, 13, 275, 250);
        contentPane.add(pieChart);
        
        //==================================================
        
        timeChart = new TimeSeriesChartPanel();
        timeChart.setBounds(618, 264, 275, 250);
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

                int[] selectedRows = tblExpensesTable.getSelectedRows();
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(
                        MainGUI.this,
                        "No rows selected.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                    MainGUI.this,
                    "Are you sure you want to delete the selected expense(s)?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                FileManager fm = new FileManager("expenses.dat");
                ArrayList<Expense> list = fm.read();
                
                ArrayList<Integer> modelIndices = new ArrayList<>();
                for (int row : selectedRows) {
                    int modelRow = tblExpensesTable.convertRowIndexToModel(row);
                    modelIndices.add(modelRow);
                }

                modelIndices.sort((a, b) -> b - a);	// sort list from biggest to smallest
                for (int index : modelIndices) {	// remove items
                    if (index >= 0 && index < list.size()) {
                        list.remove(index);
                    }
                }

                fm.write(list);
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

        expensesModel.setRowCount(0); // clear

        for (Expense e : expenses) {
            expensesModel.addRow(new Object[]{
                e.getName(),
                e.getPrice(),
                e.getType(),
                e.getDescription(),
                e.getStoreName(),
                e.getDate().toString()
            });
        }
        expensesModel.fireTableDataChanged();
        
        pieChart.updateData(expenses);
        timeChart.updateData(expenses);
    }
}
