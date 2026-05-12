import javax.swing.border.TitledBorder;
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
        setLayout(new BorderLayout(15,15));
        setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        setBackground(new Color(245,245,245));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            "CREATE NEW CLASS", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial",Font.BOLD,14), new Color(0,85,170)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,15,10,15);
        
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;
        formPanel.add(new JLabel("Class Name:"), gbc);
        classField = new JTextField(25);
        classField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180,180,180)),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST;
        formPanel.add(classField, gbc);
        
        gbc.gridx=0; gbc.gridy=1;
        formPanel.add(new JLabel("Description:"), gbc);
        descField = new JTextField(25);
        descField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180,180,180)),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.gridy=1;
        formPanel.add(descField, gbc);
        
        JButton saveBtn = new JButton("CREATE CLASS");
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setFont(new Font("Arial",Font.BOLD,14));
        saveBtn.setBorder(BorderFactory.createLineBorder(new Color(0,85,170),2));
        saveBtn.addActionListener(e -> saveClass());
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets = new Insets(25,15,15,15);
        formPanel.add(saveBtn, gbc);
        
        String[] cols = {"ID","Class Name","Description"};
        tableModel = new DefaultTableModel(cols,0);
        classTable = new JTable(tableModel);
        classTable.setRowHeight(30);
        classTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        classTable.getTableHeader().setBackground(new Color(48,54,79));
        classTable.getTableHeader().setForeground(Color.WHITE);
        
        JButton refreshBtn = new JButton("REFRESH");
        refreshBtn.setBackground(Color.WHITE);
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        refreshBtn.addActionListener(e -> refreshTable());
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(refreshBtn);
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            "ALL CLASSES", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial",Font.BOLD,14), new Color(0,85,170)));
        tableContainer.add(new JScrollPane(classTable), BorderLayout.CENTER);
        tableContainer.add(bottom, BorderLayout.SOUTH);
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tableContainer);
        split.setResizeWeight(0.35);
        split.setBorder(null);
        add(split, BorderLayout.CENTER);
        
        refreshTable();
    }
    
    private void saveClass() {
        String name = classField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter class name!");
            return;
        }
        dataManager.addClass(new Class(name, descField.getText().trim()));
        JOptionPane.showMessageDialog(this, "Class created: " + name);
        classField.setText("");
        descField.setText("");
        refreshTable();
        // Force reload in other panels will happen when they refresh on visibility
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Class c : dataManager.getAllClasses()) {
            tableModel.addRow(new Object[]{c.getId(), c.getClassName(), c.getDescription()});
        }
    }
}
