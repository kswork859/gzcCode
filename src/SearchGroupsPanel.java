package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchGroupsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    
    public SearchGroupsPanel()
    {
      //  System.out.println("Awais BHSDK");
    }
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

        String[] columnNames = {"Group Name", "Country", "City", "Area","Admin Name", "Total Members" };
        //Controller displayAllGroups = new Controller();
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames);
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
            System.out.println("Join Group was pressed");
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
                System.out.println("Data Updated!");
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
        model.setDataVector(data, new String[] { "Group Name", "Country", "City", "Area","Admin Name", "Total Members"  });
    }

    private void joinGroup(String userid) {
        int selectedRow = table.getSelectedRow();
      
        // Check if a row is selected
        if (selectedRow != -1) {
          Object value = model.getValueAt(selectedRow, 0);
      
          // Check if a value exists at the first column (Group Name)
          if (value != null) {
            String groupName = (String) value; // Cast value to String
            String[] data = new String[2];
            data[0] = userid;
            data[1] = groupName;
            Controller joinGroup = new Controller();
            joinGroup.joinGroup(data);
            JOptionPane.showMessageDialog(this, "You have joined the group: " + data[1]);
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

        SearchGroupsPanel joinGroupsPanel = new SearchGroupsPanel();
        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Join Groups", joinGroupsPanel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(groupTabbedPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
