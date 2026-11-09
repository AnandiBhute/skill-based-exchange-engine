import java.util.List;

public class UserService {

    private UserDAO dao =
            new UserDAO();

    private final UserHistory userHistory = new UserHistory();

    public int register(
            String name,
            String email) {

        return dao.addUser(
                name,
                email
        );
    }

    public void teach(
            int userId,
            String skill) {

        saveProfileBeforeChange(userId);
        dao.addSkill(
                userId,
                skill,
                "TEACH"
        );
    }

    public void learn(
            int userId,
            String skill) {

        saveProfileBeforeChange(userId);
        dao.addSkill(
                userId,
                skill,
                "LEARN"
        );
    }

    public void addInterest(
            int userId,
            String interest) {

        saveProfileBeforeChange(userId);
        dao.addInterest(
                userId,
                interest
        );
    }

    public User get(int id) {
        return dao.getUser(id);
    }

    public List<User> all() {
        return dao.getAllUsers();
    }

    private void saveProfileBeforeChange(int userId) {
        User current = dao.getUser(userId);
        if (current != null) {
            userHistory.save(current);
        }
    }

    /**
     * Undo the most recent skill/interest change for the selected user.
     * Returns false if there is no snapshot or the database restore fails.
     */
    public boolean undoProfileChange(int userId) {
        UserMemento snapshot = userHistory.peek(userId);

        if (snapshot == null) {
            return false;
        }

        if (dao.restoreProfile(snapshot)) {
            userHistory.removeLatest(userId);
            return true;
        }

        return false;
    }
}