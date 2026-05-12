import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

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
        setUndecorated(true);
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 30, 50), 
                                                     getWidth(), getHeight(), new Color(10, 20, 40));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Decorative circles
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.fillOval(-100, -100, 400, 400);
                g2d.fillOval(getWidth() - 300, getHeight() - 300, 500, 500);
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillOval(getWidth() - 200, 50, 300, 300);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        // Main login card
        JPanel loginCard = new JPanel(new BorderLayout());
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        loginCard.setPreferredSize(new Dimension(520, 580));
        loginCard.setMaximumSize(new Dimension(520, 580));
        
        // Top decorative bar
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(0, 85, 170));
        topBar.setPreferredSize(new Dimension(520, 8));
        loginCard.add(topBar, BorderLayout.NORTH);
        
        // Logo and title section
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        
        JLabel logoLabel = new JLabel("AL-QUWIYYI", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logoLabel.setForeground(new Color(0, 85, 170));
        headerPanel.add(logoLabel, BorderLayout.CENTER);
        
        JLabel subLabel = new JLabel("School Management System", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(new Color(120, 120, 120));
        headerPanel.add(subLabel, BorderLayout.SOUTH);
        
        loginCard.add(headerPanel, BorderLayout.NORTH);
        
        // Form section
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username field
        JLabel userIconLabel = new JLabel("USERNAME");
        userIconLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userIconLabel.setForeground(new Color(80, 80, 80));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userIconLabel, gbc);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        usernameField.setBackground(new Color(248, 249, 250));
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);
        
        // Password field
        JLabel passIconLabel = new JLabel("PASSWORD");
        passIconLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passIconLabel.setForeground(new Color(80, 80, 80));
        gbc.gridy = 2;
        formPanel.add(passIconLabel, gbc);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        passwordField.setBackground(new Color(248, 249, 250));
        gbc.gridy = 3;
        formPanel.add(passwordField, gbc);
        
        // Login button
        JButton loginBtn = new JButton("SIGN IN");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginBtn.setBackground(new Color(0, 85, 170));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                loginBtn.setBackground(new Color(0, 70, 145));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                loginBtn.setBackground(new Color(0, 85, 170));
            }
        });
        loginBtn.addActionListener(e -> attemptLogin());
        
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 8, 8, 8);
        formPanel.add(loginBtn, gbc);
        
        // Info text
        JLabel infoLabel = new JLabel("Default: ALQUWIYYI / Aquwiyyi@33", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(160, 160, 160));
        gbc.gridy = 5;
        gbc.insets = new Insets(15, 8, 8, 8);
        formPanel.add(infoLabel, gbc);
        
        loginCard.add(formPanel, BorderLayout.CENTER);
        
        // Footer section
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setPreferredSize(new Dimension(520, 45));
        JLabel footerLabel = new JLabel("© 2025 AL-QUWIYYI. All rights reserved.");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(new Color(140, 140, 140));
        footerPanel.add(footerLabel);
        loginCard.add(footerPanel, BorderLayout.SOUTH);
        
        // Close button (X) for undecorated frame
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setOpaque(false);
        JButton closeBtn = new JButton("✕");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(new Color(255, 80, 80));
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> System.exit(0));
        closePanel.add(closeBtn);
        closePanel.setBounds(getWidth() - 60, 10, 50, 30);
        
        mainPanel.add(loginCard);
        add(mainPanel);
        
        // Add close button to layered pane
        JLayeredPane layeredPane = getLayeredPane();
        closePanel.setBounds(getWidth() - 70, 10, 60, 35);
        layeredPane.add(closePanel, JLayeredPane.POPUP_LAYER);
        
        passwordField.addActionListener(e -> attemptLogin());
        
        // Resize listener for close button position
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                closePanel.setBounds(getWidth() - 70, 10, 60, 35);
            }
        });
        
        // Load icon if exists
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
                "Invalid Credentials!\n\nUsername: ALQUWIYYI\nPassword: Aquwiyyi@33",
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
