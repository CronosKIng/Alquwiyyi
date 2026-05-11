import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final String VALID_USERNAME = "ALQUWIYYI";
    private final String VALID_PASSWORD = "Aquwiyyi@33";
    
    public LoginFrame() {
        setTitle("AL-QUWIYYI - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(44, 62, 80));
        
        JLabel headerLabel = new JLabel("AL-QUWIYYI SCHOOL SYSTEM", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(44, 62, 80));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(userLabel, gbc);
        
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);
        
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(passLabel, gbc);
        
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);
        
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(new Color(46, 204, 113));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        loginPanel.add(loginBtn, gbc);
        
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        loginBtn.addActionListener(e -> attemptLogin());
        passwordField.addActionListener(e -> attemptLogin());
    }
    
    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            dispose();
            new DashboardFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password!\nEnter:\nUsername: ALQUWIYYI\nPassword: Aquwiyyi@33",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {}
            new LoginFrame().setVisible(true);
        });
    }
}
