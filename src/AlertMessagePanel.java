package src;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class AlertMessagePanel extends JPanel {

    private JList<String> alertList;

    public AlertMessagePanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding

        // Sample data for alerts
        Vector<String> alerts = new Vector<>();
        alerts.add("Date: 2024-05-25   Time: 10:00 AM   Activity Number: 001   Group No: G101   Status: Scheduled  Be On Time!");
        alerts.add("Date: 2024-05-26   Time: 11:00 AM   Activity Number: 002   Group No: G102   Status: Scheduled I am Khurram!");

        // Create JList to display alerts
        alertList = new JList<>(alerts);
        JScrollPane scrollPane = new JScrollPane(alertList);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Alert Messages");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);

            // Add AlertMessagePanel to the frame
            AlertMessagePanel alertMessagePanel = new AlertMessagePanel();
            frame.add(alertMessagePanel);

            frame.setVisible(true);
        });
    }
}
