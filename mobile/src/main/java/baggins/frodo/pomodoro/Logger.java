package baggins.frodo.pomodoro;

import android.content.Context;
import android.util.Log;

/**
 * Created by Zach Sogolow on 5/24/2015.
 *
 */
public class Logger {

    public String TAG = "";
    public Logger(Context context) {
        TAG = context.getClass().getName();
    }

    public void write(String d) {
        Log.d(TAG, d);
    }
}
