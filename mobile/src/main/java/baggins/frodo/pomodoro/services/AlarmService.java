package baggins.frodo.pomodoro.services;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import baggins.frodo.pomodoro.common.enums.ServiceTag;
import baggins.frodo.pomodoro.logging.Logger;

/**
 * Created by Zach Sogolow on 5/27/2015.
 */
public class AlarmService extends Service {

    Logger log = new Logger(this);

    boolean running = false;

    AsyncTask runAsyncTask = null;

    public final class Constants {

        // Defines a custom Intent action
        public static final String BROADCAST_ACTION =
                "baggins.frodo.pomodoro.BROADCAST";

        // Defines the key for the status "extra" in an Intent
        public static final String EXTENDED_DATA_STATUS =
                "baggins.frodo.pomodoro.STATUS";
    }

    @Override
    public void onCreate() {
        log.write("onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log.write("onStartCommand");
        log.write("Running: " + running);

        String dataString = intent.getDataString();
        log.write(dataString);
        ServiceTag sTag = ServiceTag.parse(dataString);
        switch (sTag) {
            case START:
                if (!running) {
                    int time = intent.getIntExtra(ServiceTag.TIME.toString(), 0);
                    runAsyncTask = new RunAsyncTask().execute(time);
                    running = true;
                }
                break;
            case STOP:
                log.write("Stopping self");
                if (runAsyncTask != null) {
                    log.write("Async is not null");
                    runAsyncTask.cancel(true);
                }
                stopSelf();
                break;

            case OVER:
                log.write("Stopping self");
                stopSelf();
                break;
            case UNKNOWN:
                log.write(sTag.toString());
                break;
            default:
                log.write("Default case Switching on dataString: " + dataString);
                break;
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        log.write("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        log.write("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        log.write("onRebind");
        super.onRebind(intent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        log.write("dump");
        super.dump(fd, writer, args);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        log.write("onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onLowMemory() {
        log.write("onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        log.write("onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public void onDestroy() {
        log.write("onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        log.write("onBind");
        return null;
    }

    private class RunAsyncTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            log.write("onPreExecute");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            log.write("onPostExecute");
            running = false;
            stopSelf();

            Intent localIntent =
                    new Intent(Constants.BROADCAST_ACTION)
                            // Puts the status into the Intent
                            .putExtra(Constants.EXTENDED_DATA_STATUS, ServiceTag.TIMERFINISHED.toString());
            // Broadcasts the Intent to receivers in this app.
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(localIntent);

            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            log.write("doInBackground");
            int time = (int) params[0];
            long currentTime = System.currentTimeMillis();

            while (System.currentTimeMillis() < currentTime + time) {
                if (isCancelled()) {
                    return null;
                }
            }
            return null;
        }

        @Override
        protected void onCancelled(Object o) {
            log.write("onCancelled");
            Intent localIntent =
                    new Intent(Constants.BROADCAST_ACTION)
                            // Puts the status into the Intent
                            .putExtra(Constants.EXTENDED_DATA_STATUS, ServiceTag.CANCELLED.toString());
            // Broadcasts the Intent to receivers in this app.
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(localIntent);
            super.onCancelled(o);
        }
    }
}
