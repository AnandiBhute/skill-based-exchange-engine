/**
 * Data carried between colleagues (through the mediator) while one
 * bond request is being processed. Colleagues fill in the parts they own.
 */
public class BondRequest {

    final int user1Id;
    final int user2Id;
    final String user1Skill;
    final String user2Skill;

    User user1;          // filled in by MatchingColleague
    User user2;          // filled in by MatchingColleague
    Bond bond;           // filled in by BondColleague
    String reason;       // explanation when a step rejects the request

    public BondRequest(
            int user1Id,
            int user2Id,
            String user1Skill,
            String user2Skill) {

        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.user1Skill = user1Skill;
        this.user2Skill = user2Skill;
    }
}
