import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CreateStudentPanel extends JPanel {
    private DataManager dataManager;
    private JTextField nameField, parentField, phoneField;
    private JComboBox<String> classCombo;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    
    public CreateStudentPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("ADD NEW STUDENT"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Full Name:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Class:"), gbc);
        classCombo = new JComboBox<>();
        refreshClassCombo();
        gbc.gridx = 1;
        formPanel.add(classCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Parent Name:"), gbc);
        parentField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(parentField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        phoneField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        
        JButton saveBtn = new JButton("SAVE STUDENT");
        saveBtn.addActionListener(e -> saveStudent());
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(saveBtn, gbc);
        
        String[] columns = {"ID", "Name", "Class", "Parent", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        refreshTable();
        
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
    }
    
    private void saveStudent() {
        String name = nameField.getText().trim();
        String className = (String) classCombo.getSelectedItem();
        String parent = parentField.getText().trim();
        String phone = phoneField.getText().trim();
        if (name.isEmpty() || className == null) return;
        dataManager.addStudent(new Student(name, className, parent, phone));
        nameField.setText("");
        parentField.setText("");
        phoneField.setText("");
        refreshTable();
        JOptionPane.showMessageDialog(this, "Student added!");
    }
    
    public void refreshClassCombo() {
        classCombo.removeAllItems();
        for (SchoolClass c : dataManager.getAllClasses()) {
            classCombo.addItem(c.getClassName());
        }
        if (classCombo.getItemCount() == 0) {
            classCombo.addItem("No classes - Create class first");
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : dataManager.getAllStudents()) {
            tableModel.addRow(new Object[]{s.getId(), s.getFullName(), s.getClassName(), s.getParentName(), s.getParentPhone()});
        }
    }
}
