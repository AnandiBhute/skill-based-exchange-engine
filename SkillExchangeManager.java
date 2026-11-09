public class SkillExchangeManager {

    private static SkillExchangeManager instance;

    private SkillExchangeManager() {
    }

    public static SkillExchangeManager getInstance() {

        if (instance == null) {
            instance = new SkillExchangeManager();
        }

        return instance;
    }

    public void showSystemStatus() {

        System.out.println(
                "Skill Exchange Platform is running."
        );
    }
}