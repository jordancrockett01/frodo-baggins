package baggins.frodo.pomodoro.logging;

import android.util.Log;

/**
 * Created by Zach Sogolow on 5/24/2015.
 *
 */
public class Logger {

    public String TAG = "";
    public Logger(Class context) {
        TAG = context.getName();
    }

    public void write(String d) {
        Log.d(TAG, d);
    }
}
