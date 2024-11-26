package mvc.model.cave;

import mvc.model.Global.Directions;
import mvc.model.Global.PerceptionType;
import mvc.model.Global.SquareStatus;
import mvc.model.Perceptions;

import static mvc.model.Global.getDirectionDelta;
import static mvc.model.Global.mapStatusToPerception;

/**
 * Represents an abstract model of a cave, which is organized as a grid of squares.
 *
 * <p>This class serves as the base for specific cave implementations, providing core functionality
 * for managing a grid of {@link Square} objects and determining their positions based on rows
 * and columns. The specific characteristics of the cave, such as the initial {@link SquareStatus},
 * must be defined by subclasses.</p>
 *
 * <p>The cave is represented as a 1D array of {@link Square} objects for memory efficiency, and
 * utilities are provided to translate between 2D grid coordinates and the underlying array indices.</p>
 *
 * <h2>Key Responsibilities</h2>
 * <ul>
 *   <li>Manage the grid of {@link Square} objects based on the cave size.</li>
 *   <li>Provide utility methods for accessing squares and validating grid boundaries.</li>
 *   <li>Allow subclasses to define the initial {@link SquareStatus} for the grid.</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * Subclasses must implement the {@link #getInitialStatus()} method to specify the default status
 * for the cave's squares.
 *
 * @author Sergio Vega García
 */
public abstract class CaveModel {

    /**
     * The side length of the cave grid. This value determines the number of rows and columns in the grid.
     */
    protected byte caveSide;

    /**
     * The 1D array representing the grid of squares in the cave.
     * The array's size is {@code caveSide * caveSide}.
     */
    protected Square[] squares;

    /**
     * Constructs a new {@code CaveModel} with the specified grid size.
     *
     * @param caveSide The side length of the square cave grid. Must be greater than zero.
     */
    public CaveModel(byte caveSide) {
        this.caveSide = caveSide;
        this.squares = new Square[caveSide * caveSide];
        initializeSquares();
    }

    /**
     * Provides the initial {@link SquareStatus} for all squares in the cave grid.
     *
     * <p>This method must be implemented by subclasses to specify the default status
     * for the cave's squares.</p>
     *
     * @return The default {@link SquareStatus} for this cave.
     */
    protected abstract SquareStatus getInitialStatus();

    /**
     * Initializes the {@code squares} array with {@link Square} objects, all with
     * the initial {@link SquareStatus}.
     */
    protected void initializeSquares() {
        SquareStatus initialStatus = getInitialStatus();
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square(initialStatus);
        }
    }

    /**
     * Converts 2D coordinates (row, column) into a linear index for the squares array.
     *
     * @param row    The row index (0-based).
     * @param column The column index (0-based).
     * @return The linear index.
     */
    public int toLinearIndex(byte row, byte column) {
        return row * caveSide + column;
    }

    /**
     * Retrieves the {@link Square} object at the specified row and column.
     *
     * @param row The row index (0-based).
     * @param col The column index (0-based).
     * @return The {@link Square} object at the specified position.
     */
    public Square getSquare(byte row, byte col) {
        return squares[toLinearIndex(row, col)];
    }

    /**
     * Returns the side length of the cave grid.
     *
     * @return The side length.
     */
    public byte getCaveSide() {
        return caveSide;
    }

    /**
     * Checks if the given row and column indices are within the cave grid bounds.
     *
     * @param row    The row index.
     * @param column The column index.
     * @return {@code true} if within bounds; {@code false} otherwise.
     */
    public boolean isWithinBounds(byte row, byte column) {
        return row >= 0 && row < caveSide && column >= 0 && column < caveSide;
    }

    /**
     * Updates the perceptions for a specific square based on its neighbors.
     *
     * @param row    The row index of the square.
     * @param column The column index of the square.
     */
    public void updatePerceptions(byte row, byte column) {
        Square square = getSquare(row, column);
        Perceptions perceptions = new Perceptions();

        // Calculate perceptions based on neighbors
        for (Directions direction : Directions.values()) {
            byte[] delta = getDirectionDelta(direction);
            byte neighborRow = (byte) (row + delta[0]);
            byte neighborCol = (byte) (column + delta[1]);

            if (isWithinBounds(neighborRow, neighborCol)) {
                PerceptionType perceptionType = mapStatusToPerception(getSquare(neighborRow, neighborCol).getStatus());
                if (perceptionType != null) {
                    perceptions.setPerception(perceptionType, true);
                }
            }
        }
        square.setPerceptions(perceptions);
    }

    /**
     * Retrieves the neighbors' positions (row, column) for a given square.
     *
     * @param row The row index of the square.
     * @param col The column index of the square.
     * @return A 2D array containing valid neighbors' positions.
     */
    protected byte[][] getNeighborPositions(byte row, byte col) {
        byte[][] neighbors = new byte[Directions.values().length][];
        for (Directions direction : Directions.values()) {
            byte[] delta = getDirectionDelta(direction);
            byte neighborRow = (byte) (row + delta[0]);
            byte neighborCol = (byte) (col + delta[1]);

            if (isWithinBounds(neighborRow, neighborCol)) {
                neighbors[direction.ordinal()] = new byte[]{neighborRow, neighborCol};
            } else {
                neighbors[direction.ordinal()] = null;
            }
        }
        return neighbors;
    }

    /**
     * Updates the perceptions for all neighboring squares of a given square.
     *
     * @param row The row index of the square.
     * @param col The column index of the square.
     */
    public void updateNeighborPerceptions(byte row, byte col) {
        byte[][] neighbors = getNeighborPositions(row, col);
        for (byte[] neighbor : neighbors) {
            if (neighbor != null) {
                updatePerceptions(neighbor[0], neighbor[1]);
            }
        }
    }
}
