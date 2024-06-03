package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton LoginUIButton, signUpButton;

    public LoginUI() {
        setTitle("Green Zone Connect");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add a heading
        JLabel heading = new JLabel("Green Zone Connect", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        add(heading, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // add padding

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        LoginUIButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");

        LoginUIButton.addActionListener(this);
        signUpButton.addActionListener(this);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(LoginUIButton);
        panel.add(signUpButton);

        add(panel, BorderLayout.CENTER);

        // Set fixed window size
        setResizable(false);
        setSize(400, 300);
        setLocationRelativeTo(null); // center the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String[] data = new String[2];
        if (e.getSource() == LoginUIButton) {
            // Perform LoginUI authentication here
            data[0] = usernameField.getText();
            data[1] = new String(passwordField.getPassword());
            Controller Controller = new Controller();
            boolean myresult = Controller.login(data);
            if (myresult == true) {
                String userID = data[0];
                dispose();
                new GreenZoneDashboard(userID);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect User ID or Password! Please try Again!", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == signUpButton) {
            dispose();
            new RegisterWindow().setVisible(true);
        }
    }
}

