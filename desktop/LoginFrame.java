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
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(36, 40, 59));
        
        JLabel headerLabel = new JLabel("AL-QUWIYYI SCHOOL SYSTEM", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(36, 40, 59));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        
        JLabel userLabel = new JLabel("USERNAME:");
        userLabel.setForeground(new Color(200, 200, 200));
        userLabel.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(userLabel, gbc);
        
        usernameField = new JTextField(18);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(new Color(50, 55, 80));
        usernameField.setForeground(Color.WHITE);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(80, 85, 110)));
        usernameField.setCaretColor(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(usernameField, gbc);
        
        JLabel passLabel = new JLabel("PASSWORD:");
        passLabel.setForeground(new Color(200, 200, 200));
        passLabel.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(passLabel, gbc);
        
        passwordField = new JPasswordField(18);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(50, 55, 80));
        passwordField.setForeground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(80, 85, 110)));
        passwordField.setCaretColor(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(passwordField, gbc);
        
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                loginBtn.setBackground(new Color(0, 100, 195));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                loginBtn.setBackground(new Color(0, 120, 215));
            }
        });
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(25, 10, 10, 10);
        loginPanel.add(loginBtn, gbc);
        
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        loginBtn.addActionListener(e -> attemptLogin());
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
