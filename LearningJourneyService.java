public class LearningJourneyService {

    public void showBeginner() {
        LearningJourneyTemplate journey =
                new BeginnerLearningJourney();
        journey.startJourney();
    }

    public void showAdvanced() {
        LearningJourneyTemplate journey =
                new AdvancedLearningJourney();
        journey.startJourney();
    }
}
