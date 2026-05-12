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
        setTitle("AL-QUWIYYI - School Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        // Main panel with dark background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(30, 34, 52));
        
        // Login card panel
        JPanel loginCard = new JPanel(new BorderLayout());
        loginCard.setBackground(new Color(255, 255, 255));
        loginCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 85, 170));
        headerPanel.setPreferredSize(new Dimension(450, 80));
        
        JLabel headerLabel = new JLabel("AL-QUWIYYI SCHOOL SYSTEM");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        loginCard.add(headerPanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Username
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Arial", Font.BOLD, 13));
        userLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userLabel, gbc);
        
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(usernameField, gbc);
        
        // Password
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(passLabel, gbc);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordField, gbc);
        
        // Login Button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(new Color(0, 85, 170));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> attemptLogin());
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(30, 10, 10, 10);
        formPanel.add(loginBtn, gbc);
        
        loginCard.add(formPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setPreferredSize(new Dimension(450, 35));
        JLabel footerLabel = new JLabel("AL-QUWIYYI v1.0");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(footerLabel);
        loginCard.add(footerPanel, BorderLayout.SOUTH);
        
        // Set card size
        loginCard.setPreferredSize(new Dimension(450, 420));
        loginCard.setMaximumSize(new Dimension(450, 420));
        
        mainPanel.add(loginCard);
        add(mainPanel);
        
        passwordField.addActionListener(e -> attemptLogin());
        
        // Load icon
        try {
            java.net.URL iconURL = getClass().getResource("/icons/hgd.ico");
            if (iconURL != null) {
                java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
                setIconImage(icon);
            }
        } catch (Exception e) {}
    }
    
    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            dispose();
            new DashboardFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password!\n\nUsername: ALQUWIYYI\nPassword: Aquwiyyi@33",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
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
