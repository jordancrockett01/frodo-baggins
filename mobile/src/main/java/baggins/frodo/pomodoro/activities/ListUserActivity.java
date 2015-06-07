package baggins.frodo.pomodoro.activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import baggins.frodo.pomodoro.R;
import baggins.frodo.pomodoro.access.JSONParser;
import baggins.frodo.pomodoro.logging.Logger;

/**
 * Created by Zach Sogolow on 6/4/2015.
 */
public class ListUserActivity extends ListActivity {

    Logger log = new Logger(this);

    ArrayList<HashMap<String, ?>> userList;
    private ProgressDialog progressMessage;
    JSONParser jParser = new JSONParser();
    private static String url = "http://commit/PomWebApi/api/Users"; //TODO
    JSONArray users = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user_activity);
        userList = new ArrayList<>();
        new LoadAllProducts().execute();
    }

    class LoadAllProducts extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressMessage = new ProgressDialog(ListUserActivity.this);
            progressMessage.setMessage("Loading ...");
            progressMessage.setIndeterminate(false);
            progressMessage.setCancelable(false);
            progressMessage.show();
        }

        @Override
        protected String doInBackground(Object[] params) {
            JSONArray json = jParser.getJSONFromUrl(url);
            if (json==null) return null;

            try {
                log.write(json.toString());
                log.write(json.length()+"");
                for (int i = 0; i < json.length(); i++) {
                    log.write(json.get(i).toString());
                }
                json.get(0); // to throw exception for try block
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object file_url) {
            progressMessage.dismiss();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    ListAdapter adapter = new SimpleAdapter(
//                            ListUserActivity.this, userList,
//                            R.layout.display_user_layout, new String[] { "id",
//                            "first_name","last_name"},
//                            new int[] { R.id.id, R.id.first_name,R.id.last_name });
//                    setListAdapter(adapter);
//                }
//            });

        }

    }
}

