package baggins.frodo.pomodoro.access.user;

import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import baggins.frodo.pomodoro.access.IDBAccess;
import baggins.frodo.pomodoro.access.enums.AccessMethod;
import baggins.frodo.pomodoro.logging.Logger;
import baggins.frodo.pomodoro.model.User;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public class UserDBAccess implements IDBAccess<User> {

    private Logger log = null;

    public UserDBAccess() {
        log = new Logger(this);
    }

    @Override

    public List<User> getAll() {
        List<User> users = null;

        AccessMethod am = AccessMethod.GetAllUsers;
        AsyncTask userGetAsync = new UserGetAsync().execute(am.getExecutionParams(am));

       try {
           users = (List<User>) userGetAsync.get();
       } catch (InterruptedException | ExecutionException ee) {
           ee.printStackTrace();
       }
        return users;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public boolean remove(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }
}
