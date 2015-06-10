package baggins.frodo.pomodoro.deprecated;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import baggins.frodo.pomodoro.common.enums.WebRequest;
import baggins.frodo.pomodoro.common.exceptions.CreateUserException;
import baggins.frodo.pomodoro.logging.Logger;
import baggins.frodo.pomodoro.model.Task;
import baggins.frodo.pomodoro.model.User;

/**
 * DbAccess.java
 * Created by Zach Sogolow on 6/7/2015.
 */
public class DBAccess {

    Logger log = new Logger(getClass());
    JSONParser jParser = new JSONParser();

    public DBAccess() {
    }

    // Create User                  POST
    // Log In                       POST/GET
    // Create new task              POST
    // Retrieve list of Tasks       GET
    // Remove Task                  POST

    public boolean getAllUsers() {
        final String url = "http://10.0.0.248/PomWebApi/api/Users";
        Object[] params = new Object[2];
        params[0] = WebRequest.GET.toString();
        params[1] = url;
        new AccessAsync().execute(params);

        return true;
    }

    public boolean createUser(User user) throws CreateUserException{
        final String url = "http://10.0.0.248/PomWebApi/api/UserNames/PostUserName";
//        final String url = "http://localhost:4359/api/UserNames/PostUserName?";

        Object[] params = new Object[3];
        params[0] = WebRequest.POST.toString();
        params[1] = url;
        params[2] = new String[] { user.getUserName() };
        new AccessAsync().execute(params);

        return false;
    }

    public static User getUser(String username) {


        return null;
    }

    public static boolean login(String username, String password) {


        return false;
    }

    public static boolean createTask(Task task) {


        return false;
    }

    public static boolean removeTask(Task task) {


        return false;
    }

    public static boolean updateTask(Task task) {


        return false;
    }


    class AccessAsync extends AsyncTask {

        @Override
        protected void onPreExecute() {
            log.write("onPreExecute");
            super.onPreExecute();
        }

        /**
         * MUST PASS WebRequest enum as argument 0, the rest will depend on type:
         *
         * POST:    [0] = WebRequest.POST
         *          [1] =  URL to post to
         *          [2] = STRING[]  data
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(Object[] params) {
            log.write("doInBackground, " + params[0]);

            if (params[0] == null) {
                cancel(true);
                return "Cancelled";
            }

            WebRequest request = WebRequest.parse((String) params[0]);

            switch (request) {
                case POST:
                    handlePost(params);
                    break;

                case GET:
                    handleGet(params);
                    break;

                case INVALID:
                    cancel(true);
                    return "Invalid Request";

                default:
                    break;
            }
            return null;
        }

        protected void handlePost(Object[] params) {
            String[] postParams = (String[]) ((params[2] == null) ? new String[] {} : params[2]);
            String url = (params[1] == null) ? "null" : params[1].toString();
            if (url.equals("null")) return;

            JSONObject jsonObject = jParser.postJSONtoUrl(url, postParams);
            if (jsonObject == null) return;
            try {
                log.write(jsonObject.toString());
                log.write(jsonObject.length() + "");
                log.write(jsonObject.get("").toString());// to throw exception
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        protected void handleGet(Object[] params) {
            String url = (params[1] == null) ? "null" : params[1].toString();
            if (url.equals("null")) return;

            JSONArray json = jParser.getJSONFromUrl(url);
            if (json==null) return;

            try {
                log.write(json.length() + "");
                for (int i = 0; i < json.length(); i++) {
                    log.write(json.get(i).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Object file_url) {
            log.write("onPostExecute");
        }

        @Override
        protected void onCancelled(Object o) {
            log.write("onCancelled");
            super.onCancelled(o);
        }
    }
}
