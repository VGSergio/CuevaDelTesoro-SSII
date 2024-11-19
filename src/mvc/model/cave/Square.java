package mvc.model.cave;

import mvc.model.Global.SquareStatus;
import mvc.model.Perceptions;

public class Square {

    /**
     * The current status of this square.
     */
    private SquareStatus status;

    /**
     * The perceptions associated with this square.
     */
    private Perceptions perceptions;

    /**
     * Indicates whether this square has been visited.
     */
    private boolean visited;

    /**
     * Indicates whether this square contains a treasure.
     */
    private boolean hasTreasure;

    /**
     * Constructs a new {@code Square} with the specified status.
     *
     * @param status The initial status of this square.
     */
    public Square(SquareStatus status) {
        this.status = status;
        perceptions = null;
        visited = false;
    }

    /**
     * Retrieves the current status of this square.
     *
     * @return The current {@link SquareStatus}.
     */
    public SquareStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of this square.
     *
     * <p>If the status is set to {@link SquareStatus#TREASURE},
     * the square is marked as containing a treasure.</p>
     *
     * @param status The new {@link SquareStatus}.
     */
    public void setStatus(SquareStatus status) {
        this.status = status;
        if (status == SquareStatus.TREASURE) hasTreasure = true;
    }

    /**
     * Checks whether this square contains a treasure.
     *
     * @return {@code true} if this square has a treasure, otherwise {@code false}.
     */
    public boolean hasTreasure() {
        return hasTreasure;
    }

    /**
     * Sets whether this square contains a treasure.
     *
     * @param hasTreasure {@code true} to mark this square as containing a treasure.
     */
    public void setHasTreasure(boolean hasTreasure) {
        this.hasTreasure = hasTreasure;
    }

    /**
     * Retrieves the perceptions associated with this square.
     *
     * @return The {@link Perceptions} object for this square.
     */
    public Perceptions getPerceptions() {
        return perceptions;
    }

    /**
     * Updates the perceptions for this square.
     *
     * @param perceptions The new {@link Perceptions} object to associate with this square.
     */
    public void setPerceptions(Perceptions perceptions) {
        this.perceptions = perceptions;
    }

    /**
     * Checks whether this square has not been visited.
     *
     * @return {@code true} if the square has not been visited, otherwise {@code false}.
     */
    public boolean notVisited() {
        return !visited;
    }

    /**
     * Marks this square as visited or unvisited.
     *
     * @param value {@code true} to mark this square as visited.
     */
    public void setVisited(boolean value) {
        visited = value;
    }
}
