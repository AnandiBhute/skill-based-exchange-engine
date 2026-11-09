public class RealSkillExchangeService
        implements SkillExchangeService {

    public void createBond(
            int user1Id,
            int user2Id,
            String user1Skill,
            String user2Skill) {

        UserDAO dao = new UserDAO();

        User user1 = dao.getUser(user1Id);
        User user2 = dao.getUser(user2Id);

        if (user1 == null || user2 == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        if (user1.freePoints <
                ExchangeConfig.BOND_POINTS ||
            user2.freePoints <
                ExchangeConfig.BOND_POINTS) {

            System.out.println(
                    "Insufficient points."
            );

            return;
        }

        int bondId =
                new BondDAO().createBond(
                        user1Id,
                        user2Id,
                        user1Skill,
                        user2Skill,
                        ExchangeConfig.BOND_POINTS
                );

Bond bond;
try {
    bond = new BondDirector().standardBond(
            bondId, user1, user2, user1Skill, user2Skill);
} catch (IllegalStateException e) {
    System.out.println("Invalid bond: " + e.getMessage());
    return;
}

bond.notifyObservers();

        bond.notifyObservers();

        System.out.println(
                "Bond created successfully."
        );

        System.out.println(
                "Bond ID: " + bondId
        );
    }

    public void completeBond(int bondId) {

        new BondDAO().updateStatus(
                bondId,
                "COMPLETED"
        );

        System.out.println(
                "Bond completed."
        );
    }

    public void cancelBond(int bondId) {

        new BondDAO().updateStatus(
                bondId,
                "CANCELLED"
        );

        System.out.println(
                "Bond cancelled."
        );
    }
}