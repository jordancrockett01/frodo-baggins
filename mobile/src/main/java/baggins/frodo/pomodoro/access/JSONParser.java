package baggins.frodo.pomodoro.access;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zach Sogolow on 4/30/2015.
 */
public class JSONParser {
    static OutputStream os = null;
    static InputStream is = null;
    static JSONArray jArray = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {}

    public JSONArray getJSONFromUrl(String url) {

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
        try {
            jArray = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jArray;

    }

    public JSONObject postJSONtoUrl(String url, Object[] params) {

        for (Object o : params) {
            System.out.println(o);
        }

        // Making HTTP request
        try {
            // defaultHttpClient
            URL rUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)rUrl.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            os = conn.getOutputStream();

            List<AbstractMap.SimpleEntry<String, String>> postParams = new ArrayList<>();
//            postParams.add(new AbstractMap.SimpleEntry<String, String>("first_name", "first"));
//            postParams.add(new AbstractMap.SimpleEntry<String, String>("last_name", "last"));
//            postParams.add(new AbstractMap.SimpleEntry<String, String>("email", "email"));

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(getQuery(postParams));
            writer.flush();
            writer.close();
            os.close();
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
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }

    private String getQuery(List<AbstractMap.SimpleEntry<String, String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (AbstractMap.SimpleEntry<String,String> pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}