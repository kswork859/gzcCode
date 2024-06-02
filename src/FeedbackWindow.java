package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedbackWindow extends JFrame {
    private JTextField scoreField;
    private JTextArea feedbackTextArea;

    public FeedbackWindow() {
        setTitle("Provide Feedback");
        setSize(400, 200);
        setResizable(false); // Fixed window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());

        // Score Panel
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel scoreLabel = new JLabel("Score:");
        scoreField = new JTextField(5);
        scorePanel.add(scoreLabel);
        scorePanel.add(scoreField);

        // Feedback Panel
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        JLabel feedbackLabel = new JLabel("Verbal Feedback:");
        feedbackTextArea = new JTextArea(5, 20);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackTextArea);
        feedbackPanel.add(feedbackLabel, BorderLayout.NORTH);
        feedbackPanel.add(feedbackScrollPane, BorderLayout.CENTER);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String score = scoreField.getText();
                String feedback = feedbackTextArea.getText();

                // Process the feedback and score here
                System.out.println("Score: " + score);
                System.out.println("Feedback: " + feedback);

                // Close the feedback window
                dispose();
            }
        });

        // Add components to content pane
        contentPane.add(scorePanel, BorderLayout.NORTH);
        contentPane.add(feedbackPanel, BorderLayout.CENTER);
        contentPane.add(submitButton, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FeedbackWindow feedbackWindow = new FeedbackWindow();
            feedbackWindow.setVisible(true);
        });
    }
}
