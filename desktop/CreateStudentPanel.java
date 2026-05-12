import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CreateStudentPanel extends JPanel {
    private DataManager dm;
    private JTextField nameField, parentField, phoneField;
    private JComboBox<String> classCombo;
    private JTable table;
    private DefaultTableModel model;
    
    public CreateStudentPanel(DataManager dm) {
        this.dm = dm;
        setLayout(new BorderLayout(15,15));
        setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        setBackground(new Color(245,245,245));
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder("ADD NEW STUDENT"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,15,10,15);
        
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;
        form.add(new JLabel("Full Name:"), gbc);
        nameField = new JTextField(25);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
        form.add(nameField, gbc);
        
        gbc.gridx=0; gbc.gridy=1;
        form.add(new JLabel("Class:"), gbc);
        classCombo = new JComboBox<>();
        classCombo.setBackground(Color.WHITE);
        classCombo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshClassCombo();
        gbc.gridx=1;
        form.add(classCombo, gbc);
        
        gbc.gridx=0; gbc.gridy=2;
        form.add(new JLabel("Parent Name:"), gbc);
        parentField = new JTextField(25);
        parentField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1;
        form.add(parentField, gbc);
        
        gbc.gridx=0; gbc.gridy=3;
        form.add(new JLabel("Phone:"), gbc);
        phoneField = new JTextField(20);
        phoneField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1;
        form.add(phoneField, gbc);
        
        JButton saveBtn = new JButton("SAVE STUDENT");
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> saveStudent());
        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets=new Insets(25,15,15,15);
        form.add(saveBtn, gbc);
        
        String[] cols = {"ID","Name","Class","Parent","Phone","Date"};
        model = new DefaultTableModel(cols,0);
        table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane sp = new JScrollPane(table);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("REGISTERED STUDENTS"));
        tablePanel.add(sp, BorderLayout.CENTER);
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, form, tablePanel);
        split.setResizeWeight(0.4);
        add(split);
        
        refreshTable();
    }
    
    public void refreshClassCombo() {
        classCombo.removeAllItems();
        List<Class> classes = dm.getAllClasses();
        if (classes.isEmpty()) {
            classCombo.addItem("No classes - Create class first");
        } else {
            for (Class c : classes) classCombo.addItem(c.getClassName());
        }
    }
    
    private void saveStudent() {
        String name = nameField.getText().trim();
        String cls = (String) classCombo.getSelectedItem();
        if (name.isEmpty() || cls == null || cls.contains("No classes")) {
            JOptionPane.showMessageDialog(this, "Fill all fields and ensure class exists");
            return;
        }
        dm.addStudent(new Student(name, cls, parentField.getText().trim(), phoneField.getText().trim()));
        JOptionPane.showMessageDialog(this, "Student saved!");
        nameField.setText(""); parentField.setText(""); phoneField.setText("");
        refreshTable();
        refreshClassCombo();
    }
    
    private void refreshTable() {
        model.setRowCount(0);
        for (Student s : dm.getAllStudents()) {
            model.addRow(new Object[]{s.getId(), s.getFullName(), s.getClassName(), 
                        s.getParentName(), s.getParentPhone(), s.getAdmissionDate()});
        }
    }
}
