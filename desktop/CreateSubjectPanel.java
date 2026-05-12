import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class CreateSubjectPanel extends JPanel {
    private DataManager dataManager;
    private JTextField subjectField;
    private JComboBox<String> classCombo;
    private JTable subjectTable;
    private DefaultTableModel tableModel;
    
    public CreateSubjectPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(15,15));
        setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        setBackground(new Color(245,245,245));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            "CREATE SUBJECT", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial",Font.BOLD,14), new Color(0,85,170)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,15,10,15);
        
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;
        formPanel.add(new JLabel("Subject Name:"), gbc);
        subjectField = new JTextField(25);
        subjectField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180,180,180)),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.gridy=0;
        formPanel.add(subjectField, gbc);
        
        gbc.gridx=0; gbc.gridy=1;
        formPanel.add(new JLabel("Class:"), gbc);
        classCombo = new JComboBox<>();
        classCombo.setBackground(Color.WHITE);
        classCombo.setBorder(BorderFactory.createLineBorder(new Color(180,180,180)));
        refreshClassCombo();
        gbc.gridx=1; gbc.gridy=1;
        formPanel.add(classCombo, gbc);
        
        JButton saveBtn = new JButton("CREATE SUBJECT");
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setBorder(BorderFactory.createLineBorder(new Color(0,85,170),2));
        saveBtn.addActionListener(e -> saveSubject());
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets = new Insets(25,15,15,15);
        formPanel.add(saveBtn, gbc);
        
        String[] cols = {"ID","Subject Name","Class"};
        tableModel = new DefaultTableModel(cols,0);
        subjectTable = new JTable(tableModel);
        subjectTable.setRowHeight(30);
        subjectTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            "ALL SUBJECTS", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial",Font.BOLD,14), new Color(0,85,170)));
        tablePanel.add(new JScrollPane(subjectTable), BorderLayout.CENTER);
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel);
        split.setResizeWeight(0.35);
        split.setBorder(null);
        add(split, BorderLayout.CENTER);
        
        refreshTable();
    }
    
    private void saveSubject() {
        String sub = subjectField.getText().trim();
        String className = (String) classCombo.getSelectedItem();
        if (sub.isEmpty() || className == null || className.contains("No classes")) {
            JOptionPane.showMessageDialog(this, "Enter subject and ensure classes exist!");
            return;
        }
        dataManager.addSubject(new Subject(sub, className));
        JOptionPane.showMessageDialog(this, "Subject created!");
        subjectField.setText("");
        refreshTable();
        refreshClassCombo();
    }
    
    private void refreshClassCombo() {
        classCombo.removeAllItems();
        java.util.List<Class> classes = dataManager.getAllClasses();
        if (classes.isEmpty()) classCombo.addItem("No classes - create class first");
        else for (Class c : classes) classCombo.addItem(c.getClassName());
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Subject s : dataManager.getAllSubjects()) {
            tableModel.addRow(new Object[]{s.getId(), s.getSubjectName(), s.getClassName()});
        }
    }
}
