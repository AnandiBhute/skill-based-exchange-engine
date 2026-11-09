public class InterestMatchAlgorithm implements MatchingAlgorithm {

    @Override
    public int calculateScore(User target, User candidate) {
        int score = 0;

        for (String interest : target.interests) {
            for (String candidateInterest : candidate.interests) {
                if (interest.equalsIgnoreCase(candidateInterest)) {
                    score += 80;
                }
            }
        }

        return score;
    }
}
