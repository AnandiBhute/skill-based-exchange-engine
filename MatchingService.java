import java.util.List;

public class MatchingService {

    public List<MatchResult> find(
            User user,
            MatchingStrategy strategy,
            List<User> users) {

        return strategy.findMatches(
                user,
                users
        );
    }
}