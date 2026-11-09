public interface MatchingAlgorithm {

    int calculateScore(
            User target,
            User candidate
    );
}