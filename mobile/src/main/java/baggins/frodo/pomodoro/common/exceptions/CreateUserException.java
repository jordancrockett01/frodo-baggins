package baggins.frodo.pomodoro.common.exceptions;

/**
 * Created by Zach Sogolow on 6/7/2015.
 */
public class CreateUserException extends Exception {
    public CreateUserException() {
        super("Error creating user");
    }
}
