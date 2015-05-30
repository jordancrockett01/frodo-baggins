package baggins.frodo.pomodoro.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import baggins.frodo.pomodoro.logging.Logger;

/**
 * Created by Zach Sogolow on 5/27/2015.
 */
public class AlarmService extends IntentService {

    Logger log = new Logger(this);

    public final class Constants {

        // Defines a custom Intent action
        public static final String BROADCAST_ACTION =
                "baggins.frodo.pomodoro.BROADCAST";

        // Defines the key for the status "extra" in an Intent
        public static final String EXTENDED_DATA_STATUS =
                "baggins.frodo.pomodoro.STATUS";
    }


    public AlarmService() {
        super("Boom");
    }
    public AlarmService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();

        // Do work here, based on the contents of dataString
        String handled = doWorkHere(dataString);

        /*
         * Creates a new Intent containing a Uri object
         * BROADCAST_ACTION is a custom Intent action
         */
        Intent localIntent =
                new Intent(Constants.BROADCAST_ACTION)
                        // Puts the status into the Intent
                        .putExtra(Constants.EXTENDED_DATA_STATUS, handled);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

    }

    private String doWorkHere(String string) {
        log.write(string);
        return "booyah";
    }
}
