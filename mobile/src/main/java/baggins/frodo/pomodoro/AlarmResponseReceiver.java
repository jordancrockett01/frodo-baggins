package baggins.frodo.pomodoro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Zach Sogolow on 5/27/2015.
 */
public class AlarmResponseReceiver extends BroadcastReceiver {

    // Prevents instantiation
    public AlarmResponseReceiver() {
    }
    // Called when the BroadcastReceiver gets an Intent it's registered to receive

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * Handle Intents here.
         */
        Logger log = new Logger(context);
        log.write(intent.getStringExtra(AlarmService.Constants.EXTENDED_DATA_STATUS));
    }

}
