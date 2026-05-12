import javax.swing.border.TitledBorder;
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
        dataManager = new DataManager();
        dataManager.loadAllData();
        
        setTitle("AL-QUWIYYI Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        setLayout(new BorderLayout());
        
        // Sidebar buttons white background, black text
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(36,40,59));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20,15,20,15));
        
        String[] items = {"CREATE STUDENT","ALL STUDENTS","CREATE SUBJECT","CREATE CLASS","CREATE RESULT","INVOICE","REPORT","PAYMENT"};
        String[] cards = {"createStudent","allStudents","createSubject","createClass","createResult","invoice","report","payment"};
        
        for (int i=0; i<items.length; i++) {
            JButton btn = new JButton(items[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250,50));
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            final String card = cards[i];
            btn.addActionListener(e -> {
                cardLayout.show(contentPanel, card);
                if (card.equals("createStudent")) studentPanel.refreshClassCombo();
                if (card.equals("createSubject")) subjectPanel.refreshClassCombo();
                if (card.equals("createClass")) classPanel.refreshTable();
            });
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0,10)));
        }
        
        sidebar.add(Box.createVerticalGlue());
        JButton logout = new JButton("LOGOUT");
        logout.setBackground(Color.WHITE);
        logout.setForeground(Color.BLACK);
        logout.setBorder(BorderFactory.createLineBorder(Color.RED,2));
        logout.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,"Logout?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        sidebar.add(logout);
        
        add(sidebar, BorderLayout.WEST);
        
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(0,85,170));
        top.setBorder(BorderFactory.createEmptyBorder(15,25,15,25));
        JLabel title = new JLabel("AL-QUWIYYI School Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD,20));
        title.setForeground(Color.WHITE);
        top.add(title, BorderLayout.WEST);
        JLabel user = new JLabel("ADMIN: ALQUWIYYI");
        user.setForeground(Color.WHITE);
        top.add(user, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245,245,245));
        
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
