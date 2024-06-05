package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    public void areaDetails(int areaID, String country, String city, String town) {
        // Your Code goes here
    }

    public void defineArea() {
        // Your Code goes here
    }

    public void updateGroup(String groupName, int adminID, int areaID, int userID) {
        // Your Code goes here
    }

    public boolean deleteGroup(String userID, String groupID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            conn = DatabaseConnection.getConnection();

            // Check if the user is the admin of the group
            String checkAdminQuery = "SELECT adminid FROM grouptable WHERE groupid = ? AND adminid = ?";
            stmt = conn.prepareStatement(checkAdminQuery);
            stmt.setInt(1, Integer.parseInt(groupID));
            stmt.setInt(2, Integer.parseInt(userID));
            rs = stmt.executeQuery();

            if (rs.next()) {
                // User is the admin, proceed to delete the group

                // Start a transaction
                conn.setAutoCommit(false);

                // Delete from participation table
                String deleteParticipationQuery = "DELETE FROM participation WHERE activityno IN (SELECT activityno FROM oscactivity WHERE groupid = ?)";
                stmt = conn.prepareStatement(deleteParticipationQuery);
                stmt.setInt(1, Integer.parseInt(groupID));
                stmt.executeUpdate();

                // Delete from multimedia table
                String deleteMultimediaQuery = "DELETE FROM multimedia WHERE activityno IN (SELECT activityno FROM oscactivity WHERE groupid = ?)";
                stmt = conn.prepareStatement(deleteMultimediaQuery);
                stmt.setInt(1, Integer.parseInt(groupID));
                stmt.executeUpdate();

                // Delete from oscactivity table
                String deleteOscactivityQuery = "DELETE FROM oscactivity WHERE groupid = ?";
                stmt = conn.prepareStatement(deleteOscactivityQuery);
                stmt.setInt(1, Integer.parseInt(groupID));
                stmt.executeUpdate();

                // Delete from belongings table
                String deleteBelongingsQuery = "DELETE FROM belongings WHERE groupid = ?";
                stmt = conn.prepareStatement(deleteBelongingsQuery);
                stmt.setInt(1, Integer.parseInt(groupID));
                stmt.executeUpdate();

                // Finally, delete from grouptable
                String deleteGroupQuery = "DELETE FROM grouptable WHERE groupid = ?";
                stmt = conn.prepareStatement(deleteGroupQuery);
                stmt.setInt(1, Integer.parseInt(groupID));
                int rowsAffected = stmt.executeUpdate();

                // Commit the transaction
                conn.commit();
                return rowsAffected > 0;
            } else {
                // User is not the admin
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Rollback the transaction in case of any error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            return false;
        } finally {
            // Close connections and statements
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void scheduleActivity() {
        // Your Code goes here
    }

    public void selectDate(String activityName, int groupID, String activityDate) {
        // Your Code goes here
    }

    public void sendAlerts(String[] data) {
        System.out.println(data[0]);
        System.out.println(data[1]);
        System.out.println(data[2]);
        System.out.println(data[3]);
    }

    public void giveFeedback(int activityID, int groupID, int activityScore) {
        // Your Code goes here
    }
}
