package baggins.frodo.pomodoro;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Zach Sogolow on 5/24/2015.
 */
public class AlarmResponseReciever extends BroadcastReceiver {

    Activity activity;
    public AlarmResponseReciever(Activity activity) {
        this.activity = activity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(AlarmService.Constants.EXTENDED_DATA_STATUS);
        Logger log = new Logger(context);
        log.write(data);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(activity)
                        .setSmallIcon(R.drawable.common_signin_btn_icon_dark)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        Intent resultIntent = new Intent(activity, TimerActivity.class);

        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        activity,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_NO_CREATE
                );

        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
