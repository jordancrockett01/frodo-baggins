package baggins.frodo.pomodoro.model;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import baggins.frodo.pomodoro.activities.TimerActivity;

/**
 * Created by Zach Sogolow on 5/24/2015.
 */
public class Pomodoro {

    public enum PomState { NEW("New Pomodoro"),
        WORK("Work"),
        SHORTBREAK("Short Break"),
        LONGBREAK("Long Break"),
        OVER("Pom Over"),
        UNKNOWN("Unknown");

        private String pomString;

        PomState(String str) {
            pomString = str;
        }

        public String getPomString() {
            return pomString;
        }

        public static PomState parse(String str) {
            for (PomState p : values()) {
                if (p.getPomString().equals(str)) {
                    return p;
                }
            }
            return UNKNOWN;
        }

    }

    public int sessionLength = 10000;
    public int shortBreakLength = 5000;
    public int longBreakLength = 20000;

    private PomState pomState = PomState.NEW;
    private PomState prevPomState = PomState.NEW;
    public int numberRounds = 2;
    public int currentRound = 1;
    private TimerActivity timerActivity = null;

    public Pomodoro(TimerActivity activity) {
        this.timerActivity = activity;
    }

    public Pomodoro newPomodoro() {
        currentRound = 1;
        pomState = PomState.NEW;
        prevPomState = PomState.NEW;
        return new Pomodoro(timerActivity);
    }

    public void start() {
        updatePomState();
    }


    // Only called on new game and timer.finish()
    public void updatePomState() {
        switch (pomState) {
            case NEW:       // new pomodoro, start working
                pomState = PomState.WORK;
                break;
            case WORK:      // done working, check the round
                if (currentRound < numberRounds) {
                    pomState = PomState.SHORTBREAK;
                } else {
                    pomState = PomState.LONGBREAK; // last round of work, long break
                }
                prevPomState = PomState.WORK;
                break;
            case SHORTBREAK:    // short breaks over, start working, increment round
                pomState = PomState.WORK;
                prevPomState = PomState.SHORTBREAK;
                currentRound++;
                break;
            case LONGBREAK:     // long break is over, end the pom
                pomState = PomState.OVER;
                prevPomState = PomState.LONGBREAK;
                break;
            default:
                break;
        }

        // update the timer
        timerActivity.updateTimer();
    }

    public void restoreState(Bundle savedInstanceState) {
        int round = savedInstanceState.getInt(TimerActivity.BundleKey.CURRENTROUND.getKey());
        int roundsLeft = savedInstanceState.getInt(TimerActivity.BundleKey.ROUNDSLEFT.getKey());
        PomState state = PomState.parse(savedInstanceState.getString(TimerActivity.BundleKey.POMSTATE.getKey()));

        currentRound = round;
        numberRounds = roundsLeft + round;
        pomState = state;
    }

    public Map<TimerActivity.BundleKey, Object> getStateMap() {
        Map<TimerActivity.BundleKey, Object> bundleKeyObjectMap = new HashMap<>();
        bundleKeyObjectMap.put(TimerActivity.BundleKey.CURRENTROUND, currentRound);
        bundleKeyObjectMap.put(TimerActivity.BundleKey.ROUNDSLEFT, numberRounds-currentRound);
        bundleKeyObjectMap.put(TimerActivity.BundleKey.POMSTATE, pomState);
        return bundleKeyObjectMap;
    }

    public PomState getPomState() {
        return pomState;
    }

    public PomState getPrevPomState() {
        return prevPomState;
    }

    public int getCurrentRoundLength() throws PomOverException{
        switch (pomState) {
            case WORK:
                return sessionLength;
            case SHORTBREAK:
                return shortBreakLength;
            case LONGBREAK:
                return longBreakLength;
            case OVER:
                throw new PomOverException();
            default:
                return 0;
        }
    }

    public class PomOverException extends Exception {
         public PomOverException() {
            super("The current pom state is OVER");
        }
    }
}
