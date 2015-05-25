package baggins.frodo.pomodoro;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Zach Sogolow on 5/24/2015.
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

    Intent localIntent =
            new Intent(Constants.BROADCAST_ACTION)
                    // Puts the status into the Intent
                    .putExtra(Constants.EXTENDED_DATA_STATUS, "Status");

    MyRunnable timerRunnable;

    public AlarmService() {
        this("AlarmService");
    }

    public AlarmService(String name) {
        super(name);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    @Override
    public void onCreate() {
        timerRunnable = new MyRunnable();
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String dataString = intent.getDataString();
        log.write(dataString);
        int length = Integer.parseInt(dataString);
        timerRunnable.setRunTime(length);
        timerRunnable.setStartTime(System.currentTimeMillis());
        timerRunnable.run();
    }


    class MyRunnable implements Runnable {
        Handler timerHandler = new Handler();
        private long startTime = System.currentTimeMillis();
        private long runTime;

        public void setRunTime(long time) {
            this.runTime = time;
        }

        public void setStartTime(long time) {
            this.startTime = time;
        }

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if (System.currentTimeMillis() >= startTime + runTime) {
                timerHandler.removeCallbacks(timerRunnable);
                LocalBroadcastManager.getInstance(getAlarmService()).sendBroadcast(localIntent);


                return;
            }

            timerHandler.postDelayed(this, 500);
        }

    }

    ;

    public Context getAlarmService() {
        return this;
    }
}