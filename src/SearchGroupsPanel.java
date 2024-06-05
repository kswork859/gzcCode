package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchGroupsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private String selectedGroupID;

    public SearchGroupsPanel(String userID) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding

        JLabel headingLabel = new JLabel("Join Group", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(headingLabel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Group ID", "Group Name", "Country", "City", "Area", "Admin Name", "Total Members"};
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Context Menu for Table
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem joinGroupMenuItem = new JMenuItem("Join Group");

        joinGroupMenuItem.addActionListener(e -> {
            joinGroup(userID);
        });

        popupMenu.add(joinGroupMenuItem);

        table.setComponentPopupMenu(popupMenu);
        table.addMouseListener(new TableMouseListener(table, popupMenu));

        // Search Button Action
        searchButton.addActionListener(e -> {
            Controller searchedGroups = new Controller();
            try {
                Object[][] searchedResult = searchedGroups.searchGroup(searchField.getText());
                updateTableModel(searchedResult); // Update the table model with the search results
            } catch (Exception ex) {
                ex.printStackTrace();
                // Handle the exception, e.g., show an error message to the user
                JOptionPane.showMessageDialog(SearchGroupsPanel.this,
                        "An error occurred while searching: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void updateTableModel(Object[][] data) {
        model.setDataVector(data, new String[]{"Group ID", "Group Name", "Country", "City", "Area", "Admin Name", "Total Members"});
    }

    private void joinGroup(String userid) {
        int selectedRow = table.getSelectedRow();
    
        // Check if a row is selected
        if (selectedRow != -1) {
            selectedGroupID = model.getValueAt(selectedRow, 0).toString();
            // Pass selectedGroupID to controller class method
            String[] data = new String[]{userid, selectedGroupID};
            Controller joinGroupController = new Controller();
            boolean joined = joinGroupController.joinGroup(data);
            if (joined) {
                JOptionPane.showMessageDialog(this, "You have successfully joined the group: " + model.getValueAt(selectedRow, 1));
            } else {
                JOptionPane.showMessageDialog(this, "You have already joined this group.");
            }
        }
    }

    private class TableMouseListener extends MouseAdapter {
        private JTable table;
        private JPopupMenu popupMenu;

        public TableMouseListener(JTable table, JPopupMenu popupMenu) {
            this.table = table;
            this.popupMenu = popupMenu;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
            table.setRowSelectionInterval(row, row);
            if (e.isPopupTrigger()) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Join Groups");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        SearchGroupsPanel joinGroupsPanel = new SearchGroupsPanel("userID");
        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Join Groups", joinGroupsPanel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
