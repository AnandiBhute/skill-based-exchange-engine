public class AdvancedFactory
        implements LearningJourneyFactory {

    public Session createSession() {

        return new AdvancedSession();
    }

    public Matching createMatching() {

        return new AdvancedMatching();
    }

    public Evaluation createEvaluation() {

        return new AdvancedEvaluation();
    }
}