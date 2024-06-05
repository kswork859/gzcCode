package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JoinedGroups extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private boolean isAdmin; // This flag indicates if the user is an admin
    private String userID; // Class-level variable to store user ID

    public JoinedGroups() {
    }

    public JoinedGroups(String userId) {
        this.userID = userId; // Assign the received user ID to the class-level variable
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(50, 50, 50, 50)); // Add padding

        // Heading Label
        JLabel viewGroupsHeadingLabel = new JLabel("Joined Groups", SwingConstants.CENTER);
        viewGroupsHeadingLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(viewGroupsHeadingLabel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());

        // Table Data
        String[] columnNames = {"Group ID", "Group Name", "Group Area", "Admin Name", "Members"};
        Controller mygroups = new Controller();
        Object[][] data = mygroups.displayJoinedGroups(userId);

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
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    table.setRowSelectionInterval(row, row);
                    showPopupMenu(e, row);
                }
            }

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
        // Use the class-level userID
        String userID = this.userID;
    
        // Get group ID from the selected row
        String groupID = table.getValueAt(row, 0).toString();
    
        Controller controller = new Controller();
        boolean isDeleted = controller.deleteGroup(userID, groupID);
    
        if (isDeleted) {
            // Remove the selected row from the table
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "Group deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Error: You are not the admin of this group.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
