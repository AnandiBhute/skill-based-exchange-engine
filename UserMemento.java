
import java.util.ArrayList;
import java.util.List;

/**
 * Memento: stores a snapshot of a user's profile.
 */
public final class UserMemento {

    private final int userId;
    private final List<String> teachingSkills;
    private final List<String> learningSkills;
    private final List<String> interests;

    public UserMemento(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        this.userId = user.userId;
        this.teachingSkills = new ArrayList<>(user.teachingSkills);
        this.learningSkills = new ArrayList<>(user.learningSkills);
        this.interests = new ArrayList<>(user.interests);
    }

    public int getUserId() {
        return userId;
    }

    public List<String> getTeachingSkills() {
        return new ArrayList<>(teachingSkills);
    }

    public List<String> getLearningSkills() {
        return new ArrayList<>(learningSkills);
    }

    public List<String> getInterests() {
        return new ArrayList<>(interests);
    }
}
