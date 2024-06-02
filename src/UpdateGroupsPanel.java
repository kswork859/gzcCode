package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UpdateGroupsPanel extends JPanel {

    private JComboBox<String> groupComboBox;
    private JTextField groupNameTextField;
    private JTextField adminIDTextField;
    private JTextField adminNameTextField;
    private JTextField areaIDTextField;
    private JTextField areaNameTextField;

    private Map<String, GroupData> groupDataMap;

    public UpdateGroupsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding

        JLabel headingLabel = new JLabel("Update Group Information", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(headingLabel, BorderLayout.NORTH);

        // Initialize sample group data
        initializeGroupData();

        JPanel updateFormPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Select Group Label and ComboBox
        JLabel groupLabel = new JLabel("Select Group:");
        groupComboBox = new JComboBox<>(groupDataMap.keySet().toArray(new String[0]));
        groupComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateGroupData((String) groupComboBox.getSelectedItem());
            }
        });

        // Group Name Label and TextField
        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameTextField = new JTextField();

        // Admin ID Label and TextField
        JLabel adminIDLabel = new JLabel("Admin ID:");
        adminIDTextField = new JTextField();

        // Admin Name Label and TextField
        JLabel adminNameLabel = new JLabel("Admin Name:");
        adminNameTextField = new JTextField();

        // Area ID Label and TextField
        JLabel areaIDLabel = new JLabel("Area ID:");
        areaIDTextField = new JTextField();

        // Area Name Label and TextField
        JLabel areaNameLabel = new JLabel("Area Name:");
        areaNameTextField = new JTextField();

        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            // Display the message
            JOptionPane.showMessageDialog(UpdateGroupsPanel.this, "Group Information has been updated successfully.");
        });

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            groupComboBox.setSelectedIndex(0);
            populateGroupData((String) groupComboBox.getSelectedItem());
        });

        // Add components to the form panel
        updateFormPanel.add(groupLabel);
        updateFormPanel.add(groupComboBox);
        updateFormPanel.add(groupNameLabel);
        updateFormPanel.add(groupNameTextField);
        updateFormPanel.add(adminIDLabel);
        updateFormPanel.add(adminIDTextField);
        updateFormPanel.add(adminNameLabel);
        updateFormPanel.add(adminNameTextField);
        updateFormPanel.add(areaIDLabel);
        updateFormPanel.add(areaIDTextField);
        updateFormPanel.add(areaNameLabel);
        updateFormPanel.add(areaNameTextField);

        // Add buttons to a separate panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        // Add form panel and button panel to the center of the updateGroupsPanel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(updateFormPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // Populate fields with the data of the first group initially
        populateGroupData((String) groupComboBox.getSelectedItem());
    }

    private void initializeGroupData() {
        groupDataMap = new HashMap<>();
        groupDataMap.put("G101", new GroupData("Group 1", "A101", "Admin 1", "Area 1", "Area Name 1"));
        groupDataMap.put("G102", new GroupData("Group 2", "A102", "Admin 2", "Area 2", "Area Name 2"));
        groupDataMap.put("G103", new GroupData("Group 3", "A103", "Admin 3", "Area 3", "Area Name 3"));
    }

    private void populateGroupData(String groupId) {
        GroupData data = groupDataMap.get(groupId);
        if (data != null) {
            groupNameTextField.setText(data.getGroupName());
            adminIDTextField.setText(data.getAdminID());
            adminNameTextField.setText(data.getAdminName());
            areaIDTextField.setText(data.getAreaID());
            areaNameTextField.setText(data.getAreaName());
        }
    }

    private static class GroupData {
        private final String groupName;
        private final String adminID;
        private final String adminName;
        private final String areaID;
        private final String areaName;

        public GroupData(String groupName, String adminID, String adminName, String areaID, String areaName) {
            this.groupName = groupName;
            this.adminID = adminID;
            this.adminName = adminName;
            this.areaID = areaID;
            this.areaName = areaName;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getAdminID() {
            return adminID;
        }

        public String getAdminName() {
            return adminName;
        }

        public String getAreaID() {
            return areaID;
        }

        public String getAreaName() {
            return areaName;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Group Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Update Groups", new UpdateGroupsPanel());

        frame.add(groupTabbedPane);
        frame.setVisible(true);
    }
}
