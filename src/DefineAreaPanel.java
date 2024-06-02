package src;
import javax.swing.*;
import java.awt.*;

public class DefineAreaPanel extends JPanel {

    public DefineAreaPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100)); // Add padding

        JLabel defineAreaHeading = new JLabel("Define Area", SwingConstants.CENTER);
        defineAreaHeading.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(defineAreaHeading, BorderLayout.NORTH); // Add heading above the form

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel countryLabel = new JLabel("Country Name:");
        JComboBox<String> countryComboBox = new JComboBox<>(new String[]{"Country 1", "Country 2", "Country 3"});

        JLabel cityLabel = new JLabel("City Name:");
        JComboBox<String> cityComboBox = new JComboBox<>(new String[]{"City 1", "City 2", "City 3"});

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
        JFrame frame = new JFrame("Define Area");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Define Area", new DefineAreaPanel());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
