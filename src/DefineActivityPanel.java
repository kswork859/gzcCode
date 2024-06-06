package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefineActivityPanel extends JPanel {

    private JTextField activityNameField;

    public DefineActivityPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel activityNameLabel = new JLabel("Activity Name:");
        activityNameField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(activityNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(activityNameField, gbc);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String activityName = activityNameField.getText().trim();
                if (!activityName.isEmpty()) {
                    Controller controller = new Controller();
                    // Pass activity name to controller
                    controller.saveActivity(activityName);
                } else {
                    JOptionPane.showMessageDialog(DefineActivityPanel.this,
                            "Please enter activity name",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear input fields
                activityNameField.setText("");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
