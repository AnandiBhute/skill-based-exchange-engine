public class SubscriptionService {

    public void subscribe(
            int userId,
            String plan,
            int points) {

        new SubscriptionDAO()
                .addSubscription(
                        userId,
                        plan,
                        points
                );

        new UserDAO().updatePoints(
                userId,
                points
        );

        System.out.println(
                "Subscription activated."
        );

        System.out.println(
                points + " points added."
        );
    }
}