package baggins.frodo.pomodoro.logging;

import android.content.Context;
import android.util.Log;

/**
 * Created by Zach Sogolow on 5/24/2015.
 *
 */
public class Logger {

    public String TAG = "";
    public Logger(Context context) {
        TAG = context.getClass().getCanonicalName().toString();
    }

    public void write(String d) {
        Log.d(TAG, d);
    }
}
