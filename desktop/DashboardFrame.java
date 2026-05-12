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
        setSize(1300, 750);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        JPanel sidebar = createSidebar();
        sidebar.setPreferredSize(new Dimension(260, getHeight()));
        sidebar.setBackground(new Color(25, 35, 50));
        add(sidebar, BorderLayout.WEST);
        
        JPanel topBar = createTopBar();
        topBar.setBackground(new Color(20, 28, 40));
        topBar.setPreferredSize(new Dimension(getWidth(), 65));
        add(topBar, BorderLayout.NORTH);
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 245, 250));
        
        contentPanel.add(new CreateStudentPanel(dataManager), "createStudent");
        contentPanel.add(new AllStudentsPanel(dataManager), "allStudents");
        contentPanel.add(new CreateSubjectPanel(dataManager), "createSubject");
        contentPanel.add(new CreateClassPanel(dataManager), "createClass");
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
            "Create Student", "All Students", "Create Subject",
            "Create Class", "Create Result", "Invoice",
            "Report", "Payment"
        };
        
        String[] cardNames = {
            "createStudent", "allStudents", "createSubject",
            "createClass", "createResult", "invoice", "report", "payment"
        };
        
        Color[] buttonColors = {
            new Color(52, 152, 219), new Color(46, 134, 222), new Color(41, 128, 185),
            new Color(142, 68, 173), new Color(155, 89, 182), new Color(192, 57, 43),
            new Color(211, 84, 0), new Color(39, 174, 96)
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            JButton menuBtn = new JButton(menuItems[i]);
            menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuBtn.setMaximumSize(new Dimension(230, 48));
            menuBtn.setMinimumSize(new Dimension(230, 48));
            menuBtn.setPreferredSize(new Dimension(230, 48));
            menuBtn.setBackground(buttonColors[i]);
            menuBtn.setForeground(Color.WHITE);
            menuBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            menuBtn.setFocusPainted(false);
            menuBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            menuBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            final String cardName = cardNames[i];
            menuBtn.addActionListener(e -> cardLayout.show(contentPanel, cardName));
            
            sidebar.add(menuBtn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 12)));
        }
        
        sidebar.add(Box.createVerticalGlue());
        
        JButton logoutBtn = new JButton("LOGOUT");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setMaximumSize(new Dimension(230, 48));
        logoutBtn.setBackground(new Color(192, 57, 43));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setFocusPainted(false);
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
        titleLabel.setForeground(new Color(255, 255, 255));
        topBar.add(titleLabel, BorderLayout.WEST);
        
        JLabel userLabel = new JLabel("Admin: ALQUWIYYI");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userLabel.setForeground(new Color(180, 190, 210));
        topBar.add(userLabel, BorderLayout.EAST);
        
        return topBar;
    }
}
