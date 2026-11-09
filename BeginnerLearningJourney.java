/** Beginner-specific steps for the Template Method Pattern. */
public class BeginnerLearningJourney extends LearningJourneyTemplate {

    @Override
    protected void showSession() {
        new BeginnerSession().show();
    }

    @Override
    protected void showMatching() {
        new BeginnerMatching().show();
    }

    @Override
    protected void showEvaluation() {
        new BeginnerEvaluation().show();
    }
}
