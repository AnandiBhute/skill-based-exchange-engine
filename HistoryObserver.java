public class HistoryObserver implements BondObserver {

    public void update(Bond bond) {

        new BondHistoryDAO().addHistory(
                bond
        );

        System.out.println(
                "HistoryObserver: bond history saved."
        );
    }
}