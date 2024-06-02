import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UploadMultimedia extends JPanel {

    public UploadMultimedia() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding

        // Heading Label
        JLabel mediaHeadingLabel = new JLabel("Group Media", SwingConstants.CENTER);
        mediaHeadingLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(mediaHeadingLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel mediaFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Select Group Label and ComboBox
        JLabel selectGroupLabel = new JLabel("Select Group:");
        JComboBox<String> selectGroupComboBox = new JComboBox<>(new String[]{"G101", "G102", "G103"});
        gbc.gridx = 0;
        gbc.gridy = 0;
        mediaFormPanel.add(selectGroupLabel, gbc);
        gbc.gridx = 1;
        mediaFormPanel.add(selectGroupComboBox, gbc);

        // Select Activity Label and ComboBox
        JLabel selectActivityLabel = new JLabel("Select Activity:");
        JComboBox<String> selectActivityComboBox = new JComboBox<>(new String[]{"A101", "A102", "A103"});
        gbc.gridx = 0;
        gbc.gridy = 1;
        mediaFormPanel.add(selectActivityLabel, gbc);
        gbc.gridx = 1;
        mediaFormPanel.add(selectActivityComboBox, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton viewButton = new JButton("View Media");
        JButton uploadMediaButton = new JButton("Upload Media");

        // Add action listeners to buttons
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display the list of media for the selected group and activity
                // Replace this with actual logic to display media
                String[] mediaList = {"Media 1", "Media 2", "Media 3"}; // Example media list
                // Show media in a panel below the form
                JPanel mediaListPanel = new JPanel(new GridLayout(mediaList.length, 1));
                for (String media : mediaList) {
                    mediaListPanel.add(new JLabel(media));
                }
                // Add the media list panel below the form
                add(mediaListPanel, BorderLayout.SOUTH);
                // Revalidate and repaint the panel
                revalidate();
                repaint();
            }
        });

        uploadMediaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display file upload dialog
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    // User selected file(s)
                    // Display caption input dialog
                    String caption = JOptionPane.showInputDialog(null, "Enter caption for the media:");
                    // Perform upload operation and display success message
                    JOptionPane.showMessageDialog(null,
                            "Media has been uploaded successfully with caption: " + caption);
                }
            }
        });

        // Add buttons to the panel
        buttonsPanel.add(viewButton);
        buttonsPanel.add(uploadMediaButton);

        // Add buttons panel to the form panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mediaFormPanel.add(buttonsPanel, gbc);

        // Add form panel to the center of the groupMediaPanel
        add(mediaFormPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Group Media Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        UploadMultimedia groupMediaPanel = new UploadMultimedia();
        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Group Media", groupMediaPanel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
