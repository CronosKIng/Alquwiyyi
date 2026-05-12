import javax.swing.*;
import javax.swing.border.*;
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
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        setBackground(new Color(245, 245, 245));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "CREATE NEW SUBJECT", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(0, 85, 170)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        
        JLabel subLabel = new JLabel("Subject Name:");
        subLabel.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(subLabel, gbc);
        
        subjectField = new JTextField(25);
        subjectField.setFont(new Font("Arial", Font.PLAIN, 13));
        subjectField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(subjectField, gbc);
        
        JLabel classLabel = new JLabel("Select Class:");
        classLabel.setFont(new Font("Arial", Font.BOLD, 13));
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
        
        JButton saveBtn = new JButton("CREATE SUBJECT");
        saveBtn.setBackground(new Color(0, 85, 170));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        saveBtn.addActionListener(e -> saveSubject());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 15, 15, 15);
        formPanel.add(saveBtn, gbc);
        
        String[] columns = {"ID", "Subject Name", "Class"};
        tableModel = new DefaultTableModel(columns, 0);
        subjectTable = new JTable(tableModel);
        subjectTable.setRowHeight(35);
        subjectTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        subjectTable.getTableHeader().setBackground(new Color(48, 54, 79));
        subjectTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(subjectTable);
        
        JButton refreshBtn = new JButton("REFRESH");
        refreshBtn.setBackground(new Color(108, 117, 125));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> refreshAll());
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "ALL SUBJECTS", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(0, 85, 170)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(refreshBtn);
        tablePanel.add(bottomPanel, BorderLayout.SOUTH);
        
        refreshTable();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);
    }
    
    public void refreshClassCombo() {
        classCombo.removeAllItems();
        java.util.List<Class> classes = dataManager.getAllClasses();
        
        if (classes.isEmpty()) {
            classCombo.addItem("No classes - Create class first");
            classCombo.setEnabled(false);
        } else {
            classCombo.setEnabled(true);
            for (Class c : classes) {
                classCombo.addItem(c.getClassName());
            }
        }
        classCombo.repaint();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Subject s : dataManager.getAllSubjects()) {
            tableModel.addRow(new Object[]{s.getId(), s.getSubjectName(), s.getClassName()});
        }
    }
    
    public void refreshAll() {
        refreshClassCombo();
        refreshTable();
        JOptionPane.showMessageDialog(this, "Data refreshed!");
    }
    
    private void saveSubject() {
        String subjectName = subjectField.getText().trim();
        String className = (String) classCombo.getSelectedItem();
        
        if (subjectName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter subject name!");
            return;
        }
        
        if (className == null || className.equals("No classes - Create class first")) {
            JOptionPane.showMessageDialog(this, "Please create a class first before adding subjects!");
            return;
        }
        
        dataManager.addSubject(new Subject(subjectName, className));
        JOptionPane.showMessageDialog(this, "Subject created successfully!");
        
        subjectField.setText("");
        refreshTable();
        refreshClassCombo();
    }
}
