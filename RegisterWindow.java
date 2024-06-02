import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow extends JFrame {
    private JTextField userIDField;
    private JTextField userNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField addressField;
    private JTextField phoneField;
    private Controller Controller;

    public RegisterWindow() {
        setTitle("Register");
        setSize(400, 400);
        setResizable(false); // Make the window non-resizable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Initialize the Controller
        Controller = new Controller();

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // User ID
        JLabel userIDLabel = new JLabel("User ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(userIDLabel, gbc);

        userIDField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(userIDField, gbc);
        // Username
        JLabel userNameLabel = new JLabel("User Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(userNameLabel, gbc);

        userNameField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(userNameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(addressLabel, gbc);

        addressField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(addressField, gbc);

        // Phone No
        JLabel phoneLabel = new JLabel("Phone No:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(phoneField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");
        JButton createAccountButton = new JButton("Create Account");

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(createAccountButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginUI();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] data = new String[6];
                data[0]  = userIDField.getText();
                data[1]  = userNameField.getText();
                data[2] = emailField.getText();
                data[3]  = new String(passwordField.getPassword());
                data[4] = addressField.getText();
                data[5]  = phoneField.getText();

                // Use Controller to handle registration
               boolean success = Controller.registerUser(data);
                if (success) {
                    JOptionPane.showMessageDialog(RegisterWindow.this, "Account created successfully!");
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(RegisterWindow.this, "Account creation failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void clearForm() {
        userIDField.setText("");
        userNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        addressField.setText("");
        phoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegisterWindow registerWindow = new RegisterWindow();
                registerWindow.setVisible(true);
            }
        });
    }
}
