import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AllStudentsPanel extends JPanel {
    private DataManager dataManager;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    public AllStudentsPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                searchStudents();
            }
        });
        searchPanel.add(searchField);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());
        searchPanel.add(refreshBtn);
        
        add(searchPanel, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Full Name", "Class", "Parent Name", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        refreshTable();
        
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : dataManager.getAllStudents()) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getFullName(), s.getClassName(),
                s.getParentName(), s.getParentPhone()
            });
        }
        searchField.setText("");
    }
    
    private void searchStudents() {
        String search = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (Student s : dataManager.getAllStudents()) {
            if (s.getFullName().toLowerCase().contains(search) ||
                s.getId().toLowerCase().contains(search)) {
                tableModel.addRow(new Object[]{
                    s.getId(), s.getFullName(), s.getClassName(),
                    s.getParentName(), s.getParentPhone()
                });
            }
        }
    }
}
