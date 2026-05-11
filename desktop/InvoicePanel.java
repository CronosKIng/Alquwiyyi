import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class InvoicePanel extends JPanel {
    private DataManager dataManager;
    private JComboBox<String> studentCombo;
    private JTextField amountField, descField;
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    
    public InvoicePanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Create Invoice"));
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
        
        JButton createBtn = new JButton("CREATE INVOICE");
        createBtn.setBackground(new Color(46, 204, 113));
        createBtn.setForeground(Color.WHITE);
        createBtn.addActionListener(e -> createInvoice());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(createBtn, gbc);
        
        String[] columns = {"ID", "Student Name", "Amount", "Description", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        invoiceTable = new JTable(tableModel);
        refreshTable();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, new JScrollPane(invoiceTable));
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
    
    private void createInvoice() {
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
            JOptionPane.showMessageDialog(this, "Please enter description!");
            return;
        }
        
        Invoice invoice = new Invoice(studentId, studentName, amount, description);
        dataManager.addInvoice(invoice);
        
        JOptionPane.showMessageDialog(this, "Invoice created successfully!\nInvoice ID: " + invoice.getId());
        
        amountField.setText("");
        descField.setText("");
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Invoice inv : dataManager.getAllInvoices()) {
            tableModel.addRow(new Object[]{
                inv.getId(), inv.getStudentName(), inv.getAmount(),
                inv.getDescription(), inv.getDate(), inv.getStatus()
            });
        }
    }
}
