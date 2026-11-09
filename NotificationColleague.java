/**
 * COLLEAGUE: sends notifications to both users.
 */
public class NotificationColleague extends PlatformColleague {

    private final NotificationDAO notificationDAO =
            new NotificationDAO();

    public NotificationColleague(PlatformMediator mediator) {
        super(mediator, "NotificationColleague");
    }

    public void notifyUsers(Bond bond) {

        notificationDAO.addNotification(
                bond.user1.userId,
                "Bond created with " + bond.user2.name
        );

        notificationDAO.addNotification(
                bond.user2.userId,
                "Bond created with " + bond.user1.name
        );

        log("Notifications sent to both users.");
    }
}
