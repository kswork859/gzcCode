package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGroupsPanel extends JPanel {

    public CreateGroupsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100)); // Add padding

        JLabel defineAreaHeading = new JLabel("Create Group", SwingConstants.CENTER);
        defineAreaHeading.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(defineAreaHeading, BorderLayout.NORTH); // Add heading above the form

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel groupNameLabel = new JLabel("Group Name:");
        JTextField groupNameTextField = new JTextField(13);

        JLabel userIDLabel = new JLabel("User ID:");
        JTextField userIDTextField = new JTextField(13);

        JLabel userPasswordLabel = new JLabel("Password:");
        JPasswordField userPasswordField = new JPasswordField(13);

        Controller controllerCity = new Controller();
        List<String> countryNames = controllerCity.getCountryNames();
        JLabel countryLabel = new JLabel("Country Names:");
        JComboBox<String> countryComboBox = new JComboBox<>(countryNames.toArray(new String[0]));

        JLabel cityLabel = new JLabel("City Name:");
        JComboBox<String> cityComboBox = new JComboBox<>();

        countryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCountry = (String) countryComboBox.getSelectedItem();
                List<String> cityNames = controllerCity.getCityNamesbyCountry(selectedCountry);

                // Update the city combo box
                cityComboBox.removeAllItems();
                for (String cityName : cityNames) {
                    cityComboBox.addItem(cityName);
                }
            }
        });

        JLabel areaLabel = new JLabel("Area Name:");
        JTextArea areaTextArea = new JTextArea();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // Save action goes here
            String country = (String) countryComboBox.getSelectedItem();
            String city = (String) cityComboBox.getSelectedItem();
            String area = areaTextArea.getText();
            // Process the saved data
            System.out.println("Country: " + country);
            System.out.println("City: " + city);
            System.out.println("Area: " + area);
        });
        formPanel.add(groupNameLabel);
        formPanel.add(groupNameTextField);
        formPanel.add(userIDLabel);
        formPanel.add(userIDTextField);
        formPanel.add(userPasswordLabel);
        formPanel.add(userPasswordField);

        formPanel.add(countryLabel);
        formPanel.add(countryComboBox);
        formPanel.add(cityLabel);
        formPanel.add(cityComboBox);
        formPanel.add(areaLabel);
        formPanel.add(new JScrollPane(areaTextArea));

        add(formPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Create Group");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Create Group", new DefineAreaPanel());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
