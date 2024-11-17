package mvc.model.cave;

import mvc.model.Global.PerceptionType;
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

    /**
     * Counter of how many times this Square instance neighbours have perceived a certain PerceptionType.
     * Minimum number of neighbours: 2. Maximum number of neighbours: 4.
     * Minimum counter value: 0. Maximum counter value: 4.
     */
    private final byte[] perceptionsCounter;
    private boolean visited;

    public Square() {
        status = SquareStatus.UNKNOWN;
        perceptions = null;
        perceptionsCounter = new byte[PerceptionType.values().length];
        visited = false;
    }

    public SquareStatus getStatus() {
        return status;
    }

    public void setStatus(SquareStatus status) {
        this.status = status;
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

    public byte[] getPerceptionsCounter() {
        return perceptionsCounter;
    }

    public void adjustPerceptionsCounter(PerceptionType perception, int value) {
        perceptionsCounter[perception.ordinal()] += (byte) value;
    }
}
