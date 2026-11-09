/**
 * COLLEAGUE: creates the bond record in the database.
 */
public class BondColleague extends PlatformColleague {

    private final BondDAO bondDAO = new BondDAO();

    public BondColleague(PlatformMediator mediator) {
        super(mediator, "BondColleague");
    }

    public void createBond(BondRequest request) {

        int bondId =
                bondDAO.createBond(
                        request.user1Id,
                        request.user2Id,
                        request.user1Skill,
                        request.user2Skill,
                        ExchangeConfig.BOND_POINTS
                );

        if (bondId <= 0) {

            request.reason = "Bond could not be saved.";
            log(request.reason);

            mediator.relay(
                    this,
                    PlatformEvent.BOND_FAILED,
                    request
            );
            return;
        }

try {
    request.bond = new BondBuilder()
            .id(bondId)
            .between(request.user1, request.user2)
            .user1Teaches(request.user1Skill)
            .user2Teaches(request.user2Skill)
            .build();
} catch (IllegalStateException e) {
    request.reason = e.getMessage();
    log(request.reason);
    mediator.relay(this, PlatformEvent.BOND_FAILED, request);
    return;
}

        log("Bond created. Bond ID = " + bondId);

        mediator.relay(
                this,
                PlatformEvent.BOND_CREATED,
                request
        );
    }
}
