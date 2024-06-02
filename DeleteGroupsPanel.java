import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DeleteGroupsPanel extends JPanel {
    private JComboBox<String> mygroupComboBox;
    private JTextField groupNames;
    private JButton deleteButton;

    public DeleteGroupsPanel() {
        setLayout(new BorderLayout());

        // Heading Label
        JLabel deleteGroupsHeadingLabel = new JLabel("Delete Groups", SwingConstants.CENTER);
        deleteGroupsHeadingLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font size and style
        add(deleteGroupsHeadingLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel delformPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Combo Box
        mygroupComboBox = new JComboBox<>(new String[]{"Group 1", "Group 2", "Group 3"});
        JLabel mygroupLabel = new JLabel("Group No.: ");
        delformPanel.add(mygroupLabel);
        delformPanel.add(mygroupComboBox);

        groupNames = new JTextField(13);
        delformPanel.add(groupNames);

        // Add Form Panel to Delete Groups Panel
        add(delformPanel, BorderLayout.CENTER);

        // Delete Button
        deleteButton = new JButton("Delete");
        delformPanel.add(deleteButton);

        // Delete Button Action Listener
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] data = new String[2];
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this group?",
                        "Delete Group", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    // Delete group logic here
                    data[0] = (String) mygroupComboBox.getSelectedItem();
                    data[1] = groupNames.getText();
                    JOptionPane.showMessageDialog(null, "Group deleted successfully: " + data[1]);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Group Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JTabbedPane groupTabbedPane = new JTabbedPane();
        groupTabbedPane.addTab("Delete Groups", new DeleteGroupsPanel());

        frame.add(groupTabbedPane);
        frame.setVisible(true);
    }
}
