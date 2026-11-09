public interface LearningJourneyFactory {

    Session createSession();

    Matching createMatching();

    Evaluation createEvaluation();
}