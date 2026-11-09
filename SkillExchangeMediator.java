/**
 * CONCRETE MEDIATOR
 *
 * Holds all the coordination logic of the "create a bond" workflow.
 * Colleagues never call each other; they only announce events here,
 * and this class decides which colleague acts next:
 *
 *   BOND_REQUESTED -> Matching -> Points(verify) -> Bond
 *   BOND_CREATED   -> Points(deduct), Availability, History,
 *                     Notification, then the Member gets the result.
 *   Any rejection  -> the Member gets the reason.
 */
public class SkillExchangeMediator implements PlatformMediator {

    private MemberColleague member;
    private MatchingColleague matching;
    private PointsColleague points;
    private BondColleague bondCreator;
    private AvailabilityColleague availability;
    private HistoryColleague history;
    private NotificationColleague notification;

    @Override
    public void register(PlatformColleague colleague) {

        if (colleague instanceof MemberColleague c) {
            member = c;
        } else if (colleague instanceof MatchingColleague c) {
            matching = c;
        } else if (colleague instanceof PointsColleague c) {
            points = c;
        } else if (colleague instanceof BondColleague c) {
            bondCreator = c;
        } else if (colleague instanceof AvailabilityColleague c) {
            availability = c;
        } else if (colleague instanceof HistoryColleague c) {
            history = c;
        } else if (colleague instanceof NotificationColleague c) {
            notification = c;
        } else {
            throw new IllegalArgumentException(
                    "Unknown colleague: " + colleague.getName()
            );
        }
    }

    @Override
    public void relay(
            PlatformColleague sender,
            PlatformEvent event,
            BondRequest request) {

        System.out.println(
                "[Mediator] " + sender.getName()
                        + " -> " + event
        );

        switch (event) {

            case BOND_REQUESTED ->
                    matching.evaluate(request);

            case MATCH_APPROVED ->
                    points.verifyBalance(request);

            case POINTS_VERIFIED ->
                    bondCreator.createBond(request);

            case BOND_CREATED -> {

                points.deduct(request.bond);
                availability.markUnavailable(request.bond);
                history.record(request.bond);
                notification.notifyUsers(request.bond);

                member.receiveResult(
                        true,
                        "Bond created successfully. Bond ID: "
                                + request.bond.bondId
                );
            }

            case MATCH_REJECTED,
                 POINTS_INSUFFICIENT,
                 BOND_FAILED ->
                    member.receiveResult(
                            false,
                            request.reason
                    );
        }
    }
}
