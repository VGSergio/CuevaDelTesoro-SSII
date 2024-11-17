package mvc.model;

import java.util.Arrays;

import static mvc.model.Global.PerceptionType;

/**
 * The {@code Perceptions} class manages the state of various perceptual attributes
 * related to an entity. Each perception is represented as a boolean value indicating
 * whether that perception is active or not.
 *
 * <p>For example, perceptions can represent sensory inputs such as "Stench," "Breeze,"
 * and other environmental or situational indicators. The class allows querying and updating
 * these perceptions and provides a summary of their current state.</p>
 */
public class Perceptions {

    /**
     * An array storing the state of each perception, mapped to the ordinal values
     * of the {@link PerceptionType} enumeration.
     */
    private final boolean[] perceptions;

    /**
     * Constructs a {@code Perceptions} object and initializes all perceptions
     * to {@code false}.
     */
    public Perceptions() {
        this.perceptions = new boolean[PerceptionType.values().length];
        Arrays.fill(this.perceptions, false); // Initialize all perceptions to false
    }

    /**
     * Retrieves the state of a specific perception.
     *
     * @param perceptionType the type of perception to retrieve, as defined in {@link PerceptionType}
     * @return {@code true} if the perception is active, {@code false} otherwise
     */
    public boolean getPerception(PerceptionType perceptionType) {
        return perceptions[perceptionType.ordinal()];
    }

    /**
     * Sets the state of a specific perception.
     *
     * @param perceptionType the type of perception to update, as defined in {@link PerceptionType}
     * @param value          {@code true} to activate the perception, {@code false} to deactivate it
     */
    public void setPerception(PerceptionType perceptionType, boolean value) {
        this.perceptions[perceptionType.ordinal()] = value;
    }

    /**
     * Checks if all perceptions are inactive, indicating a "clean" state.
     *
     * @return {@code true} if all perceptions are inactive, {@code false} if any perception is active
     */
    public boolean isClean() {
        for (boolean value : perceptions) {
            if (value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the current state of all perceptions.
     *
     * <p>Each perception is listed along with its current state (active or inactive).</p>
     *
     * @return a string summarizing the state of all perceptions
     */
    @Override
    public String toString() {
        return "Stench: " + getPerception(PerceptionType.STENCH) +
                "\nBreeze: " + getPerception(PerceptionType.BREEZE) +
                "\nRadiance: " + getPerception(PerceptionType.RADIANCE) +
                "\nBang: " + getPerception(PerceptionType.BANG) +
                "\nGroan: " + getPerception(PerceptionType.GROAN);
    }
}
