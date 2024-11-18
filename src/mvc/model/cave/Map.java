package mvc.model.cave;

import mvc.model.Global.SquareStatus;
import mvc.model.Perceptions;

import java.util.Arrays;

public class Map extends CaveModel {

    public Map(byte caveSide) {
        super(caveSide);
    }

    @Override
    protected SquareStatus getInitialStatus() {
        return SquareStatus.UNKNOWN;
    }

    /**
     * Retrieves all perceptions as an array.
     */
    public Perceptions[] getPerceptions() {
        return Arrays.stream(squares).map(Square::getPerceptions).toArray(Perceptions[]::new);
    }
}
