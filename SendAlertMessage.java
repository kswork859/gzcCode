import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendAlertMessage extends JFrame {

    private JTextField dateField;
    private JTextField timeField;
    private JTextField activityNumberField;
    private JTextField messageField;
    private JTextField groupNoField;
    private JTextField activityStatusField;

    public SendAlertMessage() {
        setTitle("Send Alert");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPane = new JPanel(new GridLayout(7, 2, 5, 5));

        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField();
        contentPane.add(dateLabel);
        contentPane.add(dateField);

        JLabel timeLabel = new JLabel("Time:");
        timeField = new JTextField();
        contentPane.add(timeLabel);
        contentPane.add(timeField);

        JLabel activityNumberLabel = new JLabel("Activity Number:");
        activityNumberField = new JTextField();
        contentPane.add(activityNumberLabel);
        contentPane.add(activityNumberField);

        JLabel messageLabel = new JLabel("Message:");
        messageField = new JTextField();
        contentPane.add(messageLabel);
        contentPane.add(messageField);

        JLabel groupNoLabel = new JLabel("Group Number:");
        groupNoField = new JTextField();
        contentPane.add(groupNoLabel);
        contentPane.add(groupNoField);

        JLabel activityStatusLabel = new JLabel("Activity Status:");
        activityStatusField = new JTextField();
        contentPane.add(activityStatusLabel);
        contentPane.add(activityStatusField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(cancelButton);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement sending alert message functionality here
                // For demonstration, just display the entered details
                String [] data = new String[6];
                   data[0] = dateField.getText();
                   data[1] = timeField.getText();
                   data[2] = activityNumberField.getText();
                   data[3] = messageField.getText();
                   data[4] = groupNoField.getText();
                   data[5] = activityStatusField.getText();

                   Controller sendAlertContol = new Controller();
                   sendAlertContol.sendAlert(data);

                // Close the window
                dispose();
            }
        });
        contentPane.add(sendButton);

        setContentPane(contentPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SendAlertMessage::new);
    }
}
