package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefineAreaPanel extends JPanel {

    public DefineAreaPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding

        JLabel defineAreaHeading = new JLabel("Define Area", SwingConstants.CENTER);
        defineAreaHeading.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(defineAreaHeading, BorderLayout.NORTH); // Add heading above the form

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding to form

        JLabel countryLabel = new JLabel("Country Name:");
        JTextField countryTextField = new JTextField();
        countryTextField.setPreferredSize(new Dimension(200, 20)); // Set preferred size with reduced height

        JLabel cityLabel = new JLabel("City Name:");
        JTextField cityTextField = new JTextField();
        cityTextField.setPreferredSize(new Dimension(200, 20)); // Set preferred size with reduced height

        JLabel areaLabel = new JLabel("Area Name:");
        JTextField areaTextField = new JTextField();
        areaTextField.setPreferredSize(new Dimension(200, 20)); // Set preferred size with reduced height

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        saveButton.addActionListener(e -> {
            // Save action goes here
            String[] data = new String[3];
            data[0] = countryTextField.getText();
            data[1] = cityTextField.getText();
            data[2] = areaTextField.getText();
            String message;
            Controller myController = new Controller();
            message = myController.defineNewArea(data);
            JOptionPane.showMessageDialog(null, message);
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        clearButton.addActionListener(e -> {
            // Clear form action goes here
            countryTextField.setText("");
            cityTextField.setText("");
            areaTextField.setText("");
        });

        formPanel.add(countryLabel);
        formPanel.add(countryTextField);
        formPanel.add(cityLabel);
        formPanel.add(cityTextField);
        formPanel.add(areaLabel);
        formPanel.add(areaTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Define Area");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250); // Adjusted size

        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Define Area", new DefineAreaPanel());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
