package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrganizeActivityWindow extends JFrame {

    private JTextField activityNameField;
    private JTextField areaAddressField;
    private JTextArea activityDescriptionArea;
    private JTextField groupIdField;

    public OrganizeActivityWindow() {
        setTitle("Organize Activity");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false); // Set window non-expandable

        JPanel contentPane = new JPanel(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Activity Name Label and Field
        JLabel activityNameLabel = new JLabel("Activity Name:");
        activityNameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(activityNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(activityNameField, gbc);

        // Area Address Label and Field
        JLabel areaAddressLabel = new JLabel("Area Address:");
        areaAddressField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(areaAddressLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(areaAddressField, gbc);

        // Activity Description Label and Field
        JLabel activityDescriptionLabel = new JLabel("Activity Description:");
        activityDescriptionArea = new JTextArea(5, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(activityDescriptionArea);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(activityDescriptionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(descriptionScrollPane, gbc);

        // Group ID Label and Field
        JLabel groupIdLabel = new JLabel("Group ID:");
        groupIdField = new JTextField(20);
        groupIdField.setText("Group 1");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(groupIdLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(groupIdField, gbc);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values
                String activityName = activityNameField.getText();
                String areaAddress = areaAddressField.getText();
                String activityDescription = activityDescriptionArea.getText();
                String groupId = groupIdField.getText();

                // Process the input (e.g., submit to system)
                // For now, just print the input values
                System.out.println("Activity Name: " + activityName);
                System.out.println("Area Address: " + areaAddress);
                System.out.println("Activity Description: " + activityDescription);
                System.out.println("Group ID: " + groupId);

                // Clear input fields after submission
                clearInputFields();
            }
        });

        // Add Form Panel and Submit Button to the content pane
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(submitButton, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setVisible(true);
    }

    // Method to clear input fields after submission
    private void clearInputFields() {
        activityNameField.setText("");
        areaAddressField.setText("");
        activityDescriptionArea.setText("");
        groupIdField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OrganizeActivityWindow();
            }
        });
    }
}
