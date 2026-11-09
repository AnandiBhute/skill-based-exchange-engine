/**
 * Events that colleagues send to the mediator.
 */
public enum PlatformEvent {

    BOND_REQUESTED,       // member asked for a bond
    MATCH_APPROVED,       // matching colleague: users are compatible
    MATCH_REJECTED,       // matching colleague: users are not compatible
    POINTS_VERIFIED,      // points colleague: both users can afford it
    POINTS_INSUFFICIENT,  // points colleague: not enough points
    BOND_CREATED,         // bond colleague: bond saved in the database
    BOND_FAILED           // bond colleague: bond could not be saved
}
