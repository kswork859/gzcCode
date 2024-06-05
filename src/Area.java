package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Area {
    // Method to check if a country exists
    private int getCountryId(String countryName) throws SQLException {
        String sql = "SELECT countryid FROM country WHERE LOWER(countryname) = LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, countryName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("countryid");
                } else {
                    return -1;
                }
            }
        }
    }

    // Method to check if a city exists under a specific country
    private int getCityId(String cityName, int countryId) throws SQLException {
        String sql = "SELECT cityid FROM city WHERE LOWER(cityname) = LOWER(?) AND countryid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cityName);
            pstmt.setInt(2, countryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cityid");
                } else {
                    return -1;
                }
            }
        }
    }

    // Method to check if an area exists under a specific city
    private boolean areaExists(String areaName, int cityId) throws SQLException {
        String sql = "SELECT areaid FROM area WHERE LOWER(areaname) = LOWER(?) AND cityid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, areaName);
            pstmt.setInt(2, cityId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Method to insert a country and return its ID
    private int insertCountry(String countryName) throws SQLException {
        int countryId = getCountryId(countryName);
        if (countryId != -1) {
            return countryId;
        }

        String sql = "INSERT INTO country (countryname) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, countryName);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating country failed, no ID obtained.");
                }
            }
        }
    }

    // Method to insert a city and return its ID
    private int insertCity(String cityName, int countryId) throws SQLException {
        int cityId = getCityId(cityName, countryId);
        if (cityId != -1) {
            return cityId;
        }

        String sql = "INSERT INTO city (cityname, countryid) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cityName);
            pstmt.setInt(2, countryId);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating city failed, no ID obtained.");
                }
            }
        }
    }

    // Method to insert an area
    private void insertArea(String areaName, int cityId) throws SQLException {
        if (areaExists(areaName, cityId)) {
            throw new SQLException("Area already exists.");
        }

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
            return e.getMessage();
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

    // Method to get area names by city ID
    public List<String> getAreaNamesByCityName(String cityName) {
        List<String> areaNames = new ArrayList<>();
        String getCityIdSql = "SELECT cityid FROM city WHERE LOWER(cityname) = LOWER(?)";
        String getAreaNamesSql = "SELECT areaname FROM area WHERE cityid = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement getCityIdStmt = conn.prepareStatement(getCityIdSql);
             PreparedStatement getAreaNamesStmt = conn.prepareStatement(getAreaNamesSql)) {
    
            // Get the city ID from the city name
            getCityIdStmt.setString(1, cityName);
            try (ResultSet cityRs = getCityIdStmt.executeQuery()) {
                if (cityRs.next()) {
                    int cityId = cityRs.getInt("cityid");
    
                    // Get the area names using the city ID
                    getAreaNamesStmt.setInt(1, cityId);
                    try (ResultSet areaRs = getAreaNamesStmt.executeQuery()) {
                        while (areaRs.next()) {
                            areaNames.add(areaRs.getString("areaname"));
                        }
                    }
                } else {
                    throw new SQLException("City not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return areaNames;
    }
    
}
