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

    public ScheduledPanel() {
        setLayout(new BorderLayout());

        // Sample data for the table
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Activity Name");
        columnNames.add("Date");
        columnNames.add("Time");

        Vector<Vector<String>> rowData = new Vector<>();
        Vector<String> row1 = new Vector<>();
        row1.add("Scheduled Activity 1");
        row1.add("2024-05-20");
        row1.add("10:00 AM");
        Vector<String> row2 = new Vector<>();
        row2.add("Scheduled Activity 2");
        row2.add("2024-05-21");
        row2.add("11:00 AM");

        rowData.add(row1);
        rowData.add(row2);

        // Scheduled activities table
        DefaultTableModel scheduledTableModel = new DefaultTableModel(rowData, columnNames);
        JTable scheduledTable = new JTable(scheduledTableModel);
        JScrollPane scheduledScrollPane = new JScrollPane(scheduledTable);
        add(scheduledScrollPane, BorderLayout.CENTER);

        // Add mouse listener for right-click context menu
        scheduledTable.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = scheduledTable.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < scheduledTable.getRowCount()) {
                        scheduledTable.setRowSelectionInterval(row, row);
                        showPopupMenu(e, row, scheduledTableModel);
                    }
                }
            }
        });
    }

    private void showPopupMenu(MouseEvent e, int row, DefaultTableModel tableModel) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem markAsCompletedItem = new JMenuItem("Mark as Completed");
        markAsCompletedItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.removeRow(row);
            }
        });

        popupMenu.add(markAsCompletedItem);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Scheduled Activities");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.add(new ScheduledPanel());
            frame.setVisible(true);
        });
    }
}
