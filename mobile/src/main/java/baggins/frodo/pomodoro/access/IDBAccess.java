package baggins.frodo.pomodoro.access;

import java.util.List;

/**
 * Created by Zach Sogolow on 6/8/2015.
 */
public interface IDBAccess<K> {
    List<K> getAll();
    K getUserById(int id);
    boolean remove(K entity);
    boolean update(K entity);
}
