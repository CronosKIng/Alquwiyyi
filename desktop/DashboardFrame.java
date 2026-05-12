import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private DataManager dataManager;
    private CreateStudentPanel studentPanel;
    private CreateSubjectPanel subjectPanel;
    private CreateClassPanel classPanel;
    
    public DashboardFrame() {
        dataManager = new DataManager();
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
            menuBtn.setMaximumSize(new Dimension(210, 45)); // SIZE YA KATI
            menuBtn.setPreferredSize(new Dimension(210, 45));
            menuBtn.setBackground(Color.WHITE);
            menuBtn.setForeground(Color.BLACK);
            menuBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            menuBtn.setFocusPainted(false);
            
            final String cardName = cardNames[i];
            menuBtn.addActionListener(e -> {
                if(cardName.equals("createStudent")) studentPanel.refreshClassCombo();
                if(cardName.equals("createSubject")) subjectPanel.refreshClassCombo();
                if(cardName.equals("createClass")) classPanel.refreshTable();
                cardLayout.show(contentPanel, cardName);
            });
            sidebar.add(menuBtn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }
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
