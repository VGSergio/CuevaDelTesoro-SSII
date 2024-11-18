package mvc.model.cave;

import static mvc.model.Global.SquareStatus;

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
}
