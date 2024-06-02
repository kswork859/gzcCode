package src;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewGroups extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private boolean isAdmin; // This flag indicates if the user is an admin

    public ViewGroups(boolean isAdmin) {
        this.isAdmin = isAdmin; // Initialize the admin flag
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(50, 50, 50, 50)); // Add padding

        // Heading Label
        JLabel viewGroupsHeadingLabel = new JLabel("View Groups", SwingConstants.CENTER);
        viewGroupsHeadingLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(viewGroupsHeadingLabel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());

        // Table Data
        String[] columnNames = { "Group ID", "Group Name", "Group Area", "Admin Name", "Members" };
        Controller mygroups = new Controller();
        Object[][] data = mygroups.displayJoinedGroups();

        // Create Table Model
        model = new DefaultTableModel(data, columnNames);

        // Create Table
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);

        // Add Table to Scroll Pane
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add Table Panel to View Groups Panel
        add(tablePanel, BorderLayout.CENTER);

        // Add Mouse Listener for right-click context menu
        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    table.setRowSelectionInterval(row, row);
                    showPopupMenu(e, row);
                }
            }
        });
    }

    private void showPopupMenu(MouseEvent e, int row) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem viewMediaItem = new JMenuItem("View Media");
        viewMediaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MediaGalleryWindow();
            }
        });
        popupMenu.add(viewMediaItem);
        
        JMenuItem viewGroupMembersItem = new JMenuItem("View Group Members");
        popupMenu.add(viewGroupMembersItem);

        JMenuItem postAlertMessageItem = new JMenuItem("Send Alert");
        postAlertMessageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SendAlertMessage();
            }
        });
        popupMenu.add(postAlertMessageItem);

        JMenuItem viewAlertMessageItem = new JMenuItem("View Alert Message");
        popupMenu.add(viewAlertMessageItem);

        JMenuItem deleteGroupItem = new JMenuItem("Delete Group");
        deleteGroupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteGroup(row);
            }
        });
        popupMenu.add(deleteGroupItem);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void handleDeleteGroup(int row) {
        if (!isAdmin) {
            JOptionPane.showMessageDialog(this, "Error: You are not the admin of this group.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this group?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // Get the data from the selected row
            String[] rowData = new String[table.getColumnCount()];
            for (int col = 0; col < table.getColumnCount(); col++) {
                rowData[col] = table.getValueAt(row, col).toString();
            }

            // Call the function with the row data (example: deleteGroup(rowData))
            Controller deleteGroup = new Controller();
            deleteGroup.deleteGroup(rowData);

            // Remove the selected row from the table
            model.removeRow(row);
        }
    }

    // Example function to demonstrate handling the row data
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Group Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JTabbedPane groupTabbedPane = new JTabbedPane();
            groupTabbedPane.addTab("View Groups", new ViewGroups(true)); // Pass true if the user is an admin

            frame.add(groupTabbedPane);
            frame.setVisible(true);
        });
    }
}
