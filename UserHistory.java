
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Caretaker: maintains undo history separately for each user.
 */
public class UserHistory {

    private final Map<Integer, Deque<UserMemento>> history =
            new HashMap<>();

    public void save(User user) {
        if (user == null) {
            return;
        }

        history.computeIfAbsent(
                user.userId, id -> new ArrayDeque<>()
        ).push(new UserMemento(user));
    }

    // View the latest snapshot without removing it.
    public UserMemento peek(int userId) {
        Deque<UserMemento> snapshots = history.get(userId);

        if (snapshots == null || snapshots.isEmpty()) {
            return null;
        }

        return snapshots.peek();
    }

    // Remove the snapshot only after a successful restore.
    public void removeLatest(int userId) {
        Deque<UserMemento> snapshots = history.get(userId);

        if (snapshots != null && !snapshots.isEmpty()) {
            snapshots.pop();

            if (snapshots.isEmpty()) {
                history.remove(userId);
            }
        }
    }

    public boolean hasHistory(int userId) {
        Deque<UserMemento> snapshots = history.get(userId);
        return snapshots != null && !snapshots.isEmpty();
    }
}
