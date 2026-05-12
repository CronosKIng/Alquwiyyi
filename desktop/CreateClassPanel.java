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
        formPanel.setBorder(BorderFactory.createTitledBorder("CREATE NEW CLASS"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,15,10,15);
        
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;
        formPanel.add(new JLabel("Class Name:"), gbc);
        classField = new JTextField(25);
        classField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
        formPanel.add(classField, gbc);
        
        gbc.gridx=0; gbc.gridy=1;
        formPanel.add(new JLabel("Description:"), gbc);
        descField = new JTextField(25);
        descField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1;
        formPanel.add(descField, gbc);
        
        JButton saveBtn = new JButton("CREATE CLASS");
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.addActionListener(e -> saveClass());
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets=new Insets(25,15,15,15);
        formPanel.add(saveBtn, gbc);
        
        String[] cols = {"ID", "Class Name", "Description"};
        tableModel = new DefaultTableModel(cols, 0);
        classTable = new JTable(tableModel);
        classTable.setRowHeight(30);
        JScrollPane sp = new JScrollPane(classTable);
        
        JButton refreshBtn = new JButton("REFRESH");
        refreshBtn.setBackground(Color.WHITE);
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshBtn.addActionListener(e -> refreshTable());
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("ALL CLASSES"));
        tablePanel.add(sp, BorderLayout.CENTER);
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bp.add(refreshBtn);
        tablePanel.add(bp, BorderLayout.SOUTH);
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel);
        split.setResizeWeight(0.35);
        add(split);
        
        refreshTable();
    }
    
    private void saveClass() {
        String name = classField.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter class name"); return; }
        dataManager.addClass(new Class(name, descField.getText().trim()));
        JOptionPane.showMessageDialog(this, "Class created!");
        classField.setText(""); descField.setText("");
        refreshTable();
    }
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Class c : dataManager.getAllClasses()) {
            tableModel.addRow(new Object[]{c.getId(), c.getClassName(), c.getDescription()});
        }
    }
}
