package src;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Activity {
    public Vector<Vector<String>> displayActivity() {
        Vector<Vector<String>> rowData = new Vector<>();
        Vector<String> row = new Vector<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                row.add("Row" + j);
            }
            rowData.add(row);
        }
        return rowData;
    }

    public void uploadmedia(String[] mediaData) {
        // insert data into data base
        System.out.println(mediaData[0]);
        System.out.println(mediaData[1]);
    }
    public boolean markParticipation(String groupID, String userID)
    {
        System.out.print("Data has Reached for Mark Participation");
        return true;
    }
    public static Object[][] getOrganizedActivities() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Object[][] organizedActivities = null;

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // SQL query to retrieve organized activities
            String sql = "SELECT activityno, groupid, activitytypeid FROM oscactivity";

            // Prepare the statement
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Populate the organized activities list
            List<Object[]> organizedActivityList = new ArrayList<>();
            while (resultSet.next()) {
                Object[] activity = new Object[3];
                activity[0] = resultSet.getInt("activityno");
                activity[1] = resultSet.getInt("groupid");
                activity[2] = resultSet.getInt("activitytypeid");
                organizedActivityList.add(activity);
            }

            // Convert list to array
            organizedActivities = organizedActivityList.toArray(new Object[0][0]);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return organizedActivities;
    }

    // Function to get scheduled activities from the database
    public static Object[][] getScheduledActivities() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Object[][] scheduledActivities = null;

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // SQL query to retrieve scheduled activities
            String sql = "SELECT activityno, groupid, activitytypeid FROM oscactivity " +
                         "WHERE activitystartingdate IS NOT NULL AND activityenddate IS NOT NULL";

            // Prepare the statement
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Populate the scheduled activities list
            List<Object[]> scheduledActivityList = new ArrayList<>();
            while (resultSet.next()) {
                Object[] activity = new Object[3];
                activity[0] = resultSet.getInt("activityno");
                activity[1] = resultSet.getInt("groupid");
                activity[2] = resultSet.getInt("activitytypeid");
                scheduledActivityList.add(activity);
            }

            // Convert list to array
            scheduledActivities = scheduledActivityList.toArray(new Object[0][0]);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return scheduledActivities;
    }

    // Function to get completed activities from the database
    public static Object[][] getCompletedActivities() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Object[][] completedActivities = null;

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // SQL query to retrieve completed activities
            String sql = "SELECT activityno, groupid, activitytypeid FROM oscactivity " +
                         "WHERE activityenddate < CURRENT_DATE AND score IS NOT NULL";

            // Prepare the statement
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Populate the completed activities list
            List<Object[]> completedActivityList = new ArrayList<>();
            while (resultSet.next()) {
                Object[] activity = new Object[3];
                activity[0] = resultSet.getInt("activityno");
                activity[1] = resultSet.getInt("groupid");
                activity[2] = resultSet.getInt("activitytypeid");
                completedActivityList.add(activity);
            }

            // Convert list to array
            completedActivities = completedActivityList.toArray(new Object[0][0]);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return completedActivities;
    }
}