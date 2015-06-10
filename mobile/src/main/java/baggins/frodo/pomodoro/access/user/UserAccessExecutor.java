package baggins.frodo.pomodoro.access.user;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import baggins.frodo.pomodoro.deprecated.JSONParser;
import baggins.frodo.pomodoro.model.User;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public class UserAccessExecutor {

    public static List<User> getAllUsers(String url) {

        JSONParser jsonParser = new JSONParser();

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
        JSONParser jsonParser = new JSONParser();

        JSONArray jsonArray = jsonParser.getJSONFromUrl(url + params[0]);

        if (jsonArray==null) return null;

        try {
            System.out.println(jsonArray.length() + "");
            for (int i = 0; i < jsonArray.length(); i++) {
                System.out.println(jsonArray.get(i).toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
