/**
 * COLLEAGUE: the platform member (user side).
 * Starts a bond request and receives the final outcome.
 * It does not know who matches, charges or notifies.
 */
public class MemberColleague extends PlatformColleague {

    public MemberColleague(PlatformMediator mediator) {
        super(mediator, "MemberColleague");
    }

    public void requestBond(
            int user1Id,
            int user2Id,
            String user1Skill,
            String user2Skill) {

        log("Requesting bond between user "
                + user1Id + " and user " + user2Id
                + " (" + user1Skill + " <-> " + user2Skill + ")");

        mediator.relay(
                this,
                PlatformEvent.BOND_REQUESTED,
                new BondRequest(
                        user1Id,
                        user2Id,
                        user1Skill,
                        user2Skill
                )
        );
    }

    /** Called by the mediator with the final result. */
    public void receiveResult(
            boolean success,
            String message) {

        log((success ? "SUCCESS: " : "REJECTED: ") + message);
    }
}
