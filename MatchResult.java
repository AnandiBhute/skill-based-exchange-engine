public class MatchResult {

    User user;
    int score;

    public MatchResult(User user, int score) {
        this.user = user;
        this.score = score;
    }

    @Override
    public String toString() {

        return user.name +
                " -> Match Score: " +
                score;
    }
}