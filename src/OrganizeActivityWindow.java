package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrganizeActivityWindow extends JFrame {

    private JComboBox<String> activityNameComboBox;
    private JTextArea activityDescriptionArea;
    private JComboBox<String> groupComboBox;

    public OrganizeActivityWindow(Object[][] groups, Object[][] activities) {
        setTitle("Organize Activity");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false); // Set window non-expandable

        JPanel contentPane = new JPanel(new BorderLayout());

        // Convert Object[][] to String[] for groups
        String[] groupNames = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            groupNames[i] = groups[i][1].toString(); // Assuming group name is at index 1
        }

        // Convert Object[][] to String[] for activities
        String[] activityNames = new String[activities.length];
        for (int i = 0; i < activities.length; i++) {
            activityNames[i] = activities[i][1].toString(); // Assuming activity name is at index 1
        }

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Group Label and ComboBox
        JLabel groupLabel = new JLabel("Group:");
        groupComboBox = new JComboBox<>(groupNames);
        groupComboBox.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(groupLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(groupComboBox, gbc);

        // Activity Name Label and ComboBox
        JLabel activityNameLabel = new JLabel("Activity Name:");
        activityNameComboBox = new JComboBox<>(activityNames);
        activityNameComboBox.setEditable(true);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(activityNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(activityNameComboBox, gbc);

        // Activity Description Label and TextArea
        JLabel activityDescriptionLabel = new JLabel("Activity Description:");
        activityDescriptionArea = new JTextArea(5, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(activityDescriptionArea);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(activityDescriptionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(descriptionScrollPane, gbc);

        // Save and Cancel Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values
                String selectedGroup = groupComboBox.getSelectedItem().toString();
                String selectedActivity = activityNameComboBox.getSelectedItem().toString();
                String activityDescription = activityDescriptionArea.getText();

                // Get group ID and activity ID
                int groupId = getGroupId(groups, selectedGroup);
                int activityId = getActivityId(activities, selectedActivity);

                System.out.println("Group ID: " + groupId);
                System.out.println("Activity ID: " + activityId);
                System.out.println("Activity Description: " + activityDescription);

                // Process the input (e.g., submit to system)
                Controller controller = new Controller();
                String[] data = { String.valueOf(groupId), String.valueOf(activityId), activityDescription };
                controller.organizeActivity(data);

                // Clear input fields after submission
                clearInputFields();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add Form Panel and Button Panel to the content pane
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setVisible(true);
    }

    // Method to clear input fields after submission
    private void clearInputFields() {
        activityNameComboBox.setSelectedIndex(-1);
        activityDescriptionArea.setText("");
    }

    private int getGroupId(Object[][] groups, String selectedGroup) {
        for (Object[] group : groups) {
            if (group[1].toString().equals(selectedGroup)) {
                return Integer.parseInt(group[0].toString());
            }
        }
        return -1; // If the group is not found
    }

    private int getActivityId(Object[][] activities, String selectedActivity) {
        for (Object[] activity : activities) {
            if (activity[1].toString().equals(selectedActivity)) {
                return Integer.parseInt(activity[0].toString());
            }
        }
        return -1; // If the activity is not found
    }

}
