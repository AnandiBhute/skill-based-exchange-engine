import java.util.ArrayList;
import java.util.List;

public class BondBuilder {

    int bondId = -1;
    User user1;
    User user2;
    String user1Skill;
    String user2Skill;
    int pointsPerUser = ExchangeConfig.BOND_POINTS;
    String status = "ACTIVE";

    private boolean attachDefaultObservers = false;
    private final List<BondObserver> extraObservers = new ArrayList<>();

    public BondBuilder id(int bondId) {
        this.bondId = bondId;
        return this;
    }

    public BondBuilder between(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        return this;
    }

    public BondBuilder user1Teaches(String skill) {
        this.user1Skill = skill;
        return this;
    }

    public BondBuilder user2Teaches(String skill) {
        this.user2Skill = skill;
        return this;
    }

    public BondBuilder points(int pointsPerUser) {
        this.pointsPerUser = pointsPerUser;
        return this;
    }

    public BondBuilder status(String status) {
        this.status = status;
        return this;
    }

    public BondBuilder withDefaultObservers() {
        this.attachDefaultObservers = true;
        return this;
    }

    public BondBuilder withObserver(BondObserver observer) {
        this.extraObservers.add(observer);
        return this;
    }

    public Bond build() {

        if (user1 == null || user2 == null) {
            throw new IllegalStateException("Both users are required.");
        }
        if (user1.userId == user2.userId) {
            throw new IllegalStateException("A user cannot bond with themselves.");
        }
        if (isBlank(user1Skill) || isBlank(user2Skill)) {
            throw new IllegalStateException("Both skills are required.");
        }
        if (pointsPerUser <= 0) {
            throw new IllegalStateException("Points per user must be positive.");
        }

        Bond bond = new Bond(this);

        if (attachDefaultObservers) {
            BondSubject.attachDefaultObservers(bond);
        }
        for (BondObserver observer : extraObservers) {
            bond.addObserver(observer);
        }

        return bond;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}