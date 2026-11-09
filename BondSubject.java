public class BondSubject {

    public static void attachDefaultObservers(
            Bond bond) {

        bond.addObserver(
                new PointObserver()
        );

        bond.addObserver(
                new AvailabilityObserver()
        );

        bond.addObserver(
                new HistoryObserver()
        );

        bond.addObserver(
                new NotificationObserver()
        );
    }
}