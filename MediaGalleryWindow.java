import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MediaGalleryWindow extends JFrame {

    private JTable mediaTable;

    public MediaGalleryWindow() {
        setTitle("Media Gallery");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());

        // Sample data for the table
        Object[][] mediaData = {
                {"Photo 1", "photo1.jpg"},
                {"Photo 2", "photo2.jpg"},
                {"Video 1", "video1.mp4"},
                {"Video 2", "video2.mp4"}
        };

        // Column names
        String[] columnNames = {"Media Name", "File Name"};

        // Media table
        DefaultTableModel model = new DefaultTableModel(mediaData, columnNames);
        mediaTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(mediaTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Add mouse listener to the table
        mediaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = mediaTable.getSelectedRow();
                int col = mediaTable.getSelectedColumn();
                if (row != -1 && col != -1) {
                    String fileName = (String) mediaTable.getValueAt(row, col);
                    String mediaType = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
                    if (mediaType.equals("jpg") || mediaType.equals("png")) {
                        // Show image viewer for photos
                        ImageIcon icon = new ImageIcon(fileName);
                        JLabel label = new JLabel(icon);
                        JOptionPane.showMessageDialog(null, label, "View Photo", JOptionPane.PLAIN_MESSAGE);
                    } else if (mediaType.equals("mp4")) {
                        // Placeholder action for playing videos
                        JOptionPane.showMessageDialog(null, "Playing video: " + fileName, "Play Video", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Unsupported media type: " + mediaType, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        setContentPane(contentPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MediaGalleryWindow::new);
    }
}
