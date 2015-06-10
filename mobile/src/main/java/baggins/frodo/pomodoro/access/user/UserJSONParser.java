package baggins.frodo.pomodoro.access.user;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import baggins.frodo.pomodoro.access.IJSONParser;
import baggins.frodo.pomodoro.logging.Logger;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public class UserJSONParser implements IJSONParser {

    Logger log = new Logger(getClass());

    public UserJSONParser() { }

    @Override
    public JSONArray getJSONFromUrl(String url) {
        InputStream is = null;
        JSONArray jArray = null;
        String json = "";

        log.write("getJSONFromUrl");
        // Making HTTP request
        try {
            // defaultHttpClient
            URL rUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)rUrl.openConnection();
            is = conn.getInputStream();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        // ugly hack
        try {
            jArray = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());

            try {
                JSONObject obj = new JSONObject(json);
                jArray = new JSONArray();
                jArray.put(obj);
            } catch (JSONException e2) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }

        // return JSON array
        return jArray;
    }

    @Override
    public JSONObject postJSONtoUrl(String url, String[] params) {
        log.write("postJSONtoUrl");
        return null;
    }
}
