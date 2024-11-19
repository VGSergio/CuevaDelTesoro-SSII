package mvc.model.cave;

import mvc.model.Global.Directions;
import mvc.model.Global.SquareStatus;

/**
 * Represents a specific implementation of the {@link CaveModel}, defining a map structure.
 * The initial status of all squares in the map is {@link SquareStatus#UNKNOWN}.
 */
public class Map extends CaveModel {

    /**
     * Constructs a new {@code Map} with the specified grid size.
     *
     * @param caveSide The side length of the map grid.
     */
    public Map(byte caveSide) {
        super(caveSide);
    }

    /**
     * Defines the initial status for all squares in the map.
     *
     * @return The default square status, which is {@link SquareStatus#UNKNOWN}.
     */
    @Override
    protected SquareStatus getInitialStatus() {
        return SquareStatus.UNKNOWN;
    }

    /**
     * Retrieves all neighbors of a given square.
     *
     * <p>The neighbors are determined based on valid directions. If a neighbor does not exist
     * (e.g., it is out of bounds), its corresponding position in the returned array will be {@code null}.</p>
     *
     * @param row The row index of the square.
     * @param col The column index of the square.
     * @return An array of {@link Square} objects representing the neighbors in each direction.
     * The array indexes correspond to the order of {@link Directions}.
     */
    public Square[] getNeighbors(byte row, byte col) {
        Square[] neighbors = new Square[Directions.values().length];
        byte[][] neighborPositions = getNeighborPositions(row, col);

        for (Directions direction : Directions.values()) {
            byte[] position = neighborPositions[direction.ordinal()];
            neighbors[direction.ordinal()] = (position != null) ? getSquare(position[0], position[1]) : null;
        }

        return neighbors;
    }
}