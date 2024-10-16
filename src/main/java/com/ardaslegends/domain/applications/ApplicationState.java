package com.ardaslegends.domain.applications;

/**
 * Enum representing the state of an application.
 * Each state has a display name associated with it.
 */
public enum ApplicationState {
    /**
     * State representing an accepted application.
     */
    ACCEPTED("Accepted"),

    /**
     * State representing an open application.
     */
    OPEN("Open"),

    /**
     * State representing an application denied by staff.
     */
    DENIED_BY_STAFF("Denied by staff"),

    /**
     * State representing a withdrawn application.
     */
    WITHDRAWN("Withdrawn");

    /**
     * The display name of the application state.
     */
    public final String displayName;

    /**
     * Constructor for creating an application state with a display name.
     *
     * @param displayName the display name of the application state
     */
    ApplicationState(String displayName) {
        this.displayName = displayName;
    }
}