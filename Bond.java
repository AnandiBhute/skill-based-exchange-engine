import java.util.ArrayList;
import java.util.List;

public class Bond {

    int bondId;
    User user1;
    User user2;

    String user1Skill;
    String user2Skill;

    int pointsPerUser;
    String status;

    private List<BondObserver> observers = new ArrayList<>();

Bond(BondBuilder b) {
    this.bondId = b.bondId;
    this.user1 = b.user1;
    this.user2 = b.user2;
    this.user1Skill = b.user1Skill;
    this.user2Skill = b.user2Skill;
    this.pointsPerUser = b.pointsPerUser;
    this.status = b.status;
}

    public void addObserver(BondObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {

        for (BondObserver observer : observers) {
            observer.update(this);
        }
    }
}