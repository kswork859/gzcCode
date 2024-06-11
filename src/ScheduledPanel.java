package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class ScheduledPanel extends JPanel {

    private DefaultTableModel scheduledTableModel;
    private JTable scheduledTable;
    private String userID;

    public ScheduledPanel() {
    }

    public ScheduledPanel(String userID) {
        this.userID = userID;
        setLayout(new BorderLayout());

        // Column names for the table
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Group ID");
        columnNames.add("Group Name");
        columnNames.add("Activity No");
        columnNames.add("Activity Name");
        columnNames.add("Starting Date");
        columnNames.add("Ending Date");
        columnNames.add("Start Time");
        columnNames.add("End Time");

        // Scheduled activities table
        scheduledTableModel = new DefaultTableModel(columnNames, 0);
        scheduledTable = new JTable(scheduledTableModel);
        JScrollPane scheduledScrollPane = new JScrollPane(scheduledTable);
        add(scheduledScrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshScheduledActivities();
            }
        });
        add(refreshButton, BorderLayout.SOUTH);

        // Add mouse listener for right-click context menu
        scheduledTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger() && SwingUtilities.isRightMouseButton(e)) {
                    int row = scheduledTable.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < scheduledTable.getRowCount()) {
                        scheduledTable.setRowSelectionInterval(row, row);
                        showPopupMenu(e, row, scheduledTableModel);
                    }
                }
            }
        });

        // Initial load of scheduled activities
        refreshScheduledActivities();
    }

    private void showPopupMenu(MouseEvent e, int row, DefaultTableModel tableModel) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem markAsCompletedItem = new JMenuItem("Mark as Completed");
        markAsCompletedItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int activityNo = (int) tableModel.getValueAt(row, 2); // Assuming "Activity No" is the third column
                new FeedbackWindow(activityNo).setVisible(true);
                tableModel.removeRow(row);
            }
        });

        popupMenu.add(markAsCompletedItem);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void refreshScheduledActivities() {
        // Clear existing data
        scheduledTableModel.setRowCount(0);

        // Get scheduled activities from the controller
        Controller controller = new Controller();
        Object[][] scheduledActivities = controller.getScheduledActivities(userID);

        // Populate the table with the scheduled activities
        for (Object[] activity : scheduledActivities) {
            scheduledTableModel.addRow(activity);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Scheduled Activities");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            frame.add(new ScheduledPanel("user123")); // Pass a sample user ID
            frame.setVisible(true);
        });
    }
}

