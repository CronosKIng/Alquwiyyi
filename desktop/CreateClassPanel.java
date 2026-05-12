import javax.swing.*;
import javax.swing.border.*;
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
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        setBackground(new Color(245, 245, 245));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "CREATE NEW CLASS", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(0, 85, 170)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        
        JLabel classLabel = new JLabel("Class Name:");
        classLabel.setFont(new Font("Arial", Font.BOLD, 13));
        classLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(classLabel, gbc);
        
        classField = new JTextField(25);
        classField.setFont(new Font("Arial", Font.PLAIN, 13));
        classField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(classField, gbc);
        
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Arial", Font.BOLD, 13));
        descLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(descLabel, gbc);
        
        descField = new JTextField(25);
        descField.setFont(new Font("Arial", Font.PLAIN, 13));
        descField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(descField, gbc);
        
        JButton saveBtn = new JButton("CREATE CLASS");
        saveBtn.setBackground(new Color(0, 85, 170));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        saveBtn.addActionListener(e -> saveClass());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 15, 15, 15);
        formPanel.add(saveBtn, gbc);
        
        String[] columns = {"ID", "Class Name", "Description", "Created Date"};
        tableModel = new DefaultTableModel(columns, 0);
        classTable = new JTable(tableModel);
        classTable.setRowHeight(35);
        classTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        classTable.getTableHeader().setBackground(new Color(48, 54, 79));
        classTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(classTable);
        
        JButton refreshBtn = new JButton("REFRESH");
        refreshBtn.setBackground(new Color(108, 117, 125));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 12));
        refreshBtn.setFocusPainted(false);
        refreshBtn.addActionListener(e -> refreshTable());
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(refreshBtn);
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "ALL CLASSES", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14), new Color(0, 85, 170)));
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        tableContainer.add(bottomPanel, BorderLayout.SOUTH);
        
        refreshTable();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tableContainer);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(null);
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
        JOptionPane.showMessageDialog(this, "Class created successfully!\nClass: " + className);
        
        classField.setText("");
        descField.setText("");
        refreshTable();
        dataManager.loadAllData();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        for (Class c : dataManager.getAllClasses()) {
            tableModel.addRow(new Object[]{
                c.getId(), c.getClassName(), c.getDescription(), currentDate
            });
        }
    }
}
