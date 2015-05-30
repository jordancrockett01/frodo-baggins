package baggins.frodo.pomodoro.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import baggins.frodo.pomodoro.R;
import baggins.frodo.pomodoro.logging.Logger;
import baggins.frodo.pomodoro.model.Pomodoro;
import baggins.frodo.pomodoro.services.AlarmResponseReceiver;
import baggins.frodo.pomodoro.services.AlarmService;

/**
 * Created by Zach Sogolow on 5/24/2015.
 * TimerActivity started by clicking "Wanna pom?" from the MainActivity.
 */
public class TimerActivity extends Activity {

    Logger log = new Logger(this);

    TextView clock = null;
    TextView roundsLeftView = null;
    TextView currentRoundView = null;
    TextView gameStateView = null;

    private CountDownTimer countDownTimer = null;
    private Pomodoro pomodoro = null;

    public CountDownTimer newTimer(int length) {
        return new CountDownTimer(length, 1000) {

            public void onTick(long millisUntilFinished) {
                clock.setText("Time remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                clock.setText("done!");
                pomodoro.updatePomState();
            }
        };
    }

    public void updateTimer() {
        try {
            countDownTimer = newTimer(pomodoro.getCurrentRoundLength());
        } catch (Pomodoro.PomOverException poe) {
            poe.printStackTrace();
            countDownTimer.cancel();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        StringBuilder messageBuilder = new StringBuilder();
        switch (pomodoro.getPrevPomState()) {
            case NEW:
                messageBuilder.append("New Pomodoro.\nStart?");
                break;
            case WORK:
                String longBreak = "";
                if (pomodoro.currentRound == pomodoro.numberRounds) longBreak = "longer ";
                messageBuilder.append("Work session ");
                messageBuilder.append(pomodoro.currentRound);
                messageBuilder.append(" over.\n");
                messageBuilder.append("Ready for a ");
                messageBuilder.append(longBreak);
                messageBuilder.append("break? ");
                break;
            case SHORTBREAK:
                messageBuilder.append("Short break over.\nEnd of round ");
                messageBuilder.append(pomodoro.currentRound - 1);
                messageBuilder.append(".");
                break;
            case LONGBREAK:
                messageBuilder.append("Ready for another round?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        findViewById(R.id.newpombutton).setVisibility(View.VISIBLE);
                    }
                });
                builder.setPositiveButton("Go!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onNewPomClicked(null);
                        updateTextViews();
                    }
                });
                break;
            default:
                break;
        }

        builder.setMessage(messageBuilder.toString())
                .setTitle("Pomodoro");

        if (pomodoro.getPrevPomState() != Pomodoro.PomState.LONGBREAK) {
            builder.setPositiveButton("Go!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    countDownTimer.start();
                    updateTextViews();
                }
            });
        }
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private AlertDialog.Builder buildDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title);
        return builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log.write("onCreate");

        setContentView(R.layout.activity_timer);

        clock = (TextView) findViewById(R.id.clockview);
        roundsLeftView = (TextView) findViewById(R.id.roundsleft);
        currentRoundView = (TextView) findViewById(R.id.currentround);
        gameStateView = (TextView) findViewById(R.id.gamestate);

        pomodoro = new Pomodoro(this);

        updateTextViews();


        // The filter's action is BROADCAST_ACTION
        IntentFilter mStatusIntentFilter = new IntentFilter(
                AlarmService.Constants.BROADCAST_ACTION);

        AlarmResponseReceiver alarmResponseReceiver = new AlarmResponseReceiver();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(alarmResponseReceiver, mStatusIntentFilter);

        super.onCreate(savedInstanceState);
    }

    private void updateTextViews() {
        String left = roundsLeftView.getText().toString();
        String current = currentRoundView.getText().toString();

        roundsLeftView.setText(left.subSequence(0, left.length() - 1) + "" + (pomodoro.numberRounds - pomodoro.currentRound));
        currentRoundView.setText(current.subSequence(0, current.length() - 1) + "" + pomodoro.currentRound);
        gameStateView.setText(pomodoro.getPomState().getPomString());
    }

    public void onNewPomClicked(View view) {
        pomodoro = pomodoro.newPomodoro();
        findViewById(R.id.newpombutton).setVisibility(View.GONE);
        findViewById(R.id.startpombutton).setVisibility(View.VISIBLE);
        clock.setText("");
        updateTextViews();
    }

    public void onStartPomClicked(View view) {
        pomodoro.start();
        findViewById(R.id.startpombutton).setVisibility(View.GONE);
    }

    /**
     * Its actually ping
     * @param view the ping button
     */
    public void onAlarmButtonClicked(View view) {
        Intent alarmServiceIntent = new Intent(this, AlarmService.class);
        alarmServiceIntent.setData(Uri.parse("Passing app data to the AlarmService"));
        startService(alarmServiceIntent);
    }

    @Override
    protected void onStart() {
        log.write("onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        log.write("onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        log.write("onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        log.write("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        log.write("onPause");
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        log.write("onBackPressed");

        AlertDialog.Builder builder = buildDialog("Do  you want to end the session?", "Pomodoro");
        builder.setPositiveButton("Yes, Leave.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                damnInnerClass();
            }
        });
        builder.setNegativeButton("No, Stay!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Can't call super from an inner class. This is a public method
     * on the activity so that it can reach the parent class of Activity.
     * Not safe. Total hack.
     */
    public void damnInnerClass() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        log.write("onDestroy");
        countDownTimer.cancel();
        super.onDestroy();
    }

}
