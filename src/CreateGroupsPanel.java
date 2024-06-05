package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGroupsPanel extends JPanel {

    public CreateGroupsPanel(String userID) {
        System.out.println(userID);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding

        JLabel defineAreaHeading = new JLabel("Create Group", SwingConstants.CENTER);
        defineAreaHeading.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(defineAreaHeading, BorderLayout.NORTH); // Add heading above the form

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between elements
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Group Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel groupNameLabel = new JLabel("Group Name:");
        formPanel.add(groupNameLabel, gbc);

        gbc.gridx = 1;
        JTextField groupNameTextField = new JTextField(15);
        formPanel.add(groupNameTextField, gbc);

        // Country Names
        Controller controller = new Controller();
        List<String> countryNames = controller.getCountryNames();

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel countryLabel = new JLabel("Country Names:");
        formPanel.add(countryLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> countryComboBox = new JComboBox<>(countryNames.toArray(new String[0]));
        formPanel.add(countryComboBox, gbc);

        // City Names
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel cityLabel = new JLabel("City Name:");
        formPanel.add(cityLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> cityComboBox = new JComboBox<>();
        formPanel.add(cityComboBox, gbc);

        // Area Names
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel areaLabel = new JLabel("Area Name:");
        formPanel.add(areaLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> areaComboBox = new JComboBox<>();
        formPanel.add(areaComboBox, gbc);

        // Save Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        JButton saveButton = new JButton("Save");
        formPanel.add(saveButton, gbc);

        // Add listeners
        countryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCountry = (String) countryComboBox.getSelectedItem();
                List<String> cityNames = controller.getCityNamesByCountry(selectedCountry);

                // Update the city combo box
                cityComboBox.removeAllItems();
                for (String cityName : cityNames) {
                    cityComboBox.addItem(cityName);
                }
            }
        });

        cityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCity = (String) cityComboBox.getSelectedItem();
                if (selectedCity != null) {
                    List<String> areaNames = controller.getAreaNamesByCity(selectedCity);

                    // Update the area combo box
                    areaComboBox.removeAllItems();
                    for (String areaName : areaNames) {
                        areaComboBox.addItem(areaName);
                    }
                }
            }
        });

        saveButton.addActionListener(e -> {
            // Save action goes here
            String[] data = new String[5];
            data[0] = userID;
            data[1] = groupNameTextField.getText();
            data[2] = (String) countryComboBox.getSelectedItem();
            data[3] = (String) cityComboBox.getSelectedItem();
            data[4] = (String) areaComboBox.getSelectedItem();

            // Process the saved data
            Controller createGroupController = new Controller();
            createGroupController.createGroup(data);
        });

        add(formPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Create Group");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Create Group", new CreateGroupsPanel("testUserID"));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
