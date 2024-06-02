package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Area {
     private int insertCountry(String countryName) throws SQLException {
        String sql = "INSERT INTO country (countryname) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, countryName);
            pstmt.executeUpdate();
            int countryId;
            try (var generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    countryId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating country failed, no ID obtained.");
                }
            }
            return countryId;
        }
    }

    // Method to insert data into the city table
    private int insertCity(String cityName, int countryId) throws SQLException {
        String sql = "INSERT INTO city (cityname, countryid) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cityName);
            pstmt.setInt(2, countryId);
            pstmt.executeUpdate();
            int cityId;
            try (var generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cityId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating city failed, no ID obtained.");
                }
            }
            return cityId;
        }
    }

    // Method to insert data into the area table
    private void insertArea(String areaName, int cityId) throws SQLException {
        String sql = "INSERT INTO area (areaname, cityid) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, areaName);
            pstmt.setInt(2, cityId);
            pstmt.executeUpdate();
        }
    }

    // Method to define a new area
    public String defineArea(String[] data) {
        String countryName = data[0];
        String cityName = data[1];
        String areaName = data[2];

        try {
            // Insert country
            int countryId = insertCountry(countryName);

            // Insert city
            int cityId = insertCity(cityName, countryId);

            // Insert area
            insertArea(areaName, cityId);

            return "Area inserted successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to insert area.";
        }
    }
    
    public List<String> getCityNamesByCountry(String countryName) {
        List<String> cityNames = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT cityname FROM city c JOIN country cn ON c.countryid = cn.countryid WHERE cn.countryname = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, countryName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String cityName = resultSet.getString("cityname");
                cityNames.add(cityName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityNames;
    }
    public List<String> getCountryNames() {
    List<String> countryNames = new ArrayList<>();
    try (Connection connection = DatabaseConnection.getConnection()) {
        String sql = "SELECT countryname FROM country";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String countryName = resultSet.getString("countryname");
            countryNames.add(countryName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return countryNames;
}

    
}
