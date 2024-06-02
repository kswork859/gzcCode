package src;
//import javax.sound.sampled.Control;
import javax.swing.*;
import java.awt.*;

public class CreateGroupsPanel extends JPanel {

    public CreateGroupsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding

        JLabel formHeadingLabel = new JLabel("Create New Group", SwingConstants.CENTER);
        formHeadingLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(formHeadingLabel, BorderLayout.NORTH);

        JPanel createFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints createGbc = new GridBagConstraints();
        createGbc.insets = new Insets(10, 10, 10, 10);
        createGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel groupIDLabel = new JLabel("Group Unique ID:");
        JTextField groupIDTextField = new JTextField(20);
        createGbc.gridx = 0;
        createGbc.gridy = 0;
        createFormPanel.add(groupIDLabel, createGbc);
        createGbc.gridx = 1;
        createFormPanel.add(groupIDTextField, createGbc);

        JLabel groupNameLabel = new JLabel("Group Name:");
        JTextField groupNameTextField = new JTextField(20);
        createGbc.gridx = 0;
        createGbc.gridy = 1;
        createFormPanel.add(groupNameLabel, createGbc);
        createGbc.gridx = 1;
        createFormPanel.add(groupNameTextField, createGbc);

        JLabel groupAreaLabel = new JLabel("Select Group Area:");
        JComboBox<String> groupAreaComboBox = new JComboBox<>(new String[] { "Area 1", "Area 2", "Area 3" });
        createGbc.gridx = 0;
        createGbc.gridy = 2;
        createFormPanel.add(groupAreaLabel, createGbc);
        createGbc.gridx = 1;
        createFormPanel.add(groupAreaComboBox, createGbc);

        JLabel adminLabel = new JLabel("Admin ID");
        JTextField adminIdTextField = new JTextField(20);
        createGbc.gridx = 0;
        createGbc.gridy = 3;
        createFormPanel.add(adminLabel, createGbc);
        createGbc.gridx = 1;
        createFormPanel.add(adminIdTextField, createGbc);

        JLabel adminPasswordLabel = new JLabel("Admin ID");
        JPasswordField adminPasswordTextField = new JPasswordField(20);
        createGbc.gridx = 0;
        createGbc.gridy = 4;
        createFormPanel.add(adminPasswordLabel, createGbc);
        createGbc.gridx = 1;
        createFormPanel.add(adminPasswordTextField, createGbc);

        JButton createGroupButton = new JButton("Create Group");
        createGroupButton.addActionListener(e -> {
            String[] data = new String[5];
            data[0] = groupIDTextField.getText();
            data[1] = groupNameTextField.getText();
            data[2] = (String) groupAreaComboBox.getSelectedItem();
            data[3] = adminIdTextField.getText();
            data[4] = new String(adminPasswordTextField.getPassword());
            Controller createMyGroup = new Controller();
            boolean isGroupcreated;
            isGroupcreated = createMyGroup.createGroup(data);
            if(isGroupcreated)
            {
                JOptionPane.showMessageDialog(CreateGroupsPanel.this,
                    "Group has been create successfully!");
            }
            else{
                JOptionPane.showMessageDialog(CreateGroupsPanel.this,
                    "Group could not be created!.");
            }
            
        });

        createGbc.gridx = 1;
        createGbc.gridy = 5;
        createFormPanel.add(createGroupButton, createGbc);

        add(createFormPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Group Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Create Groups", new CreateGroupsPanel());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
