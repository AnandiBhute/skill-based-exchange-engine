public interface SkillExchangeService {

    void createBond(
            int user1Id,
            int user2Id,
            String user1Skill,
            String user2Skill
    );

    void completeBond(int bondId);

    void cancelBond(int bondId);
}