package view;

import model.Expense;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExpenseTablePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;

    private static final String[] COLUMN_NAMES = {
        "Name", "Price", "Type", "Description", "Store", "Date"
    };

    public ExpenseTablePanel() {
        setLayout(new BorderLayout());

        // Custom non-editable table model
        model = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(model);
        table.setAutoCreateRowSorter(true);

        // Preferred widths
        table.getColumnModel().getColumn(0).setPreferredWidth(160);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(110);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /** Replace the table contents with updated expense data */
    public void updateData(ArrayList<Expense> expenses) {
        model.setRowCount(0);

        for (Expense e : expenses) {
            model.addRow(new Object[] {
                e.getName(),
                e.getPrice(),
                e.getType(),
                e.getDescription(),
                e.getStoreName(),
                e.getDate().toString()
            });
        }

        model.fireTableDataChanged();
    }

    /** Returns selected rows converted to model indices */
    public ArrayList<Integer> getSelectedModelRows() {
        int[] viewRows = table.getSelectedRows();
        ArrayList<Integer> modelIndices = new ArrayList<>();

        for (int viewIndex : viewRows) {
            int modelIndex = table.convertRowIndexToModel(viewIndex);
            modelIndices.add(modelIndex);
        }

        return modelIndices;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
