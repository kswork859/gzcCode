package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrganizedPanel extends JPanel {

    private DefaultTableModel organizedTableModel;
    private JTable organizedTable;
    private String userID; // Added userID as a class-level variable

    public OrganizedPanel() {
    }

    public OrganizedPanel(String userID) {
        this.userID = userID; // Assign the userID to the class-level variable
        setLayout(new BorderLayout());

        // Organized activities table
        organizedTableModel = new DefaultTableModel();
        organizedTable = new JTable(organizedTableModel);
        JScrollPane organizedScrollPane = new JScrollPane(organizedTable);
        add(organizedScrollPane, BorderLayout.CENTER);

        // Add right-click menu for table
        organizedTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPopupMenu(e);
                }
            }
        });

        // Add the button to open the OrganizeActivityWindow
        JButton organizeActivityButton = new JButton("Organize Activity");
        organizeActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrganizeActivityWindow();
            }
        });

        // Add the refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTableData();
            }
        });

        // Create a panel to hold both buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(organizeActivityButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load data from the database
        loadDataFromDatabase(userID);
    }

    private void showPopupMenu(MouseEvent e) {
        JPopupMenu menu = new JPopupMenu();

        // Add the Refresh option to the context menu
        JMenuItem refreshItem = new JMenuItem("Refresh Table");
        refreshItem.addActionListener(e1 -> refreshTableData());
        menu.add(refreshItem);

        int row = organizedTable.rowAtPoint(e.getPoint());
        if (row >= 0 && row < organizedTable.getRowCount()) {
            organizedTable.setRowSelectionInterval(row, row);

            JMenuItem deleteItem = new JMenuItem("Delete Activity");
            deleteItem.addActionListener(e1 -> {
                int selectedRow = organizedTable.getSelectedRow();
                organizedTableModel.removeRow(selectedRow);
            });

            JMenuItem scheduleItem = new JMenuItem("Schedule Activity");
            scheduleItem.addActionListener(e1 -> {
                int selectedRow = organizedTable.getSelectedRow();
                if (selectedRow != -1) {
                    String groupID = organizedTable.getValueAt(selectedRow, 0).toString();
                    String activityID = (String) organizedTable.getValueAt(selectedRow, 2).toString(); // Assuming the activity ID is in the third column
                    // Pass the group ID and activity ID to the schedule activity window
                    ScheduleActivityWindow scheduleWindow = new ScheduleActivityWindow(groupID, activityID);
                    scheduleWindow.setVisible(true);
                }
            });
            
            JMenuItem markParticipation = new JMenuItem("Mark Participation");
            markParticipation.addActionListener(e1 -> {
                int selectedRow = organizedTable.getSelectedRow();
                if (selectedRow != -1) {
                    String groupID = (String) organizedTable.getValueAt(selectedRow, 0);
                    String userID = JOptionPane.showInputDialog(OrganizedPanel.this, "Enter User ID:");

                    if (userID != null) { // If the user didn't cancel the dialog
                        // Pass the group ID and user ID to the controller
                        Controller controller = new Controller();
                        boolean success = controller.markParticipation(groupID, userID);

                        if (success) {
                            JOptionPane.showMessageDialog(OrganizedPanel.this,
                                    "Participation marked successfully!");
                        } else {
                            JOptionPane.showMessageDialog(OrganizedPanel.this,
                                    "Failed to mark participation.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            menu.add(deleteItem);
            menu.add(scheduleItem);
            menu.add(markParticipation);
        }

        menu.show(this, e.getX(), e.getY());
    }

    private void openOrganizeActivityWindow() {
        // Fetch groups and activities data from the controller
        Controller controller = new Controller();
        Object[][] groups = controller.displayJoinedGroups(userID);
        Object[][] activities = controller.getDefinedActivity();

        // Open the OrganizeActivityWindow
        OrganizeActivityWindow organizeActivityWindow = new OrganizeActivityWindow(groups, activities);
        organizeActivityWindow.setVisible(true);
    }

    private void loadDataFromDatabase(String userID) {
        Controller controller = new Controller();
        Object[][] organizedActivities = controller.getOrganizedActivity(userID);

        // Update table data
        String[] columnNames = {"Group ID", "Group Name", "Activity ID", "Activity Name", "Activity Description"};
        organizedTableModel.setDataVector(organizedActivities, columnNames);
    }

    private void refreshTableData() {
        loadDataFromDatabase(userID);
    }
}
