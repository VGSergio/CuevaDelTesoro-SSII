package mvc.model.cave;

import mvc.model.Global;
import mvc.model.Global.SquareStatus;

public class Map extends CaveModel {

    public Map(byte caveSide) {
        super(caveSide);
    }

    @Override
    protected SquareStatus getInitialStatus() {
        return SquareStatus.UNKNOWN;
    }

    public Square[] getNeighbors(byte row, byte col) {
        Square[] neighbors = new Square[Global.Directions.values().length];

        byte[][] neighborsRowsAndColumns = getNeighborsRowsAndColumns(row, col);

        for (Global.Directions direction : Global.Directions.values()) {
            byte[] neighbourRowAndColumn = neighborsRowsAndColumns[direction.ordinal()];
            neighbors[direction.ordinal()] = neighbourRowAndColumn == null ? null : getSquare(neighbourRowAndColumn[0], neighbourRowAndColumn[1]);
        }

        return neighbors;
    }


}
