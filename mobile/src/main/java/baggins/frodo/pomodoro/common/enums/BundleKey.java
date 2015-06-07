package baggins.frodo.pomodoro.common.enums;

/**
 * Created by Zach Sogolow on 6/7/2015.
 */
public enum BundleKey {
    ROUNDSLEFT("Rounds Left"),
    CURRENTROUND("Current Round"),
    POMSTATE("Pom State");

    private String key;

    BundleKey(String str) {
        key = str;
    }

    public String getKey() {
        return key;
    }

}
