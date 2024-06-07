package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GreenZoneDashboard extends JFrame {
    public GreenZoneDashboard() {
        System.out.println("Main Dashboard Opened!");
    }
    String myUserID;

    public GreenZoneDashboard(String userID) {
        setTitle("Green Zone Connect Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(87, 0, 112)); // Purple 900 color

        // Green Zone Connect Label
        JLabel titleLabel = new JLabel("Green Zone Connect", SwingConstants.LEFT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Navigation Links
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setBackground(new Color(87, 0, 112)); // Purple 900 color

        String[] navLinks = { "Home", "About", "Contact Us", "Logout" };
        for (String link : navLinks) {
            JButton navButton = new JButton(link);
            navButton.setForeground(Color.WHITE);
            navButton.setBackground(new Color(87, 0, 112)); // Purple 900 color
            navButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (link.equals("Home")) {
                        // Action for Home link
                    } else if (link.equals("About")) {
                        // Action for About link
                    } else if (link.equals("Contact Us")) {
                        // Action for Contact Us link
                    } else if (link.equals("Logout")) {
                        dispose();
                        new LoginUI();
                    }
                }
            });
            navPanel.add(navButton);
        }
        headerPanel.add(navPanel, BorderLayout.EAST);

        // Add Header Panel to Frame
        add(headerPanel, BorderLayout.NORTH);

        // Main area
        JTabbedPane mainTabbedPane = new JTabbedPane();
        JPanel activityPanel = createActivityPanel(userID);
        JPanel groupPanel = createGroupPanel(userID);
        mainTabbedPane.addTab("Activity", activityPanel);
        mainTabbedPane.addTab("Groups", groupPanel);

        // Add change listener to refresh tab content on selection
        mainTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = mainTabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    mainTabbedPane.setComponentAt(0, createActivityPanel(userID));
                } else if (selectedIndex == 1) {
                    mainTabbedPane.setComponentAt(1, createGroupPanel(userID));
                }
            }
        });

        add(mainTabbedPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(87, 0, 112)); // Purple 900 color

        JLabel footerLabel = new JLabel("Developed by KSA Development Team");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(footerLabel, BorderLayout.CENTER);

        // Add Footer Panel to Frame
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createGroupPanel(String userID) {
        JPanel panel = new JPanel(new BorderLayout());

        // Group tabbed pane
        JTabbedPane groupTabbedPane = new JTabbedPane();

        JoinedGroups ViewGroups = new JoinedGroups(userID);
        groupTabbedPane.addTab("View Groups", ViewGroups);

        UpdateGroupsPanel updateGroupsPanel = new UpdateGroupsPanel();
        groupTabbedPane.add("Update Group", updateGroupsPanel);

        SearchGroupsPanel SearchGroupsPanel = new SearchGroupsPanel(userID);
        groupTabbedPane.addTab("Search and Join a Group", SearchGroupsPanel);

        CreateGroupsPanel createGroupsPanel = new CreateGroupsPanel(userID);
        groupTabbedPane.add("Create Group", createGroupsPanel);

        DefineAreaPanel defineAreaPanel = new DefineAreaPanel();
        groupTabbedPane.add("Define Area", defineAreaPanel);

        AlertMessagePanel sendAlert = new AlertMessagePanel();
        groupTabbedPane.add("Send Alerts", sendAlert);

        panel.add(groupTabbedPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActivityPanel(String userID) {
        JPanel panel = new JPanel(new BorderLayout());
        JTabbedPane activityTabbedPane = new JTabbedPane();

        CompletedPanel completedPanel = new CompletedPanel(userID);
        ScheduledPanel scheduledPanel = new ScheduledPanel(userID);
        OrganizedPanel organizedPanel = new OrganizedPanel(userID);
        DefineActivityPanel defineActivityPanel = new DefineActivityPanel();

        activityTabbedPane.addTab("Completed", completedPanel);
        activityTabbedPane.addTab("Scheduled", scheduledPanel);
        activityTabbedPane.addTab("Organized", organizedPanel);
        activityTabbedPane.addTab("Define Activity", defineActivityPanel);

        panel.add(activityTabbedPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GreenZoneDashboard::new);
    }
}
