import java.util.ArrayList;
import java.util.List;

public class SkillBasedMatching
        extends MatchingStrategy {

    public SkillBasedMatching(
            MatchingAlgorithm algorithm) {

        super(algorithm);
    }

    public List<MatchResult> findMatches(
            User target,
            List<User> users) {

        List<MatchResult> results =
                new ArrayList<>();

        for (User user : users) {

            if (user.userId == target.userId)
                continue;

            int score =
                    algorithm.calculateScore(
                            target,
                            user
                    );

            if (score > 0) {

                results.add(
                        new MatchResult(
                                user,
                                score
                        )
                );
            }
        }

        return results;
    }
}