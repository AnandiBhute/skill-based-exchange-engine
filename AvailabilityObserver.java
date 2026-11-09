public class AvailabilityObserver
        implements BondObserver {

    public void update(Bond bond) {

        UserDAO dao = new UserDAO();

        dao.updateAvailability(
                bond.user1.userId,
                false
        );

        dao.updateAvailability(
                bond.user2.userId,
                false
        );

        System.out.println(
                "AvailabilityObserver: users marked unavailable."
        );
    }
}