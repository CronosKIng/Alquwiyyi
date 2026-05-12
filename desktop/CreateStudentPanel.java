import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class CreateStudentPanel extends JPanel {
    private DataManager dataManager;
    private JTextField nameField, parentNameField, phoneField;
    private JComboBox<String> classCombo;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    
    public CreateStudentPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Student"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Full Name:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        JPanel classPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        classCombo = new JComboBox<>();
        classCombo.setPreferredSize(new Dimension(150, 25));
        classPanel.add(classCombo);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        refreshBtn.setBackground(new Color(100, 100, 100));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> refreshClassCombo());
        classPanel.add(refreshBtn);
        formPanel.add(new JLabel("Class:"), gbc);
        gbc.gridx = 1;
        formPanel.add(classPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Parent Name:"), gbc);
        parentNameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(parentNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Phone Number:"), gbc);
        phoneField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        
        JButton saveBtn = new JButton("SAVE STUDENT");
        saveBtn.setBackground(new Color(0, 120, 215));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> saveStudent());
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(saveBtn, gbc);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("All Registered Students"));
        
        String[] columns = {"ID", "Full Name", "Class", "Parent", "Phone", "Admission Date"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        refreshTable();
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel);
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);
        
        refreshClassCombo();
    }
    
    private void refreshClassCombo() {
        classCombo.removeAllItems();
        java.util.List<Class> classes = dataManager.getAllClasses();
        if (classes.isEmpty()) {
            classCombo.addItem("-- No Classes Available --");
        } else {
            for (Class c : classes) {
                classCombo.addItem(c.getClassName());
            }
        }
    }
    
    private void saveStudent() {
        String name = nameField.getText().trim();
        String className = (String) classCombo.getSelectedItem();
        String parentName = parentNameField.getText().trim();
        String phone = phoneField.getText().trim();
        
        if (className == null || className.equals("-- No Classes Available --")) {
            JOptionPane.showMessageDialog(this, "No classes available! Please create a class first.");
            return;
        }
        
        if (name.isEmpty() || parentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }
        
        Student student = new Student(name, className, parentName, phone);
        dataManager.addStudent(student);
        JOptionPane.showMessageDialog(this, "Student added successfully!\nID: " + student.getId());
        
        nameField.setText("");
        parentNameField.setText("");
        phoneField.setText("");
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : dataManager.getAllStudents()) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getFullName(), s.getClassName(),
                s.getParentName(), s.getParentPhone(), s.getAdmissionDate()
            });
        }
    }
}
