package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String userName;
    private String userID;
    private String userEmail;
    private String userPhone;
    private String userPassword;
    private String userAddress;

    // Getter for userID
    public boolean createGroup(String[] data) {
        Connection conn = null;
        PreparedStatement pstmt = null;
    
        try {
            conn = DatabaseConnection.getConnection();
            String insertQuery = "INSERT INTO grouptable (groupname, areaid, adminid) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, data[1]);  // groupname
            pstmt.setInt(2, Integer.parseInt(data[4]));  // areaid
            pstmt.setInt(3, Integer.parseInt(data[0]));  // adminid
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
    

    public String getUserID() {
        return userID;
    }

    // joinGroup method

    public void joinGroup(String[] info) {
        System.out.println("This is Joined Group Function");
    }

    public boolean loginToAcc(String[] data) {
    String userID = data[0];
    String userPassword = data[1];
    
    try (Connection connection = DatabaseConnection.getConnection()) {
        String sql = "SELECT userpassword FROM users WHERE userid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userID);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String dbPassword = resultSet.getString("userpassword");
            if (userPassword.equals(dbPassword)) {
                return true; // Successful login
            } else {
                System.out.println("Invalid password");
            }
        } else {
            System.out.println("User not found");
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
    
    return false; // Login failed
}


    public boolean registerUser(String[] data) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("this is my name");
            String sql = "INSERT INTO users (userid, username, useremail, userpassword, useraddress, userphone) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters
            statement.setString(1, data[0]); // userID
            statement.setString(2, data[1]); // username
            statement.setString(3, data[2]); // useremail
            statement.setString(4, data[3]); // userpassword
            statement.setString(5, data[4]); // useraddress
            statement.setString(6, data[5]); // userphone

            // Execute the query
            int rowsInserted = statement.executeUpdate();

            // Check if the user was inserted successfully
            if (rowsInserted > 0) {
                System.out.println("User registered successfully.");
                return true;
            } else {
                System.out.println("Failed to register user.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception appropriately
        }
    }
}
