import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private DataManager dataManager;
    private CreateStudentPanel studentPanel;
    private CreateSubjectPanel subjectPanel;
    private CreateClassPanel classPanel;
    
    public DashboardFrame() {
        dataManager = new DataManager();
        dataManager.loadAllData();
        
        setTitle("AL-QUWIYYI - School Management Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        JPanel sidebar = createSidebar();
        sidebar.setPreferredSize(new Dimension(280, getHeight()));
        sidebar.setBackground(new Color(36, 40, 59));
        add(sidebar, BorderLayout.WEST);
        
        JPanel topBar = createTopBar();
        topBar.setBackground(new Color(0, 85, 170));
        topBar.setPreferredSize(new Dimension(getWidth(), 70));
        add(topBar, BorderLayout.NORTH);
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 245, 245));
        
        // Create panels
        studentPanel = new CreateStudentPanel(dataManager);
        subjectPanel = new CreateSubjectPanel(dataManager);
        classPanel = new CreateClassPanel(dataManager);
        
        contentPanel.add(studentPanel, "createStudent");
        contentPanel.add(new AllStudentsPanel(dataManager), "allStudents");
        contentPanel.add(subjectPanel, "createSubject");
        contentPanel.add(classPanel, "createClass");
        contentPanel.add(new CreateResultPanel(dataManager), "createResult");
        contentPanel.add(new InvoicePanel(dataManager), "invoice");
        contentPanel.add(new ReportPanel(dataManager), "report");
        contentPanel.add(new PaymentPanel(dataManager), "payment");
        
        add(contentPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        String[] menuItems = {
            "CREATE STUDENT", "ALL STUDENTS", "CREATE SUBJECT",
            "CREATE CLASS", "CREATE RESULT", "INVOICE",
            "REPORT", "PAYMENT"
        };
        
        String[] cardNames = {
            "createStudent", "allStudents", "createSubject",
            "createClass", "createResult", "invoice", "report", "payment"
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            JButton menuBtn = new JButton(menuItems[i]);
            menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuBtn.setMaximumSize(new Dimension(250, 55));
            menuBtn.setMinimumSize(new Dimension(250, 55));
            menuBtn.setPreferredSize(new Dimension(250, 55));
            menuBtn.setBackground(Color.WHITE);
            menuBtn.setForeground(Color.BLACK);
            menuBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            menuBtn.setFocusPainted(false);
            menuBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            menuBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            final String cardName = cardNames[i];
            menuBtn.addActionListener(e -> {
                cardLayout.show(contentPanel, cardName);
                // Refresh data when switching panels
                if (cardName.equals("createStudent")) {
                    studentPanel.refreshClassCombo();
                } else if (cardName.equals("createSubject")) {
                    subjectPanel.refreshClassCombo();
                } else if (cardName.equals("createClass")) {
                    classPanel.refreshTable();
                }
            });
            
            sidebar.add(menuBtn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 12)));
        }
        
        sidebar.add(Box.createVerticalGlue());
        
        JButton logoutBtn = new JButton("LOGOUT");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setMaximumSize(new Dimension(250, 55));
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 50, 50), 2));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        sidebar.add(logoutBtn);
        
        return sidebar;
    }
    
    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        
        JLabel titleLabel = new JLabel("AL-QUWIYYI School Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topBar.add(titleLabel, BorderLayout.WEST);
        
        JLabel userLabel = new JLabel("ADMINISTRATOR: ALQUWIYYI");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(220, 220, 220));
        topBar.add(userLabel, BorderLayout.EAST);
        
        return topBar;
    }
}
