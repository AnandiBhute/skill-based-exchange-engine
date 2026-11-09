/**
 * COLLEAGUE: marks users as unavailable once they are bonded.
 */
public class AvailabilityColleague extends PlatformColleague {

    private final UserDAO userDAO = new UserDAO();

    public AvailabilityColleague(PlatformMediator mediator) {
        super(mediator, "AvailabilityColleague");
    }

    public void markUnavailable(Bond bond) {

        userDAO.updateAvailability(bond.user1.userId, false);
        userDAO.updateAvailability(bond.user2.userId, false);

        log("Users marked unavailable.");
    }
}
