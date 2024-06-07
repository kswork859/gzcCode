package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

public class CompletedPanel extends JPanel {

    private DefaultTableModel completedTableModel;
    private JTable completedTable;
    private String userID;

    public CompletedPanel(String userID) {
        this.userID = userID;
        setLayout(new BorderLayout());

        // Column names for the table
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Activity No");
        columnNames.add("Group ID");
        columnNames.add("Group Name");
        columnNames.add("Activity Type ID");
        columnNames.add("Activity Name");
        columnNames.add("Starting Date");
        columnNames.add("Ending Date");
        columnNames.add("Start Time");
        columnNames.add("End Time");
        columnNames.add("Score");

        // Completed activities table
        completedTableModel = new DefaultTableModel(columnNames, 0);
        completedTable = new JTable(completedTableModel);
        JScrollPane completedScrollPane = new JScrollPane(completedTable);
        add(completedScrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshCompletedActivities());
        add(refreshButton, BorderLayout.SOUTH);

        // Add mouse listener to table for right-click context menu
        completedTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = completedTable.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < completedTable.getRowCount()) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem feedbackItem = new JMenuItem("Give Feedback");
                        JMenuItem uploadMediaItem = new JMenuItem("Upload Multimedia");
                        JMenuItem viewMediaItem = new JMenuItem("View Media");
                        feedbackItem.addActionListener(e1 -> {
                            // Open feedback window
                            FeedbackWindow feedbackWindow = new FeedbackWindow();
                            feedbackWindow.setVisible(true);
                        });
                        uploadMediaItem.addActionListener(e1 -> {
                            // Open file chooser for multimedia upload
                            JFileChooser fileChooser = new JFileChooser();
                            int option = fileChooser.showOpenDialog(null);
                            if (option == JFileChooser.APPROVE_OPTION) {
                                // User selected file(s)
                                File selectedFile = fileChooser.getSelectedFile();
                                String filePath = selectedFile.getAbsolutePath();
                                System.out.println("Selected file: " + filePath);
                                // Display caption input dialog
                                String caption = JOptionPane.showInputDialog(null, "Enter caption for the media:");
                                String[] data = new String[2];
                                data[0] = caption;
                                data[1] = filePath;
                                Controller uploadmedia = new Controller();
                                uploadmedia.uploadMultimedia(data);
                                // Perform upload operation and display success message
                                JOptionPane.showMessageDialog(null,
                                        "Media has been uploaded successfully with caption: " + caption);
                            }
                        });
                        viewMediaItem.addActionListener(e1 -> new MediaGalleryWindow());
                        popupMenu.add(feedbackItem);
                        popupMenu.add(uploadMediaItem);
                        popupMenu.add(viewMediaItem);
                        popupMenu.show(completedTable, e.getX(), e.getY());
                    }
                }
            }
        });

        // Initial load of completed activities
        refreshCompletedActivities();
    }

    private void refreshCompletedActivities() {
        // Clear existing data
        completedTableModel.setRowCount(0);

        // Get completed activities from the controller
        Controller controller = new Controller();
        Object[][] completedActivities = controller.getCompletedActivities(userID);

        // Populate the table with the completed activities
        for (Object[] activity : completedActivities) {
            completedTableModel.addRow(activity);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Completed Activities");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            frame.add(new CompletedPanel("user123")); // Pass a sample user ID
            frame.setVisible(true);
        });
    }
}
