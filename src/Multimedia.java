package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Multimedia {
    public void uploadmedia(String[] data) {
        String caption = data[0];
        String filePath = data[1];
        String userID = data[2];
        int activityNo = Integer.parseInt(data[3]);

        String insertSQL = "INSERT INTO multimedia (multimedialink, mediacaptions, userid, activityno) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, filePath);
            pstmt.setString(2, caption);
            pstmt.setInt(3, Integer.parseInt(userID));
            pstmt.setInt(4, activityNo);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Media uploaded successfully!");
            } else {
                System.out.println("Failed to upload media.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}