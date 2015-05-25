package baggins.frodo.pomodoro;

/**
 * Created by Zach Sogolow on 5/24/2015.
 */
public class Pomodoro {

    enum PomState { NEW("New Pomodoro"),
        WORK("Work"),
        SHORTBREAK("Short Break"),
        LONGBREAK("Long Break"),
        OVER("Pom Over");

        private String pomString;

        PomState(String str) {
            pomString = str;
        }

        public String getPomString() {
            return pomString;
        }

    }

    public int sessionLength = 15000;
    public int shortBreakLength = 5000;
    public int longBreakLength = 80000;

    private PomState pomState = PomState.NEW;
    private PomState prevPomState = PomState.NEW;
    public int numberRounds = 3;
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

    public void updatePomState() {
        switch (pomState) {
            case NEW:
                pomState = PomState.WORK;
                break;
            case WORK:
                if (currentRound < numberRounds) {
                    pomState = PomState.SHORTBREAK;
                } else {
                    pomState = PomState.LONGBREAK;
                }
                prevPomState = PomState.WORK;
                break;
            case SHORTBREAK:
                if (currentRound < numberRounds) {
                    pomState = PomState.WORK;
                } else {
                    pomState = PomState.WORK;
                }
                prevPomState = PomState.SHORTBREAK;
                currentRound++;
                break;
            case LONGBREAK:
                pomState = PomState.OVER;
                prevPomState = PomState.LONGBREAK;
                break;
            default:
                break;
        }

        timerActivity.updateTimer(pomState);
    }

    public PomState getPomState() {
        return pomState;
    }

    public PomState getPrevPomState() {
        return prevPomState;
    }
}
