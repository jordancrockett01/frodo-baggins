package baggins.frodo.pomodoro.access.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import baggins.frodo.pomodoro.logging.Logger;
import baggins.frodo.pomodoro.model.User;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public class UserAccessExecutor {

    static Logger log = new Logger(UserAccessExecutor.class);

    private static User parseUser(JSONObject obj) throws JSONException {
        int id = obj.getInt("UserID");                 // make enum or final fields
        String firstName = obj.getString("FirstName");
        String lastName = obj.getString("LastName");
        String userName = obj.getJSONObject("UserName").getString("Name");
        return new User(id, firstName, lastName, userName);
    }

    public static List<User> getAllUsers(String url) {

        UserJSONParser jsonParser = new UserJSONParser();

        JSONArray jsonArray = jsonParser.getJSONFromUrl(url);

        if (jsonArray==null) return new ArrayList<User>();

        try {
            System.out.println(jsonArray.length() + "");
            for (int i = 0; i < jsonArray.length(); i++) {
                System.out.println(jsonArray.get(i).toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    public static User getUserById(String url, Object[] params) {
        UserJSONParser jsonParser = new UserJSONParser();
        JSONArray jsonArray = jsonParser.getJSONFromUrl(url + params[0]);
        User user = null;

        if (jsonArray==null) return null;

        try {
            log.write(jsonArray.length() + "");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                user = parseUser(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
