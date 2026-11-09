/**
 * COLLEAGUE: records the bond in the bond history table.
 */
public class HistoryColleague extends PlatformColleague {

    private final BondHistoryDAO historyDAO =
            new BondHistoryDAO();

    public HistoryColleague(PlatformMediator mediator) {
        super(mediator, "HistoryColleague");
    }

    public void record(Bond bond) {

        historyDAO.addHistory(bond);

        log("Bond history saved.");
    }
}
