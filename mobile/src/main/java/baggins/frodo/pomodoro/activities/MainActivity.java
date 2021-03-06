package baggins.frodo.pomodoro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.TextClock;

import java.util.List;

import baggins.frodo.pomodoro.R;
import baggins.frodo.pomodoro.access.user.UserDBAccess;
import baggins.frodo.pomodoro.logging.Logger;
import baggins.frodo.pomodoro.model.User;

/**
 * Created by Zach Sogolow on 5/24/2015.
 * MainActivity is the entry point into the application
 */
public class MainActivity extends ActionBarActivity {

    @SuppressWarnings("unused")
    Logger log = new Logger(getClass());

    AnalogClock analog = null;
    TextClock textClock = null;


    // Handler and runnable for updating the two clock views
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            analog.postInvalidate();
            textClock.postInvalidate();

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        analog = (AnalogClock) findViewById(R.id.clock);
        textClock = (TextClock) findViewById(R.id.textclock);

        timerRunnable.run();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStartClicked(View view) {
        Intent startIntent = new Intent(this, TimerActivity.class);
        startActivity(startIntent);
    }

    public void onWebApiClicked(View view) {
        UserDBAccess userDBAccess = new UserDBAccess();

        List<User> users = userDBAccess.getAll();
        for (User u : users) {
            log.write(u.toString());
        }

        User user = userDBAccess.getUserById(1);
        log.write(user.toString());
    }
}
