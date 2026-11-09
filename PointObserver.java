public class PointObserver implements BondObserver {

    public void update(Bond bond) {

        UserDAO dao = new UserDAO();

        dao.updatePoints(
                bond.user1.userId,
                -bond.pointsPerUser
        );

        dao.updatePoints(
                bond.user2.userId,
                -bond.pointsPerUser
        );

        new PointTransactionDAO().addTransaction(
                bond.user1.userId,
                bond.bondId,
                -bond.pointsPerUser,
                "Bond points deducted"
        );

        new PointTransactionDAO().addTransaction(
                bond.user2.userId,
                bond.bondId,
                -bond.pointsPerUser,
                "Bond points deducted"
        );

        System.out.println(
                "PointObserver: points deducted."
        );
    }
}