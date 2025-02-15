package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
//import java.util.regex.*;

public class Controller {
    public Object[][] getDefinedActivity()
    {
        Activity groupactivity = new Activity();
        return groupactivity.getDefinedActivity();
    }

    public boolean deleteGroup(String userID, String groupID) {
        Admin group = new Admin();
        return group.deleteGroup(userID, groupID);
    }

    public String defineNewArea(String[] data) {
        Area newArea = new Area();
        return newArea.defineArea(data);
    }

    public List<String> getCountryNames() {
        Area countries = new Area();
        return countries.getCountryNames();
    }

    public List<String> getCityNamesByCountry(String data) {
        Area city = new Area();
        return city.getCityNamesByCountry(data);
    }

    public List<String> getAreaNamesByCity(String data) {
        Area city = new Area();
        return city.getAreaNamesByCityName(data);
    }

    public void sendAlert(String[] data) {
        Admin admin = new Admin();
        admin.sendAlerts(data);
    }

    public boolean registerUser(String[] data) {
        User isUserReg = new User();

        if (isUserReg.registerUser(data)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean login(String[] data) {
        User weLogin = new User();
        boolean myresult;
        myresult = weLogin.loginToAcc(data);
        return myresult;
    }

    public void uploadMultimedia(String[] data) {
        Multimedia myuser = new Multimedia();
        myuser.uploadmedia(data);
    }

    public Vector<Vector<String>> passActivityToUI() {

        Activity A = new Activity();
        Vector<Vector<String>> rowData = new Vector<>();
        rowData = A.displayActivity();
        return rowData;
    }

    public Object[][] displayJoinedGroups(String userId) {
        Group myGroup = new Group();
        Object[][] groupsData = null;
        try {
            groupsData = myGroup.displayJoinedGroups(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log, display error message, etc.)
        }
        return groupsData;
    }

    public boolean createGroup(String[] data) {
        Group createGroup = new Group();
        createGroup.createGroup(data);
        return false;
    }

    public Object[][] searchGroup(String groupArea) throws Exception {
        Group searchedGroups = new Group();
        Object[][] data = searchedGroups.searchGroup(groupArea);
        return data;
    }

    public Object[][] displayAllGroups(String groupName) {
        Group allGroups = new Group();
        Object[][] data = allGroups.displayAllGroups(groupName);
        return data;
    }

    public boolean joinGroup(String[] data) {
        User joinGroup = new User();
        return joinGroup.joinGroup(data);
    }

    public boolean markParticipation(String groupID, String userID) {
        Activity markParticipation = new Activity();
        markParticipation.markParticipation(groupID, userID);
        return true;
    }
    public boolean organizeActivity(String[] data)
    {
        Activity myActivity = new Activity();
        return myActivity.organizeActivity(data);
    }
    public Object[][] getOrganizedActivity(String userID) {
        Activity organizedActivity = new Activity();
        return organizedActivity.getOrganizedActivities(userID);
        
    }
    public void saveActivity(String name)
    {
        Activity defineActivity = new Activity();
        defineActivity.defineActivity(name);
    }
    public String scheduleActivity(String[] data) {
        try {
            // Extract data from the array
            int groupId = Integer.parseInt(data[0]);
            int activityNo = Integer.parseInt(data[1]);
            LocalDate startingDate = LocalDate.parse(data[2]);
            LocalDate endingDate = LocalDate.parse(data[3]);
            LocalTime startTime = LocalTime.parse(data[4]);
            LocalTime endTime = LocalTime.parse(data[5]);
    
            // Validate the data
            if (startingDate.isAfter(endingDate)) {
                return "Error: Starting date cannot be after ending date.";
            }
    
            if (startingDate.equals(endingDate) && startTime.compareTo(endTime) >= 0) {
                return "Error: Starting time must be before ending time.";
            }
    
            // Call the Activity class method to schedule the activity
            boolean success = Activity.scheduleActivity(groupId, activityNo, startingDate, endingDate, startTime, endTime);
    
            // Return success or error message based on the result
            if (success) {
                return "Success: Activity updated successfully!";
            } else {
                return "Error: Failed to update activity.";
            }
        } catch (NumberFormatException e) {
            return "Error: Invalid number format.";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Error: Insufficient data provided.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    public Object[][] getScheduledActivities(String userID) {
        Activity scheduleActivity = new Activity();
        return scheduleActivity.getScheduledActivities(userID);
    }
    public Object[][] getCompletedActivities(String userID) {
        Activity scheduleActivity = new Activity();
        return scheduleActivity.getCompletedActivities(userID);
    }
    public void updateActivityFeedbackAndScore(int activityNo, double score, String feedback){

        Activity myactivity = new Activity();
        myactivity.updateActivityFeedbackAndScore(activityNo, score, feedback);
    }
}