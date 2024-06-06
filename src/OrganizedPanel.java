package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class OrganizedPanel extends JPanel {

    private DefaultTableModel organizedTableModel;
    private JTable organizedTable;
    public OrganizedPanel()
    {

    }

    public OrganizedPanel(String userID) {
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
                    int row = organizedTable.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < organizedTable.getRowCount()) {
                        organizedTable.setRowSelectionInterval(row, row);
                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem deleteItem = new JMenuItem("Delete Activity");
                        deleteItem.addActionListener(e1 -> {
                            int selectedRow = organizedTable.getSelectedRow();
                            organizedTableModel.removeRow(selectedRow);
                        });
                        JMenuItem scheduleItem = new JMenuItem("Schedule Activity");
                        scheduleItem.addActionListener(e1 -> {
                            // Open the schedule activity window
                            ScheduleActivityWindow scheduleWindow = new ScheduleActivityWindow();
                            scheduleWindow.setVisible(true);
                        });
                        JMenuItem markParticipation = new JMenuItem("Mark Participation");
                        markParticipation.addActionListener(e1 -> {
                            int selectedRow = organizedTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String groupID = (String) organizedTable.getValueAt(selectedRow, 3);
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
                        menu.show(organizedTable, e.getX(), e.getY());
                    }
                }
            }
        });

        // Load data from the database
        loadDataFromDatabase(userID);
    }

    private void loadDataFromDatabase(String userID) {
        Controller controller = new Controller();
        Object[][] organizedActivities = controller.getOrganizedActivity(userID);

        // Update table data
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Group ID");
        columnNames.add("Group Name");
        columnNames.add("Activity ID");
        columnNames.add("Activity Name");
        columnNames.add("Activity Description");

        Vector<Vector<String>> rowData = new Vector<>();
        for (Object[] row : organizedActivities) {
            Vector<String> rowDataItem = new Vector<>();
            for (Object value : row) {
                rowDataItem.add(value.toString());
            }
            rowData.add(rowDataItem);
        }

        organizedTableModel.setDataVector(rowData, columnNames);
    }
}
