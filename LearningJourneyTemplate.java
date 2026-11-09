/**
 * Template Method Pattern:
 * startJourney() fixes the order of the learning journey steps.
 * Subclasses provide the specific session, matching and evaluation.
 */
public abstract class LearningJourneyTemplate {

    public final void startJourney() {
        System.out.println("\n===== Learning Journey Started =====");
        showSession();
        showMatching();
        showEvaluation();
        System.out.println("===== Learning Journey Completed =====");
    }

    protected abstract void showSession();

    protected abstract void showMatching();

    protected abstract void showEvaluation();
}
