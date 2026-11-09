/** Advanced-specific steps for the Template Method Pattern. */
public class AdvancedLearningJourney extends LearningJourneyTemplate {

    @Override
    protected void showSession() {
        new AdvancedSession().show();
    }

    @Override
    protected void showMatching() {
        new AdvancedMatching().show();
    }

    @Override
    protected void showEvaluation() {
        new AdvancedEvaluation().show();
    }
}
