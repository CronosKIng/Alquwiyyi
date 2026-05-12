import java.util.List;
import javax.swing.border.TitledBorder;
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
        
        String[] columns = {"ID", "Full Name", "Class", "Parent Name", "Phone", "Admission Date"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        refreshTable();
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton exportBtn = new JButton("Export to Excel");
        exportBtn.setBackground(Color.WHITE);
        exportBtn.setForeground(Color.BLACK);
        exportBtn.addActionListener(e -> exportToExcel());
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(exportBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : dataManager.getAllStudents()) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getFullName(), s.getClassName(),
                s.getParentName(), s.getParentPhone(), s.getAdmissionDate()
            });
        }
        searchField.setText("");
    }
    
    private void searchStudents() {
        String search = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (Student s : dataManager.getAllStudents()) {
            if (s.getFullName().toLowerCase().contains(search) ||
                s.getId().toLowerCase().contains(search) ||
                s.getParentName().toLowerCase().contains(search)) {
                tableModel.addRow(new Object[]{
                    s.getId(), s.getFullName(), s.getClassName(),
                    s.getParentName(), s.getParentPhone(), s.getAdmissionDate()
                });
            }
        }
    }
    
    private void exportToExcel() {
        JFileChooser chooser = new JFileChooser("reports");
        chooser.setSelectedFile(new File("students_list.xls"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(chooser.getSelectedFile())) {
                writer.println("ID\tFull Name\tClass\tParent Name\tPhone\tAdmission Date");
                for (Student s : dataManager.getAllStudents()) {
                    writer.printf("%s\t%s\t%s\t%s\t%s\t%s%n",
                        s.getId(), s.getFullName(), s.getClassName(),
                        s.getParentName(), s.getParentPhone(), s.getAdmissionDate());
                }
                JOptionPane.showMessageDialog(this, "Exported successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting: " + ex.getMessage());
            }
        }
    }
}
