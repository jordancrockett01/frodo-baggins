package baggins.frodo.pomodoro.access.enums;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public enum AccessMethod {

    GetAllUsers("getAllUsers", "http://10.0.0.248/PomWebApi/api/Users");

    private String method;
    private String url;

    AccessMethod(String meth, String url) {
        this.method = meth;
        this.url = url;
    }

    public Object[] getExecutionParams(AccessMethod accessMethod) {
        return new Object[] { accessMethod, method, url };
    }
}
