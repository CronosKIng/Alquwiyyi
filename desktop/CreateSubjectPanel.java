import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CreateSubjectPanel extends JPanel {
    private DataManager dataManager;
    private JTextField subjectField;
    private JComboBox<String> classCombo;
    private JTable subjectTable;
    private DefaultTableModel tableModel;
    
    public CreateSubjectPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("CREATE SUBJECT"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Subject Name:"), gbc);
        subjectField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(subjectField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Class:"), gbc);
        classCombo = new JComboBox<>();
        refreshClassCombo();
        gbc.gridx = 1;
        formPanel.add(classCombo, gbc);
        
        JButton saveBtn = new JButton("CREATE SUBJECT");
        saveBtn.addActionListener(e -> saveSubject());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(saveBtn, gbc);
        
        String[] columns = {"ID", "Subject", "Class"};
        tableModel = new DefaultTableModel(columns, 0);
        subjectTable = new JTable(tableModel);
        refreshTable();
        
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(subjectTable), BorderLayout.CENTER);
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
        for (Subject s : dataManager.getAllSubjects()) {
            tableModel.addRow(new Object[]{s.getId(), s.getSubjectName(), s.getClassName()});
        }
    }
    
    private void saveSubject() {
        String subject = subjectField.getText().trim();
        String className = (String) classCombo.getSelectedItem();
        if (subject.isEmpty()) return;
        if (className == null || className.equals("No classes - Create class first")) {
            JOptionPane.showMessageDialog(this, "Create a class first!");
            return;
        }
        dataManager.addSubject(new Subject(subject, className));
        subjectField.setText("");
        refreshTable();
        JOptionPane.showMessageDialog(this, "Subject created!");
    }
}
