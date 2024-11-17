package mvc.model.cave;

import mvc.model.Perceptions;

import static mvc.model.Global.*;

/**
 * Represents a specific implementation of a cave, extending the {@link CaveModel}.
 *
 * <p>This class provides additional functionality for managing monsters, treasures,
 * and players within the cave grid. It also introduces mechanisms to calculate and
 * update perceptions for squares based on their surroundings, allowing dynamic
 * gameplay interactions.</p>
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Tracks the number of monsters, treasures, and players within the cave.</li>
 *   <li>Provides mechanisms to adjust these item counts dynamically.</li>
 *   <li>Calculates and updates square perceptions based on the statuses of neighboring squares.</li>
 *   <li>Allows the cave size to be modified dynamically, with automatic reinitialization of squares and counts.</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <p>Instances of {@code Cave} can be used to represent game maps where items, entities,
 * and player interactions depend on spatial relationships and grid-based logic.</p>
 *
 * @author Sergio Vega García
 */
public class Cave extends CaveModel {

    /**
     * The number of monsters currently in the cave.
     */
    private byte amountOfMonsters;

    /**
     * The number of treasures currently in the cave.
     */
    private byte amountOfTreasures;

    /**
     * The number of players currently in the cave.
     */
    private byte amountOfPlayers;

    /**
     * Constructs a new {@code Cave} with the specified side length.
     *
     * @param caveSide The side length of the cave grid.
     */
    public Cave(byte caveSide) {
        super(caveSide);
        initializeItemCounts();
    }

    /**
     * Provides the initial {@link SquareStatus} for all squares in this cave.
     *
     * @return The default {@link SquareStatus}, which is {@code CLEAN}.
     */
    @Override
    protected SquareStatus getInitialStatus() {
        return SquareStatus.CLEAN;
    }

    /**
     * Initializes the item counts for the cave.
     *
     * <p>Sets the number of monsters and treasures to zero.</p>
     */
    private void initializeItemCounts() {
        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
        this.amountOfPlayers = 0;
    }

    /**
     * Updates the perceptions for a specific square based on its row and column.
     *
     * <p>Perceptions are calculated by examining the statuses of neighboring squares
     * in all valid directions. Each perception type is mapped from the neighbor's
     * {@link SquareStatus} and set for the target square.</p>
     *
     * @param row    The row index of the target square.
     * @param column The column index of the target square.
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
     * Dynamically updates the side length of the cave.
     *
     * <p>This method reinitializes the grid of squares and resets item counts.</p>
     *
     * @param caveSide The new side length of the cave grid.
     */
    public void setCaveSide(byte caveSide) {
        this.caveSide = caveSide;
        initializeSquares();
        initializeItemCounts();
    }

    /**
     * Returns the number of monsters currently in the cave.
     *
     * @return The current count of monsters.
     */
    public byte getAmountOfMonsters() {
        return amountOfMonsters;
    }

    /**
     * Adjusts the number of monsters in the cave by a given delta.
     *
     * @param delta The amount to add (positive) or remove (negative) from the monster count.
     */
    public void adjustAmountOfMonsters(int delta) {
        this.amountOfMonsters += (byte) delta;
    }

    /**
     * Returns the number of treasures currently in the cave.
     *
     * @return The current count of treasures.
     */
    public byte getAmountOfTreasures() {
        return amountOfTreasures;
    }

    /**
     * Adjusts the number of treasures in the cave by a given delta.
     *
     * @param delta The amount to add (positive) or remove (negative) from the treasure count.
     */
    public void adjustAmountOfTreasures(int delta) {
        this.amountOfTreasures += (byte) delta;
    }

    /**
     * Returns the number of players currently in the cave.
     *
     * @return The current count of players.
     */
    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    /**
     * Adjusts the number of players in the cave by a given delta.
     *
     * @param delta The amount to add (positive) or remove (negative) from the player count.
     */
    public void adjustAmountOfPlayers(int delta) {
        this.amountOfPlayers += (byte) delta;
    }

    /**
     * Updates perceptions for all squares in the cave.
     *
     * <p>This method iterates through every square in the cave grid and recalculates
     * their perceptions based on the statuses of their neighbors. It is called when the
     * simulation starts</p>
     */
    public void updateAllPerceptions() {
        for (int position = 0; position < squares.length; position++) {
            updatePerceptions((byte) (position / caveSide), (byte) (position % caveSide));
        }
    }
}
