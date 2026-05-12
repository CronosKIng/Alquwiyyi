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
        setTitle("AL-QUWIYYI School Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(25, 35, 55), 
                                                     getWidth(), getHeight(), new Color(15, 25, 45));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        // Login Card
        JPanel loginCard = new JPanel(new BorderLayout());
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        loginCard.setPreferredSize(new Dimension(500, 550));
        loginCard.setMaximumSize(new Dimension(500, 550));
        
        // Top Section
        JPanel topSection = new JPanel();
        topSection.setBackground(new Color(0, 85, 170));
        topSection.setPreferredSize(new Dimension(500, 110));
        topSection.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("AL-QUWIYYI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        topSection.add(titleLabel, BorderLayout.CENTER);
        
        JLabel subLabel = new JLabel("School Management System", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(new Color(230, 230, 230));
        topSection.add(subLabel, BorderLayout.SOUTH);
        
        loginCard.add(topSection, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(userLabel, gbc);
        
        usernameField = new JTextField(VALID_USERNAME);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        usernameField.setBackground(new Color(250, 250, 250));
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);
        
        // Password
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 2;
        formPanel.add(passLabel, gbc);
        
        passwordField = new JPasswordField(VALID_PASSWORD);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        passwordField.setBackground(new Color(250, 250, 250));
        passwordField.setEchoChar('*');
        gbc.gridy = 3;
        formPanel.add(passwordField, gbc);
        
        // Login Button
        JButton loginBtn = new JButton("SIGN IN");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBackground(new Color(0, 85, 170));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        loginBtn.addActionListener(e -> attemptLogin());
        
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 8, 8, 8);
        formPanel.add(loginBtn, gbc);
        
        // Info
        JLabel infoLabel = new JLabel("Credentials pre-filled - Click SIGN IN", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(100, 180, 100));
        gbc.gridy = 5;
        gbc.insets = new Insets(15, 8, 8, 8);
        formPanel.add(infoLabel, gbc);
        
        loginCard.add(formPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setPreferredSize(new Dimension(500, 40));
        JLabel footerLabel = new JLabel("2025 AL-QUWIYYI. All rights reserved.");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(new Color(140, 140, 140));
        footerPanel.add(footerLabel);
        loginCard.add(footerPanel, BorderLayout.SOUTH);
        
        mainPanel.add(loginCard);
        add(mainPanel);
        
        passwordField.addActionListener(e -> attemptLogin());
        usernameField.addActionListener(e -> attemptLogin());
    }
    
    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            dispose();
            new DashboardFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid Credentials!\n\nUsername: ALQUWIYYI\nPassword: Aquwiyyi@33",
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
