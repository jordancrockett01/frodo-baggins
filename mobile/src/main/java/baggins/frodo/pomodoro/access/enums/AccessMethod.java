package baggins.frodo.pomodoro.access.enums;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public enum AccessMethod {

    GetAllUsers("getAllUsers", "http://10.0.0.248/PomWebApi/api/Users/"),
    GetUserById("getUserById", "http://10.0.0.248/PomWebApi/api/Users/");

    private String method;
    private String url;

    AccessMethod(String method, String url) {
        this.method = method;
        this.url = url;
    }

    /**
     * This will produce the array of Objects sent to the execute method of
     * AsyncTask. params[0] = AccessMethod
     *            params[1] = method string to call
     *            params[2] = url to get/post to
     * (optional) params[3] = Object[] params needed for the get/post.
     * If there are no additional params, pass it null.
     * @param accessMethod
     * @param additionalParams
     * @return
     */
    public Object[] getExecutionParams(AccessMethod accessMethod, Object[] additionalParams) {
        Object[] params = (additionalParams == null) ? new Object[] {accessMethod, method, url}
                : new Object[] { accessMethod, method, url, additionalParams };
        return params;
    }
}
