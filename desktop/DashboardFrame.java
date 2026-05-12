import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private DataManager dataManager;
    
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
        
        contentPanel.add(new CreateStudentPanel(dataManager), "createStudent");
        contentPanel.add(new AllStudentsPanel(dataManager), "allStudents");
        contentPanel.add(new CreateSubjectPanel(dataManager), "createSubject");
        contentPanel.add(new CreateClassPanel(dataManager), "createClass");
        contentPanel.add(new CreateResultPanel(dataManager), "createResult");
        contentPanel.add(new InvoicePanel(dataManager), "invoice");
        contentPanel.add(new ReportPanel(dataManager), "report");
        contentPanel.add(new PaymentPanel(dataManager), "payment");
        
        add(contentPanel, BorderLayout.CENTER);
        
        try {
            java.net.URL iconURL = getClass().getResource("/icons/hgd.ico");
            if (iconURL != null) {
                java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
                setIconImage(icon);
            }
        } catch (Exception e) {}
        
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
            menuBtn.setBackground(new Color(48, 54, 79));
            menuBtn.setForeground(Color.BLACK);
            menuBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            menuBtn.setFocusPainted(false);
            menuBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            menuBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            final String cardName = cardNames[i];
            menuBtn.addActionListener(e -> cardLayout.show(contentPanel, cardName));
            
            sidebar.add(menuBtn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 12)));
        }
        
        sidebar.add(Box.createVerticalGlue());
        
        JButton logoutBtn = new JButton("LOGOUT");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setMaximumSize(new Dimension(250, 55));
        logoutBtn.setBackground(new Color(200, 50, 50));
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
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
        titleLabel.setForeground(Color.BLACK);
        topBar.add(titleLabel, BorderLayout.WEST);
        
        JLabel userLabel = new JLabel("ADMINISTRATOR: ALQUWIYYI");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(220, 220, 220));
        topBar.add(userLabel, BorderLayout.EAST);
        
        return topBar;
    }
}
