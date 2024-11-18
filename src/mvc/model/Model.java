package mvc.model;

import mvc.model.cave.Cave;

import java.util.ArrayList;
import java.util.List;

import static mvc.model.Global.Cave_Constants;

/**
 * The {@code Model} class serves as the core representation of the game's state and logic.
 * It manages the {@link Cave}, the list of {@link Player}s, and the game's overall status.
 *
 * <p>This class encapsulates the following functionalities:</p>
 * <ul>
 *     <li>Initialization of the cave and players.</li>
 *     <li>Managing the game's started state.</li>
 *     <li>Adding and removing players.</li>
 *     <li>Allowing players to explore the cave and checking if exploration is complete.</li>
 * </ul>
 */
public class Model {

    /**
     * The cave instance that players explore.
     * Initialized with a minimum side length defined by {@link Cave_Constants}.
     */
    private final Cave cave;

    /**
     * A list of players currently in the game.
     */
    private final List<Player> players;

    /**
     * A flag indicating whether the game has started.
     */
    private boolean started;

    /**
     * Constructs a new {@code Model} with an initialized {@link Cave} and an empty list of players.
     * The game is marked as not started by default.
     */
    public Model() {
        cave = new Cave(Cave_Constants.MIN_SIDE);
        started = false;
        players = new ArrayList<>();
    }

    /**
     * Checks whether the game has started.
     *
     * @return {@code true} if the game has started, {@code false} otherwise
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Sets the game's started state.
     *
     * @param started {@code true} to mark the game as started, {@code false} otherwise
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Retrieves the {@link Cave} instance associated with the game.
     *
     * @return the {@link Cave} instance
     */
    public Cave getCave() {
        return cave;
    }

    /**
     * Adds a new player to the game at the specified position within the cave.
     *
     * @param row the row position where the player is added
     * @param col the column position where the player is added
     */
    public void addPlayer(byte row, byte col) {
        Player player = new Player(row, col);
        player.linkCave(cave);
        players.add(player);
    }

    /**
     * Removes a player from the game at the specified position within the cave.
     *
     * @param row the row position of the player to remove
     * @param col the column position of the player to remove
     */
    public void removePlayer(byte row, byte col) {
        players.remove(new Player(row, col));
    }

    /**
     * Resets all {@link Model} variables to its default values.
     * The cave keeps its size but its empty.
     */
    public void reset() {
        started = false;
        players.clear();
        cave.setCaveSide(cave.getCaveSide());
    }

    /**
     * Instructs all players in the game to explore the cave.
     *
     * <p>Each {@link Player} will call their respective method for exploring the cave.</p>
     */
    public void exploreCave() {
        for (Player player : players) {
            player.exploreCave();
        }
    }

    /**
     * Checks whether all players have finished exploring the cave.
     *
     * @return {@code true} if all players have completed exploration, {@code false} otherwise
     */
    public boolean isCaveExplored() {
        return players.stream().allMatch(Player::hasFinished);
    }
}
