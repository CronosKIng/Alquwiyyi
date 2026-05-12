import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CreateSubjectPanel extends JPanel {
    private DataManager dm;
    private JTextField subField;
    private JComboBox<String> classCombo;
    private JTable table;
    private DefaultTableModel model;
    
    public CreateSubjectPanel(DataManager dm) {
        this.dm = dm;
        setLayout(new BorderLayout(15,15));
        setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        setBackground(new Color(245,245,245));
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder("CREATE SUBJECT"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,15,10,15);
        
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;
        form.add(new JLabel("Subject Name:"), gbc);
        subField = new JTextField(25);
        subField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(8,10,8,10)));
        gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
        form.add(subField, gbc);
        
        gbc.gridx=0; gbc.gridy=1;
        form.add(new JLabel("Select Class:"), gbc);
        classCombo = new JComboBox<>();
        classCombo.setBackground(Color.WHITE);
        classCombo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshClassCombo();
        gbc.gridx=1;
        form.add(classCombo, gbc);
        
        JButton saveBtn = new JButton("CREATE SUBJECT");
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> saveSubject());
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets=new Insets(25,15,15,15);
        form.add(saveBtn, gbc);
        
        String[] cols = {"ID","Subject","Class"};
        model = new DefaultTableModel(cols,0);
        table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane sp = new JScrollPane(table);
        
        JButton refreshBtn = new JButton("REFRESH");
        refreshBtn.setBackground(Color.WHITE);
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshBtn.addActionListener(e -> refreshAll());
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("ALL SUBJECTS"));
        tablePanel.add(sp, BorderLayout.CENTER);
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bp.add(refreshBtn);
        tablePanel.add(bp, BorderLayout.SOUTH);
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, form, tablePanel);
        split.setResizeWeight(0.35);
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
    
    private void saveSubject() {
        String sub = subField.getText().trim();
        String cls = (String) classCombo.getSelectedItem();
        if (sub.isEmpty() || cls == null || cls.contains("No classes")) {
            JOptionPane.showMessageDialog(this, "Enter subject and ensure class exists");
            return;
        }
        dm.addSubject(new Subject(sub, cls));
        JOptionPane.showMessageDialog(this, "Subject created!");
        subField.setText("");
        refreshTable();
        refreshClassCombo();
    }
    
    private void refreshTable() {
        model.setRowCount(0);
        for (Subject s : dm.getAllSubjects()) {
            model.addRow(new Object[]{s.getId(), s.getSubjectName(), s.getClassName()});
        }
    }
    
    public void refreshAll() {
        refreshClassCombo();
        refreshTable();
        JOptionPane.showMessageDialog(this, "Refreshed!");
    }
}
