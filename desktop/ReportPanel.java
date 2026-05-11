import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class ReportPanel extends JPanel {
    private DataManager dataManager;
    private JComboBox<String> classCombo;
    private JTextArea reportArea;
    
    public ReportPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(new JLabel("Select Class:"));
        classCombo = new JComboBox<>();
        classCombo.addActionListener(e -> generateReport());
        controlPanel.add(classCombo);
        
        JButton printBtn = new JButton("Print Report");
        printBtn.addActionListener(e -> printReport());
        controlPanel.add(printBtn);
        
        JButton excelBtn = new JButton("Export to Excel");
        excelBtn.addActionListener(e -> exportToExcel());
        controlPanel.add(excelBtn);
        
        add(controlPanel, BorderLayout.NORTH);
        
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);
        
        refreshClassCombo();
    }
    
    private void refreshClassCombo() {
        classCombo.removeAllItems();
        for (Class c : dataManager.getAllClasses()) {
            classCombo.addItem(c.getClassName());
        }
        if (classCombo.getItemCount() == 0) {
            classCombo.addItem("No classes available");
        }
    }
    
    private void generateReport() {
        String className = (String) classCombo.getSelectedItem();
        if (className == null || className.equals("No classes available")) {
            reportArea.setText("No classes available. Please create classes first.");
            return;
        }
        
        List<Result> classResults = dataManager.getResultsByClass(className);
        
        if (classResults.isEmpty()) {
            reportArea.setText("No results available for class: " + className);
            return;
        }
        
        Collections.sort(classResults, (a, b) -> Integer.compare(b.getTotalMarks(), a.getTotalMarks()));
        
        int position = 1;
        for (Result r : classResults) {
            r.setPosition(position++);
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================\n");
        sb.append("                    FINAL REPORT FOR CLASS: ").append(className).append("\n");
        sb.append("========================================================================\n");
        sb.append(String.format("%-5s %-25s %-10s %-15s %-10s%n", 
            "Pos", "Student Name", "Total", "Grade", "Division"));
        sb.append("------------------------------------------------------------------------\n");
        
        for (Result r : classResults) {
            sb.append(String.format("%-5d %-25s %-10d %-10s %-15s%n",
                r.getPosition(),
                r.getStudentName().length() > 25 ? r.getStudentName().substring(0, 22) + "..." : r.getStudentName(),
                r.getTotalMarks(),
                r.getGrade(),
                r.getDivision()));
        }
        
        sb.append("========================================================================\n");
        
        reportArea.setText(sb.toString());
    }
    
    private void printReport() {
        String text = reportArea.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No report to print!");
            return;
        }
        
        JFileChooser chooser = new JFileChooser("reports");
        chooser.setSelectedFile(new File("class_report.txt"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(chooser.getSelectedFile())) {
                writer.print(text);
                JOptionPane.showMessageDialog(this, "Report saved successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving report: " + ex.getMessage());
            }
        }
    }
    
    private void exportToExcel() {
        String className = (String) classCombo.getSelectedItem();
        if (className == null || className.equals("No classes available")) {
            JOptionPane.showMessageDialog(this, "Please select a class!");
            return;
        }
        
        List<Result> classResults = dataManager.getResultsByClass(className);
        if (classResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No results available for this class!");
            return;
        }
        
        JFileChooser chooser = new JFileChooser("reports");
        chooser.setSelectedFile(new File(className + "_results.xls"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(chooser.getSelectedFile())) {
                writer.println("Position\tStudent Name\tTotal Marks\tGrade\tDivision");
                for (Result r : classResults) {
                    writer.printf("%d\t%s\t%d\t%s\t%s%n",
                        r.getPosition(), r.getStudentName(), 
                        r.getTotalMarks(), r.getGrade(), r.getDivision());
                }
                JOptionPane.showMessageDialog(this, "Exported successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting: " + ex.getMessage());
            }
        }
    }
}
