public class BondService {

    private BondDAO dao =
            new BondDAO();

    public int create(
            User user1,
            User user2,
            String skill1,
            String skill2) {

        return dao.createBond(
                user1.userId,
                user2.userId,
                skill1,
                skill2,
                ExchangeConfig.BOND_POINTS
        );
    }

    public void complete(int bondId) {

        dao.updateStatus(
                bondId,
                "COMPLETED"
        );
    }

    public void cancel(int bondId) {

        dao.updateStatus(
                bondId,
                "CANCELLED"
        );
    }
}