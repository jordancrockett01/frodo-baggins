package baggins.frodo.pomodoro.common.exceptions;

/**
 * Created by Zach Sogolow on 6/7/2015.
 */
public class PomOverException extends Exception {
    public PomOverException() {
        super("The current pom state is OVER");
    }
}
