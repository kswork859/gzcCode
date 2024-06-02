package src;
import java.util.List;
import java.util.Vector;
//import java.util.regex.*;

public class Controller {
    public String defineNewArea(String[] data)
    {
        Area newArea = new Area();
        return newArea.defineArea(data);
    }
    public List<String> getCountryNames()
    {
        Area countries = new Area();
        return countries.getCountryNames();
    }
    public List<String> getCityNamesbyCountry(String data)
    {
        Area city = new Area();
        return city.getCityNamesByCountry(data);
    }
    public void sendAlert(String[] data) {
        System.out.println("Khurram Shahzad is is Controller Class");
        Admin admin = new Admin();
        admin.sendAlerts(data);
    }

    public boolean registerUser(String[] data) {
        User isUserReg = new User();
        
        if(isUserReg.registerUser(data))
        {
            return true;
        }
        else{
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

    public void deleteGroup(String[] data) {
        Admin myadmin = new Admin();
        myadmin.deleteGroup(data);
    }

    public Vector<Vector<String>> passActivityToUI() {

        Activity A = new Activity();
        Vector<Vector<String>> rowData = new Vector<>();
        rowData = A.displayActivity();
        return rowData;
    }

    public Object[][] displayJoinedGroups() {
        Group myGroup = new Group();
        Object[][] groupsData = myGroup.displayJoinedGroups();
        return groupsData;

    }
    public boolean createGroup(String[] data)
    {
        Group createGroup = new Group();
        createGroup.createGroup(data);
        return false;
    }
    public Object[][] searchGroup(String groupName)
    {
        Group searchedGroups = new Group();
         Object[][] data = searchedGroups.searchGroup(groupName);
         return data;
    }
    public Object[][] displayAllGroups(String groupName)
    {
        Group allGroups = new Group();
        Object[][] data = allGroups.displayAllGroups(groupName);
        return data;
    }
    public void joinGroup(String[] data)
    {
        User joinGroup = new User();
        joinGroup.joinGroup(data);
    }
    public boolean markParticipation(String groupID, String userID)
    {
        Activity markParticipation= new Activity();
        markParticipation.markParticipation(groupID, userID);
        return true;
    }
    public Object[][] getOrganizedActivity()
    {
        Activity organizedActivity = new Activity();
        Object[][] data;
        data = organizedActivity.getOrganizedActivity();
        return data;
    }
}