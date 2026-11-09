public class ExactMatchAlgorithm
        implements MatchingAlgorithm {

    public int calculateScore(
            User target,
            User candidate) {

        int score = 0;

        for (String skill :
                target.learningSkills) {

            if (candidate.teachingSkills
                    .contains(skill)) {

                score += 100;
            }
        }

        for (String skill :
                target.teachingSkills) {

            if (candidate.learningSkills
                    .contains(skill)) {

                score += 100;
            }
        }

        return score;
    }
}