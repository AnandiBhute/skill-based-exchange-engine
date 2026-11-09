public class NotificationObserver
        implements BondObserver {

    public void update(Bond bond) {

        NotificationDAO dao =
                new NotificationDAO();

        dao.addNotification(
                bond.user1.userId,
                "Bond created with "
                        + bond.user2.name
        );

        dao.addNotification(
                bond.user2.userId,
                "Bond created with "
                        + bond.user1.name
        );

        System.out.println(
                "NotificationObserver: notifications sent."
        );
    }
}