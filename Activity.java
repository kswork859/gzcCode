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
    public Object[][] getOrganizedActivity()
    {
        Object[][] data = {
                { "G101", "Group 1", "Area 1", "Admin 1", "Member 1, Member 2" },
                { "G102", "Group 2", "Area 2", "Admin 2", "Member 3, Member 4" },
                { "G103", "Group 3", "Area 3", "Admin 3", "Member 5, Member 6" }
        };
        return data;
    }
}