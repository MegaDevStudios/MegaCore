package dev.mega.megacore.manager;

/**
 * Represents the interface for reloadable classes.
 */
public interface Reloadable {
    /**
     * Reloads the Manager's resources
     */
    default void reload() {
        disable();
        enable();
    }

    /**
     * Sets the Manager's resources
     */
    void enable();

    /**
     * Cleans up the Manager's resources
     */
    void disable();
}
