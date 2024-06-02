import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScheduleActivityWindow extends JFrame {

    private JTextField dateField;
    private JTextField timeField;

    public ScheduleActivityWindow() {
        setTitle("Schedule Activity");
        setSize(300, 200); // Set fixed size
        setResizable(false); // Make window non-resizable
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());

        // Date Panel
        JPanel datePanel = new JPanel();
        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField(10);
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        contentPane.add(datePanel, BorderLayout.NORTH);

        // Time Panel
        JPanel timePanel = new JPanel();
        JLabel timeLabel = new JLabel("Time:");
        timeField = new JTextField(10);
        timePanel.add(timeLabel);
        timePanel.add(timeField);
        contentPane.add(timePanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save action here
                String date = dateField.getText();
                String time = timeField.getText();
                
                System.out.println(date+time);
                // Process the date and time
                // For now, just display a message
                JOptionPane.showMessageDialog(ScheduleActivityWindow.this, "Activity scheduled successfully!");
                dispose(); // Close the window
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScheduleActivityWindow scheduleWindow = new ScheduleActivityWindow();
            scheduleWindow.setVisible(true);
        });
    }
}
