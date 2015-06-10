package baggins.frodo.pomodoro.model;

/**
 * Created by Zach Sogolow on 6/7/2015.
 */
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String userName;

    public User(int id, String first, String last, String user) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.userName = user;
    }

    public String getUserName() { return userName; }


    public String toString() { return "User : " + userName; }
}
