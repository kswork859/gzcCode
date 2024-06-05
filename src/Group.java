package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {
    public boolean createGroup(String[] data) {
        System.out.print("My Name is Khurram");
        Connection conn = null;
        PreparedStatement pstmt = null;
    
        try {
            conn = DatabaseConnection.getConnection();
            String insertQuery = "INSERT INTO grouptable (groupname, areaid, adminid) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, data[1]);  // groupName
            pstmt.setInt(2, new Area().getAreaIdByCityName(data[3], data[4]));  // areaId
            pstmt.setInt(3, Integer.parseInt(data[0]));  // adminId
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Group created successfully!");
                return true;
            } else {
                System.out.println("Failed to create group.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void searchMyGroup(String groupName) {
        // Method body
    }

    public void selectGroup(String groupName) {
        // Method body
    }

    public void updateGroup(String groupName, int adminID, int areaID, int userID) {
        // Method body
    }

    public void viewGroup() {
        // Method body
    }

    

    public void joinGroup(int userID) {
        // Method body
    }
    public Object[][] displayJoinedGroups()
    {
        Object[][] data = {
                { "G101", "Group 1", "Area 1", "Admin 1", "Member 1, Member 2" },
                { "G102", "Group 2", "Area 2", "Admin 2", "Member 3, Member 4" },
                { "G103", "Group 3", "Area 3", "Admin 3", "Member 5, Member 6" }
        };
        return data;
    }
    public Object[][] searchGroup(String areaName) throws SQLException {
        // Connect to your database
        Connection conn = DatabaseConnection.getConnection(); // Replace with your connection logic
      
        // Prepare a statement to search groups and user details
        String sql = "SELECT g.groupname, u.username AS adminName, c.countryname, ci.cityname, a.areaname, COUNT(b.userid) AS memberCount "
            + "FROM grouptable g "
            + "INNER JOIN area a ON g.areaid = a.areaid "
            + "INNER JOIN city ci ON a.cityid = ci.cityid "
            + "INNER JOIN country c ON ci.countryid = c.countryid "
            + "INNER JOIN users u ON g.adminid = u.userid " // Use INNER JOIN here
            + "LEFT JOIN belongings b ON g.groupid = b.groupid "
            + "WHERE a.areaname = ? "
            + "GROUP BY g.groupid";

      
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, areaName); // Set the area name parameter
      
        // Execute the query and store results
        ResultSet resultSet = statement.executeQuery();
        List<Object[]> resultList = new ArrayList<>();
        while (resultSet.next()) {
          String groupName = resultSet.getString("groupname");
          String adminName = resultSet.getString("adminName");
          String countryName = resultSet.getString("countryname");
          String cityName = resultSet.getString("cityName");
          String area = resultSet.getString("areaname");
          int memberCount = resultSet.getInt("memberCount");
      
          Object[] rowData = {groupName, countryName, cityName, area, adminName, memberCount};
          resultList.add(rowData);
        }
      
        // Close resources
        resultSet.close();
        statement.close();
        conn.close();
      
        return resultList.toArray(new Object[resultList.size()][]);
      }
      

    public Object[][] displayAllGroups(String groupName)
    {
        Object[][] data = {
                { "G101", "Group 1", "Area 1", "Admin 1", "Member 1, Member 2" },
                { "G102", "Group 2", "Area 2", "Admin 2", "Member 3, Member 4" },
                { "G103", "Group 3", "Area 3", "Admin 3", "Member 5, Member 6" }
        };
        return data;
    }
    
}

