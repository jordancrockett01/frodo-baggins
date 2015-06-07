package baggins.frodo.pomodoro.common.enums;

/**
 * Created by Zach Sogolow on 6/7/2015.
 */
public enum ServiceTag {
    START("START"),
    STOP("STOP"),
    OVER("OVER"),
    TIME("TIME"),
    TIMERFINISHED("TIMERFINSISHED"),
    CANCELLED("CANCELLED"),
    UNKNOWN("UNKNOWN");

    private String serviceTag;

    ServiceTag(String str) {
        serviceTag = str;
    }

    public String toString() {
        return serviceTag;
    }
    public static ServiceTag parse(String str) {
        for (ServiceTag tag : values()) {
            if (tag.toString().equals(str)) {
                return tag;
            }
        }
        return UNKNOWN;
    }

}