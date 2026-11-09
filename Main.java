import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner =
            new Scanner(System.in);

    static UserService userService =
            new UserService();

    static SkillExchangeService proxy =
            new SkillExchangeServiceProxy(
                    new RealSkillExchangeService()
            );

    static MemberColleague mediatorMember;

    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        SkillExchangeManager manager =
                SkillExchangeManager.getInstance();

        manager.showSystemStatus();

        initMediator();

        while (true) {

            System.out.println(
                    "\n========== SKILL EXCHANGE =========="
            );

            System.out.println("1. Register User");
            System.out.println("2. Add Teaching Skill");
            System.out.println("3. Add Learning Skill");
            System.out.println("4. Add Interest");
            System.out.println("5. View Users");
            System.out.println("6. Find Skill Matches (Strategy Pattern)");
            System.out.println("7. Create Bond");
            System.out.println("8. Complete Bond");
            System.out.println("9. Cancel Bond");
            System.out.println("10. Beginner Learning Journey");
            System.out.println("11. Advanced Learning Journey");
            System.out.println("12. Singleton Test");
            System.out.println("13. Buy Subscription");
            System.out.println("14. Mediator Pattern Demo (automatic)");
            System.out.println("15. Create Bond via Mediator");
            System.out.println("16. Undo Last Skill/Interest Change (Memento)");
            System.out.println("17. Builder Pattern Demo");
            System.out.println("0. Exit");

            System.out.print(
                    "Enter choice: "
            );

            int choice =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

            switch (choice) {

                case 1 -> register();

                case 2 -> addTeachingSkill();

                case 3 -> addLearningSkill();

                case 4 -> addInterest();

                case 5 -> viewUsers();

                case 6 -> findMatches();

                case 7 -> createBond();

                case 8 -> completeBond();

                case 9 -> cancelBond();

                case 10 ->
                        new LearningJourneyService()
                                .showBeginner();

                case 11 ->
                        new LearningJourneyService()
                                .showAdvanced();

                case 12 ->
                        singletonTest();

                case 13 ->
                        subscription();

                case 14 ->
                        mediatorDemo();

                case 15 ->
                        mediatorBond();

                case 16 ->
                        undoProfileChange();
                
                case 17 -> builderDemo();

                case 0 -> {
                    System.out.println(
                            "Program ended."
                    );
                    return;
                }

                default ->
                        System.out.println(
                                "Invalid choice."
                        );
            }
        }
    }

    static void register() {

        System.out.print("Name: ");
        String name =
                scanner.nextLine();

        System.out.print("Email: ");
        String email =
                scanner.nextLine();

        int id =
                userService.register(
                        name,
                        email
                );

        System.out.println(
                "Registered. User ID = " + id
        );
    }

    static void addTeachingSkill() {

        System.out.print("User ID: ");

        int id =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "Teaching skill: "
        );

        String skill =
                scanner.nextLine();

        userService.teach(
                id,
                skill
        );

        System.out.println(
                "Teaching skill added."
        );
    }

    static void addLearningSkill() {

        System.out.print("User ID: ");

        int id =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "Learning skill: "
        );

        String skill =
                scanner.nextLine();

        userService.learn(
                id,
                skill
        );

        System.out.println(
                "Learning skill added."
        );
    }

    static void addInterest() {

        System.out.print("User ID: ");

        int id =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "Interest: "
        );

        String interest =
                scanner.nextLine();

        userService.addInterest(
                id,
                interest
        );

        System.out.println(
                "Interest added."
        );
    }

    static void viewUsers() {

        List<User> users =
                userService.all();

        if (users.isEmpty()) {

            System.out.println(
                    "No users found."
            );

            return;
        }

        for (User user : users) {
            System.out.println(user);
        }
    }

    static void findMatches() {

        System.out.print("Enter User ID: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid user ID.");
            return;
        }

        User user = userService.get(id);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("\\nChoose matching strategy:");
        System.out.println("1. Exact Skill Match");
        System.out.println("2. Similarity-Based Match");
        System.out.println("3. Interest-Based Match");
        System.out.print("Enter choice: ");

        int option;

        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            return;
        }

        MatchingAlgorithm algorithm;
        MatchingStrategy strategy;

        switch (option) {
            case 1 -> {
                algorithm = new ExactMatchAlgorithm();
                strategy = new SkillBasedMatching(algorithm);
            }
            case 2 -> {
                algorithm = new SimilarityMatchAlgorithm();
                strategy = new SkillBasedMatching(algorithm);
            }
            case 3 -> {
                algorithm = new InterestMatchAlgorithm();
                strategy = new InterestBasedMatching(algorithm);
            }
            default -> {
                System.out.println("Invalid matching strategy.");
                return;
            }
        }

        MatchingService service = new MatchingService();
        List<MatchResult> results = service.find(
                user,
                strategy,
                userService.all()
        );

        System.out.println("\\nMatches for " + user.name + ":");

        if (results.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            for (MatchResult result : results) {
                System.out.println(result);
            }
        }
    }

    static void undoProfileChange() {

        System.out.print("Enter User ID: ");

        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid user ID.");
            return;
        }

        boolean restored = userService.undoProfileChange(id);

        if (restored) {
            System.out.println("Previous profile restored.");

            User user = userService.get(id);

            if (user != null) {
                System.out.println(user);
            }
        } else {
            System.out.println(
                    "No saved profile change to undo, "
                    + "or the restore failed."
            );
        }
    }

    static void createBond() {

        System.out.print(
                "User 1 ID: "
        );

        int user1 =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "User 2 ID: "
        );

        int user2 =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "User 1 skill: "
        );

        String skill1 =
                scanner.nextLine();

        System.out.print(
                "User 2 skill: "
        );

        String skill2 =
                scanner.nextLine();

        proxy.createBond(
                user1,
                user2,
                skill1,
                skill2
        );
    }

    static void completeBond() {

        System.out.print(
                "Bond ID: "
        );

        int id =
                Integer.parseInt(
                        scanner.nextLine()
                );

        proxy.completeBond(id);
    }

    static void cancelBond() {

        System.out.print(
                "Bond ID: "
        );

        int id =
                Integer.parseInt(
                        scanner.nextLine()
                );

        proxy.cancelBond(id);
    }

    static void singletonTest() {

        SkillExchangeManager a =
                SkillExchangeManager.getInstance();

        SkillExchangeManager b =
                SkillExchangeManager.getInstance();

        System.out.println(
                "Object 1: " + a
        );

        System.out.println(
                "Object 2: " + b
        );

        System.out.println(
                "Both are same object: "
                        + (a == b)
        );
    }

    static void subscription() {

        System.out.print(
                "User ID: "
        );

        int id =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "Plan name: "
        );

        String plan =
                scanner.nextLine();

        System.out.print(
                "Points: "
        );

        int points =
                Integer.parseInt(
                        scanner.nextLine()
                );

        new SubscriptionService()
                .subscribe(
                        id,
                        plan,
                        points
                );
    }

    // ================== MEDIATOR PATTERN ==================

    static void initMediator() {

        PlatformMediator mediator =
                new SkillExchangeMediator();

        // Colleagues register themselves with the mediator.
        // They only know the mediator, never each other.
        mediatorMember =
                new MemberColleague(mediator);

        new MatchingColleague(mediator);
        new PointsColleague(mediator);
        new BondColleague(mediator);
        new AvailabilityColleague(mediator);
        new HistoryColleague(mediator);
        new NotificationColleague(mediator);
    }

    static void mediatorBond() {

        System.out.print(
                "User 1 ID: "
        );

        int user1 =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "User 2 ID: "
        );

        int user2 =
                Integer.parseInt(
                        scanner.nextLine()
                );

        System.out.print(
                "User 1 skill (taught by user 1): "
        );

        String skill1 =
                scanner.nextLine();

        System.out.print(
                "User 2 skill (taught by user 2): "
        );

        String skill2 =
                scanner.nextLine();

        System.out.println();

        mediatorMember.requestBond(
                user1,
                user2,
                skill1,
                skill2
        );
    }

    static void mediatorDemo() {

        System.out.println(
                "\n===== MEDIATOR PATTERN DEMO ====="
        );

        // Create two demo users with complementary skills.
        // A timestamp keeps the (unique) emails different per run.
        String stamp =
                String.valueOf(
                        System.currentTimeMillis()
                );

        int alice =
                userService.register(
                        "Demo Alice",
                        "alice" + stamp + "@demo.com"
                );

        int bob =
                userService.register(
                        "Demo Bob",
                        "bob" + stamp + "@demo.com"
                );

        if (alice <= 0 || bob <= 0) {

            System.out.println(
                    "Could not create demo users."
            );

            return;
        }

        userService.teach(alice, "Java");
        userService.learn(alice, "Guitar");

        userService.teach(bob, "Guitar");
        userService.learn(bob, "Java");

        System.out.println(
                "Created Demo Alice (ID " + alice
                        + ") teaches Java, learns Guitar"
        );

        System.out.println(
                "Created Demo Bob   (ID " + bob
                        + ") teaches Guitar, learns Java"
        );

        System.out.println(
                "\n--- Scenario 1: invalid request"
                        + " (Alice does not teach Cooking) ---"
        );

        mediatorMember.requestBond(
                alice,
                bob,
                "Cooking",
                "Guitar"
        );

        System.out.println(
                "\n--- Scenario 2: valid request ---"
        );

        mediatorMember.requestBond(
                alice,
                bob,
                "Java",
                "Guitar"
        );
        System.out.println(
                "\n===== END OF MEDIATOR DEMO ====="
        );
    }
    static void builderDemo() {

    System.out.println("\n===== BUILDER PATTERN DEMO =====");

    User a = new User(1, "Alice", "a@x.com", 1000, true);
    User b = new User(2, "Bob", "b@x.com", 1000, true);

    // 1. Fluent construction with defaults
    Bond bond = new BondBuilder()
            .id(99)
            .between(a, b)
            .user1Teaches("Java")
            .user2Teaches("Guitar")
            .build();

    System.out.println("Built bond: " + bond.user1.name + " teaches "
            + bond.user1Skill + ", " + bond.user2.name + " teaches "
            + bond.user2Skill);
    System.out.println("Default points = " + bond.pointsPerUser
            + ", status = " + bond.status);

    // 2. Director recipe
    Bond viaDirector = new BondDirector()
            .standardBond(100, a, b, "Python", "Cooking");
    System.out.println("Director built bond ID " + viaDirector.bondId);

    // 3. Validation
    try {
        new BondBuilder().id(101).between(a, a)
                .user1Teaches("Java").user2Teaches("Java").build();
    } catch (IllegalStateException e) {
        System.out.println("Rejected: " + e.getMessage());
    }

    System.out.println("===== END OF BUILDER DEMO =====");
}
}