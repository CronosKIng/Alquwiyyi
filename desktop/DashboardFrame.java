import javax.swing.*;
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
        dataManager = DataManager.getInstance();  // FIXED: Singleton pattern
        setTitle("AL-QUWIYYI - System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBackground(new Color(36, 40, 59));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        String[] menuItems = {"CREATE STUDENT", "ALL STUDENTS", "CREATE SUBJECT", "CREATE CLASS", "CREATE RESULT", "INVOICE", "REPORT", "PAYMENT"};
        String[] cardNames = {"createStudent", "allStudents", "createSubject", "createClass", "createResult", "invoice", "report", "payment"};

        for (int i = 0; i < menuItems.length; i++) {
            JButton menuBtn = new JButton(menuItems[i]);
            menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuBtn.setMaximumSize(new Dimension(210, 45));
            menuBtn.setPreferredSize(new Dimension(210, 45));
            menuBtn.setBackground(Color.WHITE);
            menuBtn.setForeground(Color.BLACK);
            menuBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            menuBtn.setFocusPainted(false);
            menuBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            final String cardName = cardNames[i];
            menuBtn.addActionListener(e -> {
                if(cardName.equals("createStudent") && studentPanel != null) studentPanel.refreshClassCombo();
                if(cardName.equals("createSubject") && subjectPanel != null) subjectPanel.refreshClassCombo();
                if(cardName.equals("createClass") && classPanel != null) classPanel.refreshTable();
                cardLayout.show(contentPanel, cardName);
            });
            sidebar.add(menuBtn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        // LOGOUT BUTTON - White background, Black text, medium size
        JButton logoutBtn = new JButton("LOGOUT");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setMaximumSize(new Dimension(210, 45));
        logoutBtn.setPreferredSize(new Dimension(210, 45));
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);
        
        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
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
}
