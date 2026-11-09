public class BondDirector {

    public Bond standardBond(int bondId, User u1, User u2,
                             String u1Teaches, String u2Teaches) {

        return new BondBuilder()
                .id(bondId)
                .between(u1, u2)
                .user1Teaches(u1Teaches)
                .user2Teaches(u2Teaches)
                .withDefaultObservers()
                .build();
    }

    public Bond silentBond(int bondId, User u1, User u2,
                           String u1Teaches, String u2Teaches) {

        return new BondBuilder()
                .id(bondId)
                .between(u1, u2)
                .user1Teaches(u1Teaches)
                .user2Teaches(u2Teaches)
                .build();
    }
}