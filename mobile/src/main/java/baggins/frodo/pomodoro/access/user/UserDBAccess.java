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

    private Logger log = new Logger(getClass());

    public UserDBAccess() {
    }

    @Override

    public List<User> getAll() {
        List<User> users = null;

        AccessMethod am = AccessMethod.GetAllUsers;
        AsyncTask userGetAsync = new UserGetAsync().execute(am.getExecutionParams(am, null));

       try {
           users = (List<User>) userGetAsync.get();
       } catch (InterruptedException | ExecutionException ee) {
           ee.printStackTrace();
       }
        return users;
    }

    @Override
    public User getUserById(int id) {
        User user = null;

        AccessMethod am = AccessMethod.GetUserById;
        AsyncTask userGetByIdAsync = new UserGetAsync().execute(am.getExecutionParams(am, new Object[] { id }));

        try {
            user = (User) userGetByIdAsync.get();
        } catch (InterruptedException | ExecutionException ee) {
            ee.printStackTrace();
        }
        return user;
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
