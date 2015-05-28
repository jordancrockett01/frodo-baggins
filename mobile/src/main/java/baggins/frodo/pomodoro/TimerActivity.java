package baggins.frodo.pomodoro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Zach Sogolow on 5/24/2015.
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

    public void updateTimer(Pomodoro.PomState state) {
        if (state == Pomodoro.PomState.SHORTBREAK) {
            countDownTimer = newTimer(pomodoro.shortBreakLength);
        } else if (state == Pomodoro.PomState.LONGBREAK) {
            countDownTimer = newTimer(pomodoro.longBreakLength);
        } else if (state == Pomodoro.PomState.WORK) {
            countDownTimer = newTimer(pomodoro.sessionLength);
        } else { // game over
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
                messageBuilder.append("Work session " + (pomodoro.currentRound) + " over.\n" +
                        "Ready for a " + longBreak + "break? ");
                break;
            case SHORTBREAK:
                messageBuilder.append("Short break over.\nEnd of round " + (pomodoro.currentRound - 1) + ".");
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

    public Activity getActivity() {
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log.write("onCreate");

        if (savedInstanceState == null ) {
            setContentView(R.layout.activity_timer);

            clock = (TextView) findViewById(R.id.clockview);
            roundsLeftView = (TextView) findViewById(R.id.roundsleft);
            currentRoundView = (TextView) findViewById(R.id.currentround);
            gameStateView = (TextView) findViewById(R.id.gamestate);

            pomodoro = new Pomodoro(this);

            updateTextViews();

            super.onCreate(savedInstanceState);
        }
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

    @Override
    protected void onStart() {
        log.write("onStart");
        if (pomodoro != null ) {

        }
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
