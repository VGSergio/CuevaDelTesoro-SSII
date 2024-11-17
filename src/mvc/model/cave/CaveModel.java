package mvc.model.cave;

import mvc.model.Global.SquareStatus;

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
     * Initializes the {@code squares} array with {@link Square} objects.
     *
     * <p>Each square in the grid is created with the default {@link SquareStatus}
     * provided by {@link #getInitialStatus()}.</p>
     */
    protected void initializeSquares() {
        SquareStatus status = getInitialStatus();
        squares = new Square[caveSide * caveSide];
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square(status);
        }
    }

    /**
     * Calculates the linear index of a square in the cave based on its row and column.
     *
     * <p>This method translates 2D grid coordinates into a 1D array index using the formula:
     * {@code index = row * caveSide + column}.</p>
     *
     * @param row    The row index (0-based).
     * @param column The column index (0-based).
     * @return The linear index in the {@code squares} array.
     */
    public int getSquarePositionInCave(byte row, byte column) {
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
        return squares[getSquarePositionInCave(row, col)];
    }

    /**
     * Returns the array of all squares in the cave grid.
     *
     * <p>This array is a 1D representation of the 2D grid, with rows and columns
     * flattened into a single dimension. Use {@link #getSquare(byte, byte)} to access
     * specific squares by their 2D coordinates.</p>
     *
     * @return The array of {@link Square} objects in the cave.
     */
    public Square[] getSquares() {
        return squares;
    }

    /**
     * Returns the side length of the cave grid.
     *
     * @return The number of rows and columns in the cave grid.
     */
    public byte getCaveSide() {
        return caveSide;
    }

    /**
     * Checks if a given row and column are within the bounds of the cave grid.
     *
     * <p>A position is considered valid if:
     * <ul>
     *   <li>{@code 0 <= row < caveSide}</li>
     *   <li>{@code 0 <= column < caveSide}</li>
     * </ul>
     * </p>
     *
     * @param row    The row index to check.
     * @param column The column index to check.
     * @return {@code true} if the position is within bounds, otherwise {@code false}.
     */
    public boolean isWithinBounds(byte row, byte column) {
        return row >= 0 && row < caveSide && column >= 0 && column < caveSide;
    }
}
