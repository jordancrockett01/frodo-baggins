package baggins.frodo.pomodoro.access.user;

import org.json.JSONArray;
import org.json.JSONObject;

import baggins.frodo.pomodoro.access.IJSONParser;
import baggins.frodo.pomodoro.logging.Logger;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public class UserJSONParser implements IJSONParser {

    Logger log = new Logger(this);

    public UserJSONParser() { }

    @Override
    public JSONArray getJSONFromUrl(String url) {
        log.write("getJSONFromUrl");
        return null;
    }

    @Override
    public JSONObject postJSONtoUrl(String url, String[] params) {
        log.write("postJSONtoUrl");
        return null;
    }
}
