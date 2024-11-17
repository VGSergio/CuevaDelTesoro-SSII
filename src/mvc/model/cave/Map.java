package mvc.model.cave;

import mvc.model.Perceptions;

import java.util.Arrays;

public class Map extends CaveModel {

    public Map(byte caveSide) {
        super(caveSide);
    }

    /**
     * Retrieves all perceptions as an array.
     */
    public Perceptions[] getPerceptions() {
        return Arrays.stream(squares).map(Square::getPerceptions).toArray(Perceptions[]::new);
    }

    /**
     * Retrieves all perceptions counters as a 2D array.
     */
    public byte[][] getAllPerceptionsCounters() {
        return Arrays.stream(squares).map(Square::getPerceptionsCounter).toArray(byte[][]::new);
    }
}
