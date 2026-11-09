public class BeginnerFactory
        implements LearningJourneyFactory {

    public Session createSession() {

        return new BeginnerSession();
    }

    public Matching createMatching() {

        return new BeginnerMatching();
    }

    public Evaluation createEvaluation() {

        return new BeginnerEvaluation();
    }
}