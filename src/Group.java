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
    public Object[][] displayJoinedGroups(String userId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object[]> resultList = new ArrayList<>();

        try {
            // Connect to the database
            conn = DatabaseConnection.getConnection();

            // SQL query to fetch joined groups
            String query = "SELECT g.groupid, g.groupname, c.countryname, ci.cityname, a.areaname, u.username AS adminname " +
                    "FROM belongings b " +
                    "JOIN grouptable g ON b.groupid = g.groupid " +
                    "JOIN area a ON g.areaid = a.areaid " +
                    "JOIN city ci ON a.cityid = ci.cityid " +
                    "JOIN country c ON ci.countryid = c.countryid " +
                    "JOIN users u ON g.adminid = u.userid " +
                    "WHERE b.userid = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String groupID = rs.getString("groupid");
                String groupName = rs.getString("groupname");
                String groupArea = rs.getString("countryname") + ", " + rs.getString("cityname") + ", " + rs.getString("areaname");
                String adminName = rs.getString("adminname");

                // Fetch group members
                String groupMembers = fetchGroupMembers(conn, groupID);

                resultList.add(new Object[]{groupID, groupName, groupArea, adminName, groupMembers});
            }
        } finally {
            // Close connections and statements
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return resultList.toArray(new Object[0][]);
    }

    // Method to fetch group members
    private String fetchGroupMembers(Connection conn, String groupID) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder members = new StringBuilder();

        try {
            String query = "SELECT u.username FROM belongings b JOIN users u ON b.userid = u.userid WHERE b.groupid = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, groupID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (members.length() > 0) {
                    members.append(", ");
                }
                members.append(rs.getString("username"));
            }
        } finally {
            // Close result set and statement
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }

        return members.toString();
    }
    public Object[][] searchGroup(String searchString) throws SQLException {
        // Connect to the database
        Connection conn = DatabaseConnection.getConnection();

        // Initialize the list to hold the search results
        List<Object[]> resultList = new ArrayList<>();

        // Search by Group Name
        searchGroupByGroupName(searchString, conn, resultList);

        // Search by Admin Name
        searchGroupByAdminName(searchString, conn, resultList);

        // Search by Country Name
        searchGroupByCountryName(searchString, conn, resultList);

        // Search by City Name
        searchGroupByCityName(searchString, conn, resultList);

        // Search by Area Name
        searchGroupByAreaName(searchString, conn, resultList);

        // Convert the resultList to a 2D array and return
        return resultList.toArray(new Object[resultList.size()][]);
    }

    private void searchGroupByGroupName(String searchString, Connection conn, List<Object[]> resultList) throws SQLException {
        String query = "SELECT g.groupid, g.groupname, co.countryname, c.cityname, a.areaname, u.username, COUNT(b.userid) AS totalMembers " +
                "FROM grouptable g " +
                "JOIN area a ON g.areaid = a.areaid " +
                "JOIN city c ON a.cityid = c.cityid " +
                "JOIN country co ON c.countryid = co.countryid " +
                "JOIN users u ON g.adminid = u.userid " +
                "LEFT JOIN belongings b ON g.groupid = b.groupid " +
                "WHERE g.groupname LIKE ? " +
                "GROUP BY g.groupid";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchString + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] group = {
                        rs.getInt("groupid"),
                        rs.getString("groupname"),
                        rs.getString("countryname"),
                        rs.getString("cityname"),
                        rs.getString("areaname"),
                        rs.getString("username"),
                        rs.getInt("totalMembers")
                };
                resultList.add(group);
            }
        }
    }

    private void searchGroupByAdminName(String searchString, Connection conn, List<Object[]> resultList) throws SQLException {
        String query = "SELECT g.groupid, g.groupname, co.countryname, c.cityname, a.areaname, u.username, COUNT(b.userid) AS totalMembers " +
                "FROM grouptable g " +
                "JOIN area a ON g.areaid = a.areaid " +
                "JOIN city c ON a.cityid = c.cityid " +
                "JOIN country co ON c.countryid = co.countryid " +
                "JOIN users u ON g.adminid = u.userid " +
                "LEFT JOIN belongings b ON g.groupid = b.groupid " +
                "WHERE u.username LIKE ? " +
                "GROUP BY g.groupid";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchString + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] group = {
                        rs.getInt("groupid"),
                        rs.getString("groupname"),
                        rs.getString("countryname"),
                        rs.getString("cityname"),
                        rs.getString("areaname"),
                        rs.getString("username"),
                        rs.getInt("totalMembers")
                };
                resultList.add(group);
            }
        }
    }

    private void searchGroupByCountryName(String searchString, Connection conn, List<Object[]> resultList) throws SQLException {
        String query = "SELECT g.groupid, g.groupname, co.countryname, c.cityname, a.areaname, u.username, COUNT(b.userid) AS totalMembers " +
                "FROM grouptable g " +
                "JOIN area a ON g.areaid = a.areaid " +
                "JOIN city c ON a.cityid = c.cityid " +
                "JOIN country co ON c.countryid = co.countryid " +
                "JOIN users u ON g.adminid = u.userid " +
                "LEFT JOIN belongings b ON g.groupid = b.groupid " +
                "WHERE co.countryname LIKE ? " +
                "GROUP BY g.groupid";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchString + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] group = {
                        rs.getInt("groupid"),
                        rs.getString("groupname"),
                        rs.getString("countryname"),
                        rs.getString("cityname"),
                        rs.getString("areaname"),
                        rs.getString("username"),
                        rs.getInt("totalMembers")
                };
                resultList.add(group);
            }
        }
    }

    private void searchGroupByCityName(String searchString, Connection conn, List<Object[]> resultList) throws SQLException {
        String query = "SELECT g.groupid, g.groupname, co.countryname, c.cityname, a.areaname, u.username, COUNT(b.userid) AS totalMembers " +
                "FROM grouptable g " +
                "JOIN area a ON g.areaid = a.areaid " +
                "JOIN city c ON a.cityid = c.cityid " +
                "JOIN country co ON c.countryid = co.countryid " +
                "JOIN users u ON g.adminid = u.userid " +
                "LEFT JOIN belongings b ON g.groupid = b.groupid " +
                "WHERE c.cityname LIKE ? " +
                "GROUP BY g.groupid";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchString + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] group = {
                        rs.getInt("groupid"),
                        rs.getString("groupname"),
                        rs.getString("countryname"),
                        rs.getString("cityname"),
                        rs.getString("areaname"),
                        rs.getString("username"),
                        rs.getInt("totalMembers")
                };
                resultList.add(group);
            }
        }
    }

    private void searchGroupByAreaName(String searchString, Connection conn, List<Object[]> resultList) throws SQLException {
        String query = "SELECT g.groupid, g.groupname, co.countryname, c.cityname, a.areaname, u.username, COUNT(b.userid) AS totalMembers " +
                "FROM grouptable g " +
                "JOIN area a ON g.areaid = a.areaid " +
                "JOIN city c ON a.cityid = c.cityid " +
                "JOIN country co ON c.countryid = co.countryid " +
                "JOIN users u ON g.adminid = u.userid " +
                "LEFT JOIN belongings b ON g.groupid = b.groupid " +
                "WHERE a.areaname LIKE ? " +
                "GROUP BY g.groupid";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchString + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] group = {
                        rs.getInt("groupid"),
                        rs.getString("groupname"),
                        rs.getString("countryname"),
                        rs.getString("cityname"),
                        rs.getString("areaname"),
                        rs.getString("username"),
                        rs.getInt("totalMembers")
                };
                resultList.add(group);
            }
        }
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

