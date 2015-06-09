package baggins.frodo.pomodoro.access.user;

import android.os.AsyncTask;

import baggins.frodo.pomodoro.logging.Logger;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public class UserPostAsync extends AsyncTask {

    Logger log = new Logger(this);

    public UserPostAsync(){}

    @Override
    protected void onPreExecute() {
        log.write("onPreExecute");
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        log.write("onPostExecute");
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        log.write("onProgressUpdate");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        log.write("onCancelled(Object o)");
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        log.write("onCancelled");
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        log.write("doInBackground");
        return null;
    }
}
