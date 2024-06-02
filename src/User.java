package src;
public class User {
    private String userName;
    private String userID;
    private String userEmail;
    private String userPhone;
    private String userPassword;
    private String userAddress;

    // Getter for userID
    public String getUserID() {
        return userID;
    }

    // joinGroup method

  public void joinGroup(String[] info) {
        System.out.println("This is Joined Group Function");
    }

    public boolean loginToAcc(String[] data) {
        userID = data[0];
        userPassword = data[1];
        String dbUserID = "Khurram";
        String dbPassword = "12345";
        if (userID.equals(dbUserID) && userPassword.equals(dbPassword)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean registerUser(String[] data)
    {
        userID = data[0];
        userName = data[1];
        userEmail = data[2];
        userPassword = data[3];
        userAddress = data[4];
        userPhone = data[5];

        System.out.println("data has arrived in user.Register function");
        return true;
    }
}

// public static void main(String[] args) {
// // Create a user
// User user = new User("John", 123, "john@example.com", "1234567890",
// "password");

// // Launch the GUI for joining group
// new JoinGroupUI(user).setVisible(true);
// }
// }

/*
 * public void userData(String userName, String userEmail, String userPassword,
 * String userAddress, int userPhone ){
 * 
 * }
 * public void login(int userID, String userPassword){
 * 
 * }
 * public void logout(){
 * 
 * }
 * public void register(){
 * 
 * }
 * public void markparticipation(){
 * 
 * }
 * public void uploadMultimedia(String mediaLink, String caption){
 * 
 * }
 * public void organizedActivity(int groupID, String activityName, String
 * areaAddress ){
 * 
 * }
 * public void selectGroup(){
 * 
 * }
 */
