import java.util.List;

public abstract class MatchingStrategy {

    protected MatchingAlgorithm algorithm;

    public MatchingStrategy(
            MatchingAlgorithm algorithm) {

        this.algorithm = algorithm;
    }

    public void setAlgorithm(
            MatchingAlgorithm algorithm) {

        this.algorithm = algorithm;
    }

    public abstract List<MatchResult> findMatches(
            User target,
            List<User> users
    );
}