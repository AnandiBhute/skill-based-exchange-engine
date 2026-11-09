public class SkillExchangeServiceProxy
        implements SkillExchangeService {

    private RealSkillExchangeService realService;

    public SkillExchangeServiceProxy(
            RealSkillExchangeService realService) {

        this.realService = realService;
    }

    public void createBond(
            int user1Id,
            int user2Id,
            String user1Skill,
            String user2Skill) {

        System.out.println(
                "Proxy: validating request..."
        );

        if (user1Id <= 0 ||
                user2Id <= 0) {

            System.out.println(
                    "Invalid user ID."
            );

            return;
        }

        if (user1Skill == null ||
                user1Skill.isBlank() ||
                user2Skill == null ||
                user2Skill.isBlank()) {

            System.out.println(
                    "Skills cannot be empty."
            );

            return;
        }

        System.out.println(
                "Proxy: access granted."
        );

        realService.createBond(
                user1Id,
                user2Id,
                user1Skill,
                user2Skill
        );
    }

    public void completeBond(int bondId) {

        if (bondId <= 0) {
            System.out.println(
                    "Invalid bond ID."
            );
            return;
        }

        realService.completeBond(bondId);
    }

    public void cancelBond(int bondId) {

        if (bondId <= 0) {
            System.out.println(
                    "Invalid bond ID."
            );
            return;
        }

        realService.cancelBond(bondId);
    }
}