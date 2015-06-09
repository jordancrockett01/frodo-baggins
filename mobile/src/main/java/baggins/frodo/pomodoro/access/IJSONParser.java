package baggins.frodo.pomodoro.access;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public interface IJSONParser {

    JSONArray getJSONFromUrl(String url);
    JSONObject postJSONtoUrl(String url, String[] params);

}
