package mvc.model.cave;

import mvc.model.Global.SquareStatus;
import mvc.model.Perceptions;

public class Square {

    /**
     * SquareStatus of this square
     */
    private SquareStatus status;

    /**
     * Perceptions perceived from this squared.
     */
    private Perceptions perceptions;

    private boolean visited;

    private boolean hasTreasure;

    public Square(SquareStatus status) {
        this.status = status;
        perceptions = null;
        visited = false;
    }

    public SquareStatus getStatus() {
        return status;
    }

    public void setStatus(SquareStatus status) {
        this.status = status;
        if (status == SquareStatus.TREASURE) {
            hasTreasure = true;
        }
    }

    public boolean hasTreasure() {
        return hasTreasure;
    }

    public void setHasTreasure(boolean hasTreasure) {
        this.hasTreasure = hasTreasure;
    }

    public Perceptions getPerceptions() {
        return perceptions;
    }

    public void setPerceptions(Perceptions perceptions) {
        this.perceptions = perceptions;
    }

    public boolean notVisited() {
        return !visited;
    }

    public void setVisited(boolean value) {
        visited = value;
    }
}
