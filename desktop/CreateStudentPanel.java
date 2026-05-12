import javax.swing.*;
import javax.swing.border.*;
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
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        setBackground(new Color(245, 245, 245));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "ADD NEW STUDENT", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(0, 85, 170)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(nameLabel, gbc);
        
        nameField = new JTextField(25);
        nameField.setFont(new Font("Arial", Font.PLAIN, 13));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);
        
        JLabel classLabel = new JLabel("Class:");
        classLabel.setFont(new Font("Arial", Font.BOLD, 13));
        classLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(classLabel, gbc);
        
        classCombo = new JComboBox<>();
        classCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        classCombo.setBackground(Color.WHITE);
        classCombo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        refreshClassCombo();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(classCombo, gbc);
        
        JLabel parentLabel = new JLabel("Parent Name:");
        parentLabel.setFont(new Font("Arial", Font.BOLD, 13));
        parentLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(parentLabel, gbc);
        
        parentNameField = new JTextField(25);
        parentNameField.setFont(new Font("Arial", Font.PLAIN, 13));
        parentNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(parentNameField, gbc);
        
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 13));
        phoneLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(phoneLabel, gbc);
        
        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 13));
        phoneField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(phoneField, gbc);
        
        JButton saveBtn = new JButton("SAVE STUDENT");
        saveBtn.setBackground(new Color(0, 85, 170));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        saveBtn.addActionListener(e -> saveStudent());
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 15, 15, 15);
        formPanel.add(saveBtn, gbc);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "REGISTERED STUDENTS", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(0, 85, 170)));
        
        String[] columns = {"ID", "Full Name", "Class", "Parent", "Phone", "Admission Date"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(35);
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(48, 54, 79));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        refreshTable();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel);
        splitPane.setResizeWeight(0.4);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void saveStudent() {
        String name = nameField.getText().trim();
        String className = (String) classCombo.getSelectedItem();
        String parentName = parentNameField.getText().trim();
        String phone = phoneField.getText().trim();
        
        if (name.isEmpty() || className == null || parentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }
        
        if (className.equals("No classes available - Create class first")) {
            JOptionPane.showMessageDialog(this, "Please create a class first!");
            return;
        }
        
        Student student = new Student(name, className, parentName, phone);
        dataManager.addStudent(student);
        
        JOptionPane.showMessageDialog(this, "Student added successfully!\nID: " + student.getId());
        
        nameField.setText("");
        parentNameField.setText("");
        phoneField.setText("");
        refreshTable();
        refreshClassCombo();
    }
    
    public void refreshClassCombo() {
        classCombo.removeAllItems();
        java.util.List<Class> classes = dataManager.getAllClasses();
        
        if (classes.isEmpty()) {
            classCombo.addItem("No classes available - Create class first");
        } else {
            for (Class c : classes) {
                classCombo.addItem(c.getClassName());
            }
        }
        classCombo.repaint();
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
