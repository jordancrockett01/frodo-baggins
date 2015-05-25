package baggins.frodo.pomodoro;

import android.app.Activity;
import android.util.Log;

/**
 * Created by Zach Sogolow on 5/24/2015.
 *
 */
public class Logger {

    public String TAG = "";
    public Logger(Activity activity) {
        TAG = activity.getClass().getName();
    }

    public void write(String d) {
        Log.d(TAG, d);
    }
}
