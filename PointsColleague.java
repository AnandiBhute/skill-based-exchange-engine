/**
 * COLLEAGUE: owns everything about points
 * (balance check before the bond, deduction after the bond).
 */
public class PointsColleague extends PlatformColleague {

    private final UserDAO userDAO = new UserDAO();
    private final PointTransactionDAO transactionDAO =
            new PointTransactionDAO();

    public PointsColleague(PlatformMediator mediator) {
        super(mediator, "PointsColleague");
    }

    public void verifyBalance(BondRequest request) {

        if (request.user1.freePoints < ExchangeConfig.BOND_POINTS
                || request.user2.freePoints
                < ExchangeConfig.BOND_POINTS) {

            request.reason = "Insufficient points (each user needs "
                    + ExchangeConfig.BOND_POINTS + ").";
            log(request.reason);

            mediator.relay(
                    this,
                    PlatformEvent.POINTS_INSUFFICIENT,
                    request
            );
            return;
        }

        log("Both users have enough points.");

        mediator.relay(
                this,
                PlatformEvent.POINTS_VERIFIED,
                request
        );
    }

    public void deduct(Bond bond) {

        userDAO.updatePoints(
                bond.user1.userId,
                -bond.pointsPerUser
        );

        userDAO.updatePoints(
                bond.user2.userId,
                -bond.pointsPerUser
        );

        transactionDAO.addTransaction(
                bond.user1.userId,
                bond.bondId,
                -bond.pointsPerUser,
                "Bond points deducted"
        );

        transactionDAO.addTransaction(
                bond.user2.userId,
                bond.bondId,
                -bond.pointsPerUser,
                "Bond points deducted"
        );

        log("Deducted " + bond.pointsPerUser
                + " points from each user.");
    }
}
