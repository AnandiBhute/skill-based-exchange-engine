import java.util.ArrayList;
import java.util.List;

public class User {

    int userId;
    String name;
    String email;
    int freePoints;
    boolean available;

    List<String> teachingSkills = new ArrayList<>();
    List<String> learningSkills = new ArrayList<>();
    List<String> interests = new ArrayList<>();

    public User(int userId, String name, String email,
                int freePoints, boolean available) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.freePoints = freePoints;
        this.available = available;
    }

    @Override
    public String toString() {

        return "\nUser ID: " + userId +
                "\nName: " + name +
                "\nEmail: " + email +
                "\nPoints: " + freePoints +
                "\nAvailable: " + available +
                "\nTeaching: " + teachingSkills +
                "\nLearning: " + learningSkills +
                "\nInterests: " + interests;
    }
}