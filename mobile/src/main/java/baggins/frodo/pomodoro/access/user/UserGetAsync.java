package baggins.frodo.pomodoro.access.user;

import android.os.AsyncTask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import baggins.frodo.pomodoro.access.enums.AccessIndex;
import baggins.frodo.pomodoro.access.enums.AccessMethod;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public class UserGetAsync extends AsyncTask {

    public UserGetAsync() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /**
     * params[0] must be an AccessMethod enum.
     * @param params
     * @return
     */
    @Override
    protected Object doInBackground(Object[] params) {
        AccessMethod accessMethod = (AccessMethod) params[AccessIndex.AccessMethod.getIndex()];
        Object retObject = null;

        switch (accessMethod) {
            case GetAllUsers:
                try {
                    String methodName = (String) params[AccessIndex.MethodString.getIndex()];
                    Method method = UserAccessExecutor.class.getMethod(methodName, String.class);
                    retObject = method.invoke(null, params[AccessIndex.Url.getIndex()]);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case GetUserById:
                try {
                    String methodName = (String) params[AccessIndex.MethodString.getIndex()];
                    Method method = UserAccessExecutor.class.getMethod(methodName, String.class, Object[].class);
                    retObject = method.invoke(null, params[AccessIndex.Url.getIndex()], params[AccessIndex.AddtlParams.getIndex()]);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
        }

        return retObject;
    }
}
