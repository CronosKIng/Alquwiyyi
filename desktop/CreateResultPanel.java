import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class CreateResultPanel extends JPanel {
    private DataManager dataManager;
    private JComboBox<String> classCombo, studentCombo;
    private JPanel marksPanel;
    private Map<String, JTextField> markFields = new HashMap<>();
    private JTextArea resultArea;
    private JButton saveBtn, calculateBtn, printBtn;
    private Result currentResult;
    
    public CreateResultPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(new JLabel("Select Class:"));
        classCombo = new JComboBox<>();
        classCombo.addActionListener(e -> loadStudentsAndSubjects());
        controlPanel.add(classCombo);
        
        controlPanel.add(new JLabel("Select Student:"));
        studentCombo = new JComboBox<>();
        controlPanel.add(studentCombo);
        
        calculateBtn = new JButton("Calculate Grades");
        calculateBtn.addActionListener(e -> calculateResults());
        controlPanel.add(calculateBtn);
        
        saveBtn = new JButton("Save Result");
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.BLACK);
        saveBtn.addActionListener(e -> saveResult());
        controlPanel.add(saveBtn);
        
        printBtn = new JButton("Print Report");
        printBtn.addActionListener(e -> printReport());
        controlPanel.add(printBtn);
        
        add(controlPanel, BorderLayout.NORTH);
        
        marksPanel = new JPanel(new GridBagLayout());
        marksPanel.setBorder(BorderFactory.createTitledBorder("Subject Marks"));
        JScrollPane marksScroll = new JScrollPane(marksPanel);
        marksScroll.setPreferredSize(new Dimension(400, 300));
        
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(BorderFactory.createTitledBorder("Result Summary"));
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, marksScroll, resultScroll);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);
        
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
    
    private void loadStudentsAndSubjects() {
        String className = (String) classCombo.getSelectedItem();
        if (className == null || className.equals("No classes available")) return;
        
        studentCombo.removeAllItems();
        for (Student s : dataManager.getAllStudents()) {
            if (s.getClassName().equals(className)) {
                studentCombo.addItem(s.getFullName() + "|" + s.getId());
            }
        }
        if (studentCombo.getItemCount() == 0) {
            studentCombo.addItem("No students in this class");
        }
        
        marksPanel.removeAll();
        markFields.clear();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        
        for (Subject sub : dataManager.getSubjectsByClass(className)) {
            gbc.gridx = 0;
            marksPanel.add(new JLabel(sub.getSubjectName() + ":"), gbc);
            JTextField markField = new JTextField(8);
            gbc.gridx = 1;
            marksPanel.add(markField, gbc);
            markFields.put(sub.getSubjectName(), markField);
            gbc.gridy++;
        }
        
        marksPanel.revalidate();
        marksPanel.repaint();
    }
    
    private void calculateResults() {
        String className = (String) classCombo.getSelectedItem();
        String studentInfo = (String) studentCombo.getSelectedItem();
        
        if (className == null || studentInfo == null || studentInfo.contains("No students")) {
            JOptionPane.showMessageDialog(this, "Please select class and student");
            return;
        }
        
        String studentName = studentInfo.split("\\|")[0];
        String studentId = studentInfo.split("\\|")[1];
        
        currentResult = new Result(studentId, studentName, className);
        Map<String, Integer> marks = new HashMap<>();
        
        for (Map.Entry<String, JTextField> entry : markFields.entrySet()) {
            try {
                int mark = Integer.parseInt(entry.getValue().getText().trim());
                if (mark < 0 || mark > 100) {
                    JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100 for " + entry.getKey());
                    return;
                }
                marks.put(entry.getKey(), mark);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid marks for " + entry.getKey());
                return;
            }
        }
        
        currentResult.setMarks(marks);
        currentResult.calculateGradeAndDivision();
        
        StringBuilder sb = new StringBuilder();
        sb.append("====================================\n");
        sb.append("STUDENT RESULT REPORT\n");
        sb.append("====================================\n");
        sb.append("Student Name: ").append(studentName).append("\n");
        sb.append("Class: ").append(className).append("\n");
        sb.append("------------------------------------\n");
        sb.append("SUBJECT MARKS:\n");
        for (Map.Entry<String, Integer> entry : marks.entrySet()) {
            sb.append(String.format("  %-20s: %3d\n", entry.getKey(), entry.getValue()));
        }
        sb.append("------------------------------------\n");
        sb.append("Total Marks: ").append(currentResult.getTotalMarks()).append("\n");
        sb.append("Average: ").append(currentResult.getTotalMarks() / marks.size()).append("\n");
        sb.append("Grade: ").append(currentResult.getGrade()).append("\n");
        sb.append("Division: ").append(currentResult.getDivision()).append("\n");
        sb.append("====================================\n");
        
        resultArea.setText(sb.toString());
    }
    
    private void saveResult() {
        if (currentResult == null) {
            JOptionPane.showMessageDialog(this, "Please calculate results first!");
            return;
        }
        
        dataManager.addResult(currentResult);
        JOptionPane.showMessageDialog(this, "Result saved successfully!");
    }
    
    private void printReport() {
        String text = resultArea.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No result to print!");
            return;
        }
        
        JFileChooser chooser = new JFileChooser("reports");
        chooser.setSelectedFile(new java.io.File("result_report.txt"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (java.io.PrintWriter writer = new java.io.PrintWriter(chooser.getSelectedFile())) {
                writer.print(text);
                JOptionPane.showMessageDialog(this, "Report saved successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving report: " + ex.getMessage());
            }
        }
    }
}
