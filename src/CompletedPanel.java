package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

public class CompletedPanel extends JPanel {

    public CompletedPanel() {
        setLayout(new BorderLayout());

        // Sample data for the table
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Activity No");
        columnNames.add("Group ID");
        columnNames.add("Activity Type ID");
        columnNames.add("Activity Date");

        Controller controller = new Controller();
        Vector<Vector<String>> rowData = new Vector<>();
        rowData = controller.passActivityToUI();

        // Completed activities table
        DefaultTableModel completedTableModel = new DefaultTableModel(rowData, columnNames);
        JTable completedTable = new JTable(completedTableModel);
        JScrollPane completedScrollPane = new JScrollPane(completedTable);
        add(completedScrollPane, BorderLayout.CENTER);

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
                        viewMediaItem.addActionListener(e1 -> {
                            new MediaGalleryWindow();
                        });
                        popupMenu.add(feedbackItem);
                        popupMenu.add(uploadMediaItem);
                        popupMenu.add(viewMediaItem);
                        popupMenu.show(completedTable, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Completed Panel Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new CompletedPanel());
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
