package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DefineAreaPanel extends JPanel {

    public DefineAreaPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding

        JLabel defineAreaHeading = new JLabel("Define Area", SwingConstants.CENTER);
        defineAreaHeading.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(defineAreaHeading, BorderLayout.NORTH); // Add heading above the form

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding to form

        Controller c = new Controller();
        List<String> countryNames = c.getCountryNames();
        JLabel countryLabel = new JLabel("Country Names:");
        JComboBox<String> countryComboBox = new JComboBox<>(countryNames.toArray(new String[0]));
        countryComboBox.setEditable(true);

        JLabel cityLabel = new JLabel("City Name:");
        JComboBox<String> cityComboBox = new JComboBox<>();
        cityComboBox.setEditable(true);

        JLabel areaLabel = new JLabel("Area Name:");
        JComboBox<String> areaComboBox = new JComboBox<>();
        areaComboBox.setEditable(true);

        // Add key listener for the country combo box to enable auto-complete
        countryComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String prefix = countryComboBox.getEditor().getItem().toString();
                for (int i = 0; i < countryComboBox.getItemCount(); i++) {
                    String item = countryComboBox.getItemAt(i);
                    if (item.toLowerCase().startsWith(prefix.toLowerCase())) {
                        countryComboBox.setSelectedIndex(i);
                        countryComboBox.getEditor().setItem(item);
                        countryComboBox.getEditor().selectAll();
                        return;
                    }
                }
            }
        });

        // Add key listener for the city combo box to enable auto-complete
        cityComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String prefix = cityComboBox.getEditor().getItem().toString();
                for (int i = 0; i < cityComboBox.getItemCount(); i++) {
                    String item = cityComboBox.getItemAt(i);
                    if (item.toLowerCase().startsWith(prefix.toLowerCase())) {
                        cityComboBox.setSelectedIndex(i);
                        cityComboBox.getEditor().setItem(item);
                        cityComboBox.getEditor().selectAll();
                        return;
                    }
                }
            }
        });

        // Add key listener for the area combo box to enable auto-complete
        areaComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String prefix = areaComboBox.getEditor().getItem().toString();
                for (int i = 0; i < areaComboBox.getItemCount(); i++) {
                    String item = areaComboBox.getItemAt(i);
                    if (item.toLowerCase().startsWith(prefix.toLowerCase())) {
                        areaComboBox.setSelectedIndex(i);
                        areaComboBox.getEditor().setItem(item);
                        areaComboBox.getEditor().selectAll();
                        return;
                    }
                }
            }
        });

        countryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCountry = (String) countryComboBox.getSelectedItem();
                List<String> cityNames = c.getCityNamesByCountry(selectedCountry);

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
                List<String> areaNames = c.getAreaNamesByCity(selectedCity);

                // Update the area combo box
                areaComboBox.removeAllItems();
                for (String areaName : areaNames) {
                    areaComboBox.addItem(areaName);
                }
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        saveButton.addActionListener(e -> {
            // Save action goes here
            String[] data = new String[3];
            data[0] = (String) countryComboBox.getSelectedItem();
            data[1] = (String) cityComboBox.getSelectedItem();
            data[2] = areaComboBox.getEditor().getItem().toString();
            String message;
            Controller myController = new Controller();
            message = myController.defineNewArea(data);
            JOptionPane.showMessageDialog(null, message);
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        clearButton.addActionListener(e -> {
            // Clear form action goes here
            countryComboBox.setSelectedItem("");
            cityComboBox.removeAllItems();
            areaComboBox.removeAllItems();
        });

        formPanel.add(countryLabel);
        formPanel.add(countryComboBox);
        formPanel.add(cityLabel);
        formPanel.add(cityComboBox);
        formPanel.add(areaLabel);
        formPanel.add(areaComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Define Area");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Adjusted size

        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Define Area", new DefineAreaPanel());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}