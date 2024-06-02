import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class OrganizedPanel extends JPanel {

    private DefaultTableModel organizedTableModel;
    private JTable organizedTable;

    public OrganizedPanel() {
        setLayout(new BorderLayout());

        // Organize Activity Button
        JButton organizeButton = new JButton("Organize Activity");
        organizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrganizeActivityWindow();
            }
        });
        add(organizeButton, BorderLayout.NORTH);

        // Sample data for the table
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Activity Name");
        columnNames.add("Area Address");
        columnNames.add("Activity Description");
        columnNames.add("Group ID");
        columnNames.add("Group Name"); // New column for Group Name

        Vector<Vector<String>> rowData = new Vector<>();
        Vector<String> row1 = new Vector<>();
        row1.add("Organized Activity 1");
        row1.add("2024-05-20");
        row1.add("10:00 AM");
        row1.add("G101");
        row1.add("Group 1"); // Sample Group Name
        Vector<String> row2 = new Vector<>();
        row2.add("Organized Activity 2");
        row2.add("2024-05-21");
        row2.add("11:00 AM");
        row2.add("G102");
        row2.add("Group 2"); // Sample Group Name

        rowData.add(row1);
        rowData.add(row2);

        // Organized activities table
        organizedTableModel = new DefaultTableModel(rowData, columnNames);
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
                        deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = organizedTable.getSelectedRow();
                                organizedTableModel.removeRow(selectedRow);
                            }
                        });
                        JMenuItem scheduleItem = new JMenuItem("Schedule Activity");
                        scheduleItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Open the schedule activity window
                                ScheduleActivityWindow scheduleWindow = new ScheduleActivityWindow();
                                scheduleWindow.setVisible(true);
                            }
                        });
                        JMenuItem markparticipation = new JMenuItem("Mark Participation");
                        markparticipation.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
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
                            }
                        });

                        menu.add(deleteItem);
                        menu.add(scheduleItem);
                        menu.add(markparticipation);
                        menu.show(organizedTable, e.getX(), e.getY());
                    }
                }
            }
        });
    }

   /*  private void organizeActivity() {
        // Method to organize activity goes here
        // For now, let's add a sample organized activity to the table
        Vector<String> row = new Vector<>();
        row.add("New Organized Activity");
        row.add("2024-06-01");
        row.add("9:00 AM");
        row.add("G103");
        row.add("Group 3"); // Sample Group Name
        organizedTableModel.addRow(row);
    }*/
}
