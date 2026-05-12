import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PaymentPanel extends JPanel {
    private DataManager dataManager;
    private JComboBox<String> studentCombo;
    private JTextField amountField, descField;
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    
    public PaymentPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Record Payment"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Select Student:"), gbc);
        studentCombo = new JComboBox<>();
        refreshStudentCombo();
        gbc.gridx = 1;
        formPanel.add(studentCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Amount:"), gbc);
        amountField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(amountField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Description:"), gbc);
        descField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(descField, gbc);
        
        JButton payBtn = new JButton("RECORD PAYMENT");
        payBtn.setBackground(Color.WHITE);
        payBtn.setForeground(Color.BLACK);
        payBtn.addActionListener(e -> recordPayment());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(payBtn, gbc);
        
        String[] columns = {"ID", "Student Name", "Amount", "Description", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        paymentTable = new JTable(tableModel);
        refreshTable();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, new JScrollPane(paymentTable));
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void refreshStudentCombo() {
        studentCombo.removeAllItems();
        for (Student s : dataManager.getAllStudents()) {
            studentCombo.addItem(s.getFullName() + "|" + s.getId());
        }
        if (studentCombo.getItemCount() == 0) {
            studentCombo.addItem("No students available");
        }
    }
    
    private void recordPayment() {
        String studentInfo = (String) studentCombo.getSelectedItem();
        if (studentInfo == null || studentInfo.equals("No students available")) {
            JOptionPane.showMessageDialog(this, "Please add students first!");
            return;
        }
        
        String studentName = studentInfo.split("\\|")[0];
        String studentId = studentInfo.split("\\|")[1];
        
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid amount!");
            return;
        }
        
        String description = descField.getText().trim();
        if (description.isEmpty()) {
            description = "School fee payment";
        }
        
        Payment payment = new Payment(studentId, studentName, amount, description);
        dataManager.addPayment(payment);
        
        JOptionPane.showMessageDialog(this, "Payment recorded successfully!\nPayment ID: " + payment.getId());
        
        amountField.setText("");
        descField.setText("");
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        java.util.List<Payment> payments = dataManager.getAllPayments();
        if (payments != null) {
            for (Payment p : payments) {
                tableModel.addRow(new Object[]{
                    p.getId(), p.getStudentName(), p.getAmount(),
                    p.getDescription(), p.getDate()
                });
            }
        }
    }
}
