package view;

import model.Expense;
import model.ExpenseType;
import java.awt.Color;
import java.util.Date;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;

public class AddExpenseDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField txtName;
    private JTextField txtPrice;
    private JComboBox<ExpenseType> cmbType;
    private JTextField txtDescription;
    private JTextField txtStore;
    private JDateChooser dateChooser;

    private Expense createdExpense = null;  // store created expense

    public AddExpenseDialog(JFrame parent) {
        super(parent, "Add New Expense", true);
        setSize(383, 384);
        setLocationRelativeTo(parent);
        getContentPane().setLayout(null);
        setResizable(false);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 20, 100, 25);
        getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setBounds(140, 20, 200, 25);
        getContentPane().add(txtName);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(20, 60, 100, 25);
        getContentPane().add(lblPrice);

        txtPrice = new JTextField();
        txtPrice.setBounds(140, 60, 200, 25);
        getContentPane().add(txtPrice);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(20, 100, 100, 25);
        getContentPane().add(lblType);

        cmbType = new JComboBox<>(ExpenseType.values());
        cmbType.setBounds(140, 100, 200, 25);
        getContentPane().add(cmbType);

        JLabel lblDesc = new JLabel("Description:");
        lblDesc.setBounds(20, 140, 100, 25);
        getContentPane().add(lblDesc);

        txtDescription = new JTextField();
        txtDescription.setBounds(140, 140, 200, 25);
        getContentPane().add(txtDescription);

        JLabel lblStore = new JLabel("Store:");
        lblStore.setBounds(20, 180, 100, 25);
        getContentPane().add(lblStore);

        txtStore = new JTextField();
        txtStore.setBounds(140, 180, 200, 25);
        getContentPane().add(txtStore);

        JCheckBox chkKeepCreating = new JCheckBox("Keep creating");
        chkKeepCreating.setBounds(130, 313, 150, 25);
        getContentPane().add(chkKeepCreating);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBackground(new Color(255, 200, 200));
        btnCancel.setBounds(70, 276, 100, 30);
        btnCancel.addActionListener(e -> dispose());
        getContentPane().add(btnCancel);

        JButton btnCreate = new JButton("Create");
        btnCreate.setBackground(new Color(185, 255, 185));
        btnCreate.setBounds(180, 276, 100, 30);
        btnCreate.addActionListener(e -> {
            if (tryCreateExpense()) {
                if (chkKeepCreating.isSelected()) {
                    clearForm();
                } else {
                    dispose();
                }
            }
        });
        getContentPane().add(btnCreate);
        
        dateChooser = new JDateChooser();
        dateChooser.setBounds(140, 225, 200, 25);
        dateChooser.setDate(new Date());
        getContentPane().add(dateChooser);
        
        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(20, 225, 100, 25);
        getContentPane().add(lblDate);
    }

    private boolean tryCreateExpense() {
        try {
            String name = txtName.getText().trim();
            float price = Float.parseFloat(txtPrice.getText());
            ExpenseType type = (ExpenseType) cmbType.getSelectedItem();
            String desc = txtDescription.getText().trim();
            String store = txtStore.getText().trim();
            Date utilDate = dateChooser.getDate();
            LocalDate date = utilDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            createdExpense = new Expense(name, price, type, desc, store, date);

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
