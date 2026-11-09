import java.util.List;

/**
 * COLLEAGUE: checks whether two users are a valid skill match.
 * Reuses the existing MatchingService / SkillBasedMatching /
 * ExactMatchAlgorithm classes.
 */
public class MatchingColleague extends PlatformColleague {

    private final UserDAO userDAO = new UserDAO();
    private final MatchingService matchingService =
            new MatchingService();

    public MatchingColleague(PlatformMediator mediator) {
        super(mediator, "MatchingColleague");
    }

    public void evaluate(BondRequest request) {

        request.user1 = userDAO.getUser(request.user1Id);
        request.user2 = userDAO.getUser(request.user2Id);

        if (request.user1 == null || request.user2 == null) {

            request.reason = "User not found.";
            log(request.reason);

            mediator.relay(
                    this,
                    PlatformEvent.MATCH_REJECTED,
                    request
            );
            return;
        }

        if (!request.user1.teachingSkills
                .contains(request.user1Skill)) {

            request.reason = request.user1.name
                    + " does not teach " + request.user1Skill + ".";
            reject(request);
            return;
        }

        if (!request.user2.teachingSkills
                .contains(request.user2Skill)) {

            request.reason = request.user2.name
                    + " does not teach " + request.user2Skill + ".";
            reject(request);
            return;
        }

        MatchingStrategy strategy =
                new SkillBasedMatching(
                        new ExactMatchAlgorithm()
                );

        List<MatchResult> results =
                matchingService.find(
                        request.user1,
                        strategy,
                        List.of(request.user1, request.user2)
                );

        if (results.isEmpty()) {

            request.reason = request.user1.name + " and "
                    + request.user2.name
                    + " have no complementary skills.";
            reject(request);
            return;
        }

        log("Match found: " + results.get(0));

        mediator.relay(
                this,
                PlatformEvent.MATCH_APPROVED,
                request
        );
    }

    private void reject(BondRequest request) {

        log(request.reason);

        mediator.relay(
                this,
                PlatformEvent.MATCH_REJECTED,
                request
        );
    }
}
