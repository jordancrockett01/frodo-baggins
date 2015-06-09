package baggins.frodo.pomodoro.access.enums;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public enum AccessIndex {

    AccessMethod(0),
    MethodString(1),
    Url(2);

    private int index;
    AccessIndex(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }
}
