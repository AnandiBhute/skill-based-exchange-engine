/**
 * COLLEAGUE (abstract base)
 *
 * Every colleague holds a reference to the mediator only.
 * No colleague holds a reference to another colleague.
 */
public abstract class PlatformColleague {

    protected final PlatformMediator mediator;
    private final String name;

    protected PlatformColleague(
            PlatformMediator mediator,
            String name) {

        this.mediator = mediator;
        this.name = name;

        mediator.register(this);
    }

    public String getName() {
        return name;
    }

    protected void log(String message) {

        System.out.println(
                "   [" + name + "] " + message
        );
    }
}
