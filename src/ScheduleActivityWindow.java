package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.text.SimpleDateFormat; // Add this import for SimpleDateFormat
import java.util.Date; // Add this import for Date
import java.time.format.DateTimeFormatter;

public class ScheduleActivityWindow extends JFrame {

    private JTextField groupIdField;
    private JTextField activityIdField;
    private JSpinner startingDateSpinner;
    private JSpinner endingDateSpinner;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;

    public ScheduleActivityWindow() {

    }

    public ScheduleActivityWindow(String groupID, String activityID) {
        setTitle("Schedule Activity");
        setSize(500, 350);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 5)); // Reduce the number of rows to 6

        formPanel.add(new JLabel("Group ID:"));
        groupIdField = new JTextField();
        groupIdField.setText(groupID);
        groupIdField.setEditable(false);
        formPanel.add(groupIdField);

        formPanel.add(new JLabel("Activity ID:"));
        activityIdField = new JTextField();
        activityIdField.setText(activityID);
        activityIdField.setEditable(false);
        formPanel.add(activityIdField);

        formPanel.add(new JLabel("Activity Starting Date:"));
        startingDateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, 1));
        JSpinner.DateEditor startingDateEditor = new JSpinner.DateEditor(startingDateSpinner, "MM/dd/yy");
        startingDateSpinner.setEditor(startingDateEditor);
        startingDateSpinner.setEnabled(false); // Make the starting date spinner not editable
        formPanel.add(startingDateSpinner);

        formPanel.add(new JLabel("Activity Ending Date:"));
        endingDateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, 1));
        JSpinner.DateEditor endingDateEditor = new JSpinner.DateEditor(endingDateSpinner, "MM/dd/yy");
        endingDateSpinner.setEditor(endingDateEditor);
        formPanel.add(endingDateSpinner);

        formPanel.add(new JLabel("Activity Start Time:"));
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm:ss");
        startTimeSpinner.setEditor(startTimeEditor);
        startTimeSpinner.setEnabled(false); // Make the starting time spinner not editable
        formPanel.add(startTimeSpinner);

        formPanel.add(new JLabel("Activity End Time:"));
        endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm:ss");
        endTimeSpinner.setEditor(endTimeEditor);
        formPanel.add(endTimeSpinner);

        contentPane.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveAction();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    private void handleSaveAction() {
        int groupId = Integer.parseInt(groupIdField.getText());
        int activityNo = Integer.parseInt(activityIdField.getText());
        LocalDate startingDate = ((java.util.Date) startingDateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endingDate = ((java.util.Date) endingDateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime startTime = ((java.util.Date) startTimeSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime endTime = ((java.util.Date) endTimeSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    
        // Create a String array with the data
        String[] data = {
                String.valueOf(groupId),
                String.valueOf(activityNo),
                startingDate.toString(),
                endingDate.toString(),
                startTime.toString(),
                endTime.toString()
        };
    
        // Pass the data to the controller
        Controller controller = new Controller();
        String message = controller.scheduleActivity(data);
    
        // Display appropriate message to the user
        if (message.startsWith("Success")) {
            JOptionPane.showMessageDialog(this,
                    "Activity updated successfully.\n\n" +
                            "Activity details:\n" +
                            "Group ID: " + groupId + "\n" +
                            "Activity No: " + activityNo + "\n" +
                            "Activity Starting Date: " + startingDate + "\n" +
                            "Activity Ending Date: " + endingDate + "\n" +
                            "Activity Start Time: " + startTime + "\n" +
                            "Activity End Time: " + endTime);
        } else {
            JOptionPane.showMessageDialog(this, "Error: " + message);
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScheduleActivityWindow scheduleWindow = new ScheduleActivityWindow();
            scheduleWindow.setVisible(true);
        });
    }
}
