import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

public class AddExpenseDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField txtName;
    private JTextField txtPrice;
    private JComboBox<String> cmbType;
    private JTextField txtDescription;
    private JTextField txtStore;

    private boolean created = false;  // to signal creation

    public AddExpenseDialog(JFrame parent) {
        super(parent, "Add New Expense", true);  // modal dialog
        setSize(355, 335);
        setResizable(false);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 20, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(140, 20, 200, 25);
        add(txtName);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(20, 60, 100, 25);
        add(lblPrice);

        txtPrice = new JTextField();
        txtPrice.setBounds(140, 60, 200, 25);
        add(txtPrice);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(20, 100, 100, 25);
        add(lblType);

        DefaultComboBoxModel<String> typeModel = new DefaultComboBoxModel<>();

        for (ExpenseType t : ExpenseType.values()) {
            typeModel.addElement(formatEnumLabel(t));
        }
        cmbType = new JComboBox<>(typeModel);

        cmbType.setBounds(140, 100, 200, 25);
        add(cmbType);

        JLabel lblDesc = new JLabel("Description:");
        lblDesc.setBounds(20, 140, 100, 25);
        add(lblDesc);

        txtDescription = new JTextField();
        txtDescription.setBounds(140, 140, 200, 25);
        add(txtDescription);

        JLabel lblStore = new JLabel("Store:");
        lblStore.setBounds(20, 180, 100, 25);
        add(lblStore);

        txtStore = new JTextField();
        txtStore.setBounds(140, 180, 200, 25);
        add(txtStore);

        JCheckBox chkKeepCreating = new JCheckBox("Keep creating");
        chkKeepCreating.setBounds(120, 260, 150, 25); 
        add(chkKeepCreating);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBackground(new Color(255, 200, 200));
        btnCancel.setBounds(70, 225, 100, 30); 
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);

        JButton btnCreate = new JButton("Create");
        btnCreate.setBackground(new Color(185, 255, 185));
        btnCreate.setBounds(180, 225, 100, 30);
        btnCreate.addActionListener(e -> {
            if (createExpense()) {
                if (chkKeepCreating.isSelected()) {
                    clearForm(); 
                } else {
                    dispose();
                }
            }
        });
        add(btnCreate);
    }

    private String formatEnumLabel(ExpenseType type) {
        String raw = type.toString(); // e.g. "TRANSPORT"
        return raw.substring(0,1).toUpperCase() + raw.substring(1).toLowerCase();
    }

    private boolean createExpense() {
        try {
            float price = Float.parseFloat(txtPrice.getText());
            String typeLabel = (String) cmbType.getSelectedItem();
            ExpenseType type = ExpenseType.valueOf(typeLabel.toUpperCase());
            String name = txtName.getText();
            String desc = txtDescription.getText();
            String store = txtStore.getText();
            LocalDate date = LocalDate.now();

            Expense exp = new Expense(price, type, name, desc, store, date);

            // Save it
            FileManager fm = new FileManager("expenses.dat");
            ArrayList<Expense> list = fm.read();
            list.add(exp);
            fm.write(list);

            created = true;
            return true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Invalid input: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtPrice.setText("");
        txtDescription.setText("");
        txtStore.setText("");
        cmbType.setSelectedIndex(0);
    }

    public boolean wasCreated() {
        return created;
    }
}
