import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CreateClassPanel extends JPanel {
    private DataManager dataManager;
    private JTextField classField, descField;
    private JTable classTable;
    private DefaultTableModel tableModel;
    
    public CreateClassPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("CREATE NEW CLASS"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Class Name:"), gbc);
        classField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(classField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        descField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(descField, gbc);
        
        JButton saveBtn = new JButton("CREATE CLASS");
        saveBtn.addActionListener(e -> saveClass());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(saveBtn, gbc);
        
        String[] columns = {"ID", "Class Name", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        classTable = new JTable(tableModel);
        refreshTable();
        
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(classTable), BorderLayout.CENTER);
    }
    
    private void saveClass() {
        String name = classField.getText().trim();
        String desc = descField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter class name!");
            return;
        }
        dataManager.addClass(new SchoolClass(name, desc));
        classField.setText("");
        descField.setText("");
        refreshTable();
        JOptionPane.showMessageDialog(this, "Class created!");
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        java.util.List<SchoolClass> classes = dataManager.getAllClasses();
        for (SchoolClass c : classes) {
            tableModel.addRow(new Object[]{c.getId(), c.getClassName(), c.getDescription()});
        }
    }
}
