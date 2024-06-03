package src;
public class Group {
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
    public Object[][] searchGroup(String groupName)
    {
        Object[][] data = {
                { "G104", "Group 1", "Area 1", "Admin 1", "Member 1, Member 2" },
                { "G102", "Group 2", "Area 2", "Admin 2", "Member 3, Member 4" },
                { "G103", "Group 3", "Area 3", "Admin 3", "Member 5, Member 6" }
        };
        return data;
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

