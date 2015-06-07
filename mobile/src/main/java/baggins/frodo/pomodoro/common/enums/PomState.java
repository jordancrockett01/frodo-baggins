package baggins.frodo.pomodoro.common.enums;

/**
 * Created by Zach Sogolow on 6/7/2015.
 */
public enum PomState { NEW("New Pomodoro"),
    WORK("Work"),
    SHORTBREAK("Short Break"),
    LONGBREAK("Long Break"),
    OVER("Pom Over"),
    UNKNOWN("Unknown");

    private String pomString;

    PomState(String str) {
        pomString = str;
    }

    public String getPomString() {
        return pomString;
    }

    public static PomState parse(String str) {
        for (PomState p : values()) {
            if (p.getPomString().equals(str)) {
                return p;
            }
        }
        return UNKNOWN;
    }

}