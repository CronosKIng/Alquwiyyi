import javax.swing.border.TitledBorder;
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
        setLayout(new BorderLayout(15,15));
        setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        setBackground(new Color(245,245,245));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            "ADD NEW STUDENT", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial",Font.BOLD,14), new Color(0,85,170)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,15,10,15);
        
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;
        formPanel.add(new JLabel("Full Name:"), gbc);
        nameField = new JTextField(25);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180,180,180)),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);
        
        gbc.gridx=0; gbc.gridy=1;
        formPanel.add(new JLabel("Class:"), gbc);
        classCombo = new JComboBox<>();
        classCombo.setBackground(Color.WHITE);
        classCombo.setBorder(BorderFactory.createLineBorder(new Color(180,180,180)));
        refreshClassCombo();
        gbc.gridx=1; gbc.gridy=1;
        formPanel.add(classCombo, gbc);
        
        gbc.gridx=0; gbc.gridy=2;
        formPanel.add(new JLabel("Parent Name:"), gbc);
        parentNameField = new JTextField(25);
        parentNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180,180,180)),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.gridy=2;
        formPanel.add(parentNameField, gbc);
        
        gbc.gridx=0; gbc.gridy=3;
        formPanel.add(new JLabel("Phone Number:"), gbc);
        phoneField = new JTextField(20);
        phoneField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180,180,180)),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.gridy=3;
        formPanel.add(phoneField, gbc);
        
        JButton saveBtn = new JButton("SAVE STUDENT");
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setBorder(BorderFactory.createLineBorder(new Color(0,85,170),2));
        saveBtn.addActionListener(e -> saveStudent());
        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets = new Insets(25,15,15,15);
        formPanel.add(saveBtn, gbc);
        
        String[] cols = {"ID","Full Name","Class","Parent","Phone","Admission Date"};
        tableModel = new DefaultTableModel(cols,0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(35);
        studentTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        studentTable.getTableHeader().setBackground(new Color(48,54,79));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            "REGISTERED STUDENTS", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial",Font.BOLD,14), new Color(0,85,170)));
        tablePanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel);
        split.setResizeWeight(0.4);
        split.setBorder(null);
        add(split, BorderLayout.CENTER);
        
        refreshTable();
    }
    
    private void saveStudent() {
        String name = nameField.getText().trim();
        String className = (String) classCombo.getSelectedItem();
        if (name.isEmpty() || className == null || className.contains("No classes")) {
            JOptionPane.showMessageDialog(this, "Fill all fields and ensure classes exist!");
            return;
        }
        Student s = new Student(name, className, parentNameField.getText().trim(), phoneField.getText().trim());
        dataManager.addStudent(s);
        JOptionPane.showMessageDialog(this, "Student added! ID: " + s.getId());
        nameField.setText(""); parentNameField.setText(""); phoneField.setText("");
        refreshTable();
        refreshClassCombo(); // keep combo updated
    }
    
    public void refreshClassCombo() {
        classCombo.removeAllItems();
        java.util.List<Class> classes = dataManager.getAllClasses();
        if (classes.isEmpty()) {
            classCombo.addItem("No classes - create class first");
        } else {
            for (Class c : classes) classCombo.addItem(c.getClassName());
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : dataManager.getAllStudents()) {
            tableModel.addRow(new Object[]{s.getId(), s.getFullName(), s.getClassName(),
                s.getParentName(), s.getParentPhone(), s.getAdmissionDate()});
        }
    }
}
