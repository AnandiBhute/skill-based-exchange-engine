public class SimilarityMatchAlgorithm
        implements MatchingAlgorithm {

    public int calculateScore(
            User target,
            User candidate) {

        int score = 0;

        for (String skill :
                target.learningSkills) {

            for (String candidateSkill :
                    candidate.teachingSkills) {

                if (skill.equalsIgnoreCase(
                        candidateSkill)) {

                    score += 80;
                }
            }
        }

        for (String interest :
                target.interests) {

            if (candidate.interests
                    .contains(interest)) {

                score += 80;
            }
        }

        return score;
    }
}