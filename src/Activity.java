package src;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.sql.Types;

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
    // In Controller class

    public Object[][] getOrganizedActivities(String userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Object[][] organizedActivities = null;
    
        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();
    
            // SQL query to retrieve organized activities in groups joined by the user
            String sql = "SELECT oa.groupid, gt.groupname, oa.activitytypeid, da.activitytypename " +
                         "FROM oscactivity oa " +
                         "JOIN grouptable gt ON oa.groupid = gt.groupid " +
                         "JOIN belongings b ON oa.groupid = b.groupid " +
                         "JOIN definedactivity da ON oa.activitytypeid = da.activitytypeid " +
                         "WHERE b.userid = ?";
    
            // Prepare the statement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
    
            // Execute the query
            resultSet = preparedStatement.executeQuery();
    
            // Populate the organized activities list
            List<Object[]> organizedActivityList = new ArrayList<>();
            while (resultSet.next()) {
                Object[] activity = new Object[4];
                activity[0] = resultSet.getInt("groupid");
                activity[1] = resultSet.getString("groupname");
                activity[2] = resultSet.getInt("activitytypeid");
                activity[3] = resultSet.getString("activitytypename");
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
    public Object[][] getDefinedActivity() {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Object[]> activityData = new ArrayList<>();
    
        if (connection != null) {
            try {
                // Prepare the SQL statement
                String query = "SELECT activitytypeid, activitytypename FROM definedactivity";
                preparedStatement = connection.prepareStatement(query);
    
                // Execute the query
                resultSet = preparedStatement.executeQuery();
    
                // Populate the list with activity data
                while (resultSet.next()) {
                    int activityId = resultSet.getInt("activitytypeid");
                    String activityName = resultSet.getString("activitytypename");
                    Object[] row = {activityId, activityName};
                    activityData.add(row);
                }
    
                // Convert the list to a 2D array
                Object[][] activityDataArray = new Object[activityData.size()][2];
                for (int i = 0; i < activityData.size(); i++) {
                    activityDataArray[i] = activityData.get(i);
                }
    
                return activityDataArray;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the connection, statement, and result set
                try {
                    if (resultSet != null) resultSet.close();
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null; // Return null if connection is not successful or an error occurs
    }
    

    public boolean organizeActivity(String[] data) {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;

        if (connection != null) {
            try {
                // Prepare the SQL statement for insertion
                String query = "INSERT INTO oscactivity (groupid, activitytypeid, activitystartingdate, activityenddate, score) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);

                // Set values for the parameters
                preparedStatement.setInt(1, Integer.parseInt(data[0])); // Assuming groupid is the first element
                preparedStatement.setInt(2, Integer.parseInt(data[1])); // Assuming activitytypeid is the second element
                preparedStatement.setDate(3, null); // Assuming activity starting date is today
                preparedStatement.setDate(4, null); // Assuming activity end date is null initially
                preparedStatement.setNull(5, Types.DECIMAL); // Assuming score is null initially

                // Execute the insertion query
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Activity organized successfully!");
                    return true;
                } else {
                    System.out.println("Failed to organize activity!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the connection and statement
                try {
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    public void defineActivity(String name) {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if (connection != null) {
            try {
                // Check if activity name already exists in the database
                String query = "SELECT * FROM definedactivity WHERE activitytypename = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Activity name already exists
                    System.out.println("Activity name already exists in the database.");
                    return;
                }

                // Insert activity name into the database
                query = "INSERT INTO definedactivity (activitytypename) VALUES (?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Activity saved successfully.");
                } else {
                    System.out.println("Failed to save activity.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("An error occurred while saving activity.");
            } finally {
                // Close the result set, prepared statement, and connection
                closeResources(resultSet, preparedStatement, connection);
            }
        } else {
            System.out.println("Failed to establish database connection.");
        }
    }

    // Method to close result set, prepared statement, and connection
    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}