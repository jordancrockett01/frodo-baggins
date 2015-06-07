package baggins.frodo.pomodoro.common.enums;

/**
 * Created by Zach Sogolow on 6/7/2015.
 */
public enum WebRequest {

    GET("GET"),
    POST("POST"),
    INVALID("INVALID");

    private String request;

    WebRequest(String req) {
        this.request = req;
    }

    public String toString() {
        return request;
    }

    public static WebRequest parse(String str) {
        for (WebRequest r : values()) {
            if (r.request.equals(str)){
                return r;
            }
        }
        return INVALID;
    }
}
