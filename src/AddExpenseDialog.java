import java.awt.Color;
import java.time.LocalDate;
import javax.swing.*;

public class AddExpenseDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField txtName;
    private JTextField txtPrice;
    private JComboBox<ExpenseType> cmbType;
    private JTextField txtDescription;
    private JTextField txtStore;

    private Expense createdExpense = null;  // store created expense

    public AddExpenseDialog(JFrame parent) {
        super(parent, "Add New Expense", true);
        setSize(355, 335);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);

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

        // No need for strings — enums display nicely because of your toString()
        cmbType = new JComboBox<>(ExpenseType.values());
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
            if (tryCreateExpense()) {
                if (chkKeepCreating.isSelected()) {
                    clearForm();
                } else {
                    dispose();
                }
            }
        });
        add(btnCreate);
    }

    private boolean tryCreateExpense() {
        try {
            float price = Float.parseFloat(txtPrice.getText());
            ExpenseType type = (ExpenseType) cmbType.getSelectedItem();

            String name = txtName.getText().trim();
            String desc = txtDescription.getText().trim();
            String store = txtStore.getText().trim();
            LocalDate date = LocalDate.now();

            createdExpense = new Expense(price, type, name, desc, store, date);

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

    public Expense getCreatedExpense() {
        return createdExpense;
    }
}
