import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

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
        formPanel.setBorder(BorderFactory.createTitledBorder("Create New Class"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
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
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> saveClass());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(saveBtn, gbc);
        
        String[] columns = {"ID", "Class Name", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        classTable = new JTable(tableModel);
        refreshTable();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, new JScrollPane(classTable));
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void saveClass() {
        String className = classField.getText().trim();
        String description = descField.getText().trim();
        
        if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter class name!");
            return;
        }
        
        dataManager.addClass(new Class(className, description));
        JOptionPane.showMessageDialog(this, "Class created successfully!");
        classField.setText("");
        descField.setText("");
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Class c : dataManager.getAllClasses()) {
            tableModel.addRow(new Object[]{c.getId(), c.getClassName(), c.getDescription()});
        }
    }
}
