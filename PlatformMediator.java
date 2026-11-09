/**
 * MEDIATOR (interface)
 *
 * Defines the single contract through which all colleagues talk to each
 * other. Colleagues only know this interface - never each other.
 */
public interface PlatformMediator {

    /** Adds a colleague to the mediator's group of collaborators. */
    void register(PlatformColleague colleague);

    /**
     * Called by a colleague to announce that something happened.
     * The mediator decides who must react and what happens next.
     */
    void relay(
            PlatformColleague sender,
            PlatformEvent event,
            BondRequest request
    );
}
