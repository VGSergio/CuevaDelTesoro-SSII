package mvc.model;

import mvc.model.cave.Cave;
import mvc.model.cave.Map;
import mvc.model.cave.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mvc.model.Global.*;

/**
 * Represents a player navigating a cave grid.
 * The player can explore the cave, collect treasure, and safely exit.
 */
public class Player {

    // Initial position of the player
    private final byte startingRow;
    private final byte startingCol;

    // Current state of the player
    private byte actualRow;
    private byte actualCol;
    private boolean treasureFound;
    private boolean leftCave;

    // Player's environment
    private Cave cave;
    private Map map;
    private int arrows;

    /**
     * Constructs a player at the given starting position.
     *
     * @param row    The starting row of the player.
     * @param column The starting column of the player.
     */
    public Player(byte row, byte column) {
        startingRow = row;
        startingCol = column;
        actualRow = startingRow;
        actualCol = startingCol;

        treasureFound = false;
        leftCave = false;
    }

    /**
     * Links the player to a cave and initializes the map.
     *
     * @param cave The cave the player will explore.
     */
    public void linkCave(Cave cave) {
        this.cave = cave;
        arrows = cave.getAmountOfMonsters();
        initializeMap();
    }

    // --------------
    // INITIALIZATION
    // --------------

    /**
     * Initializes the player's map based on the cave's size.
     * Marks the player's starting position on the map.
     */
    private void initializeMap() {
        map = new Map(cave.getCaveSide());
        map.getSquare(startingRow, startingCol).setStatus(SquareStatus.PLAYER);
    }

    // ----------
    // MAIN LOGIC
    // ----------

    /**
     * The player explores the cave by perceiving, updating knowledge,
     * and making decisions based on the current state.
     */
    public void exploreCave() {
        getPerceptions();
        updateKnowledge();
        makeDecision();
    }

    /**
     * Retrieves perceptions from the current cave square and updates the map.
     */
    private void getPerceptions() {
        Square caveSquare = cave.getSquare(actualRow, actualCol);
        Square mapSquare = map.getSquare(actualRow, actualCol);

        mapSquare.setVisited(true);
        mapSquare.setPerceptions(caveSquare.getPerceptions());
    }

    /**
     * Updates the player's knowledge about the cave based on current perceptions.
     */
    private void updateKnowledge() {
        byte caveSide = map.getCaveSide();

        for (byte row = 0; row < caveSide; row++) {
            for (byte col = 0; col < caveSide; col++) {
                Square currentSquare = map.getSquare(row, col);

                Perceptions currentPerceptions = currentSquare.getPerceptions();
                Square[] neighbors = map.getNeighbors(row, col);

                // Update neighbors of a clean square
                if (currentPerceptions != null && currentPerceptions.isClean()) {
                    markNeighborsClean(neighbors);
                }

                // Handle squares with one unknown neighbor
                Object[] result = getUnknownNeighbors(neighbors);
                if ((int) result[0] == 1 && result[1] != null) {
                    updateUnknownNeighborStatus((Square) result[1], neighbors, currentPerceptions);
                }

                // Infer status of the current square based on neighbors
                if (currentSquare.getStatus() == SquareStatus.UNKNOWN) {
                    inferSquareStatus(currentSquare, neighbors);
                }
            }
        }
    }

    /**
     * Makes decisions for the player's next action based on the current state.
     */
    private void makeDecision() {
        if (treasureFound) {
            if (canLeave()) {
                leaveCave();
            } else if (shouldShoot()) {
                shoot(getMonsterDirection());
            } else {
                movement(Directions.WEST, Directions.SOUTH, Directions.EAST, Directions.NORTH);
            }
        } else {
            if (canTake()) {
                take();
            } else if (shouldShoot()) {
                shoot(getMonsterDirection());
            } else {
                movementWithPriorities(Directions.NORTH, Directions.EAST, Directions.SOUTH, Directions.WEST);
            }
        }
    }

    // -----------------
    // CONDITION METHODS
    // -----------------

    private boolean canLeave() {
        return actualRow == startingRow && actualCol == startingCol;
    }

    private boolean canTake() {
        return cave.getSquare(actualRow, actualCol).hasTreasure();
    }

    private boolean shouldShoot() {
        if (arrows <= 0) return false;

        for (Directions direction : Directions.values()) {
            Square[] squares = getSquaresInDirection(direction);
            for (Square square : squares) {
                if (square != null && square.getStatus() == SquareStatus.MONSTER) {
                    return true;
                }
            }
        }
        return false;
    }

    // --------------
    // ACTION METHODS
    // --------------

    /**
     * Allows the player to leave the cave if they are at the starting position.
     */
    private void leaveCave() {
        updateSquareStatus(actualRow, actualCol, SquareStatus.CLEAN);
        leftCave = true;
        System.out.println("Player has left the cave.");
    }

    /**
     * The player shoots an arrow in the direction it's looking.
     * The arrow won't stop until it kills a monster or collides with a wall.
     */
    private void shoot(Directions direction) {
        arrows--;
        System.out.println("Shooting an arrow to the " + direction);

        byte[] delta = getDirectionDelta(direction);
        byte newRow = (byte) (actualRow + delta[0]);
        byte newCol = (byte) (actualCol + delta[1]);

        while (cave.isWithinBounds(newRow, newCol)) {
            Square caveSquare = cave.getSquare(newRow, newCol);
            if (caveSquare.getStatus() == SquareStatus.MONSTER) {
                System.out.println(PerceptionType.GROAN + ": Monster defeated!");
                updateSquareStatus(newRow, newCol, SquareStatus.CLEAN);
                updateNeighborPerceptions(newRow, newCol);
                return;
            }
            newRow += delta[0];
            newCol += delta[1];
        }

        System.out.println(PerceptionType.BANG + ": Arrow hit a wall!");
    }

    /**
     * The player picks up the treasure.
     */
    private void take() {
        treasureFound = true;
        cave.getSquare(actualRow, actualCol).setHasTreasure(false);
        map.getSquare(actualRow, actualCol).setHasTreasure(false);
        updateNeighborPerceptions(actualRow, actualCol);
        System.out.println("Treasure collected!");
    }

    private void movement(Directions... preferences) {
        for (Directions direction : preferences) {
            if (isSafe(direction)) {
                moveInDirection(direction);
                return;
            }
        }
    }

    private void movementWithPriorities(Directions... preferences) {
        for (Directions direction : preferences) {
            if (isSafe(direction) && notHasVisited(direction)) {
                moveInDirection(direction);
                return;
            }
        }
        movement(preferences);
    }

    // ----------------------
    // HELPER METHODS - SHOOT
    // ----------------------

    private Directions getMonsterDirection() {
        for (Directions direction : Directions.values()) {
            Square[] squares = getSquaresInDirection(direction);
            for (Square square : squares) {
                if (square != null && square.getStatus() == SquareStatus.MONSTER) {
                    return direction;
                }
            }
        }
        // Should not occur due to shouldShoot
        return null;
    }

    private Square[] getSquaresInDirection(Directions direction) {
        List<Square> squaresInDirection = new ArrayList<>();
        byte[] delta = getDirectionDelta(direction);
        byte newRow = (byte) (actualRow + delta[0]);
        byte newCol = (byte) (actualCol + delta[1]);

        while (map.isWithinBounds(newRow, newCol)) {
            Square caveSquare = map.getSquare(newRow, newCol);
            squaresInDirection.add(caveSquare);
            newRow += delta[0];
            newCol += delta[1];
        }

        return squaresInDirection.toArray(new Square[0]);
    }

    // -------------------------
    // HELPER METHODS - MOVEMENT
    // -------------------------

    private void move(byte nextRow, byte nextCol) {
        updateSquareStatus(actualRow, actualCol, SquareStatus.CLEAN);
        updateSquareStatus(nextRow, nextCol, SquareStatus.PLAYER);

        actualRow = nextRow;
        actualCol = nextCol;
    }

    private void moveInDirection(Directions direction) {
        byte[] delta = getDirectionDelta(direction);
        move((byte) (actualRow + delta[0]), (byte) (actualCol + delta[1]));
    }

    private boolean isPositionSafe(byte row, byte col) {
        if (map.isWithinBounds(row, col)) {
            SquareStatus status = map.getSquare(row, col).getStatus();
            Perceptions perceptions = map.getSquare(actualRow, actualCol).getPerceptions();
            return (status == SquareStatus.TREASURE || status == SquareStatus.PLAYER || status == SquareStatus.CLEAN ||
                    (status == SquareStatus.UNKNOWN && !perceptions.getPerception(PerceptionType.STENCH) && !perceptions.getPerception(PerceptionType.BREEZE)));
        } else {
            return false;
        }
    }

    private boolean isSafe(Directions direction) {
        byte[] delta = getDirectionDelta(direction);
        byte newRow = (byte) (actualRow + delta[0]);
        byte newCol = (byte) (actualCol + delta[1]);
        return isPositionSafe(newRow, newCol);
    }

    private boolean notHasVisited(Directions direction) {
        byte[] delta = getDirectionDelta(direction);
        byte newRow = (byte) (actualRow + delta[0]);
        byte newCol = (byte) (actualCol + delta[1]);
        if (map.isWithinBounds(newRow, newCol)) {
            return map.getSquare(newRow, newCol).notVisited();
        }
        return true;

    }

    // -----------------------
    // HELPER METHODS - STATUS
    // -----------------------

    /**
     * Marks all unknown neighbors as clean.
     */
    private void markNeighborsClean(Square[] neighbors) {
        for (Square neighbor : neighbors) {
            if (neighbor != null && neighbor.getStatus() == SquareStatus.UNKNOWN) {
                neighbor.setStatus(SquareStatus.CLEAN);
            }
        }
    }

    public Object[] getUnknownNeighbors(Square[] neighbors) {
        int unknownNeighborsCount = 0;
        Square unknownNeighbor = null;
        for (Square neighbor : neighbors) {
            if (neighbor != null && neighbor.getStatus() == SquareStatus.UNKNOWN) {
                unknownNeighborsCount++;
                unknownNeighbor = neighbor;
            }
        }
        return new Object[]{unknownNeighborsCount, unknownNeighbor};
    }

    private void updateUnknownNeighborStatus(Square unknownNeighbor, Square[] neighbors, Perceptions currentPerceptions) {
        Perceptions inferredPerceptions = new Perceptions();
        for (Square neighbor : neighbors) {
            if (neighbor != null) {
                PerceptionType perceptionType = mapStatusToPerception(neighbor.getStatus());
                if (perceptionType != null) {
                    inferredPerceptions.setPerception(perceptionType, true);
                }
            }
        }
        for (PerceptionType perceptionType : PerceptionType.values()) {
            if (currentPerceptions != null &&
                    currentPerceptions.getPerception(perceptionType) != inferredPerceptions.getPerception(perceptionType)) {
                unknownNeighbor.setStatus(mapPerceptionToStatus(perceptionType));
            }
        }
    }

    /**
     * Infers the status of the current square based on its neighbors.
     */
    private void inferSquareStatus(Square square, Square[] neighbors) {
        byte[] perceptionCounter = new byte[PerceptionType.values().length];
        byte neighborsWithPerceptionsCounter = 0;

        for (Square neighbor : neighbors) {
            if (neighbor == null || neighbor.getPerceptions() == null) continue;
            neighborsWithPerceptionsCounter++;

            for (PerceptionType perceptionType : PerceptionType.values()) {
                if (neighbor.getPerceptions().getPerception(perceptionType)) {
                    perceptionCounter[perceptionType.ordinal()]++;
                }
            }
        }

        if (neighborsWithPerceptionsCounter >= 2) {
            boolean statusSet = false;
            for (int i = 0; i < perceptionCounter.length; i++) {
                if (perceptionCounter[i] >= 2) {
                    square.setStatus(SquareStatus.values()[i]);
                    statusSet = true;
                    break;
                }
            }
            if (!statusSet) {
                square.setStatus(SquareStatus.CLEAN);
            }
        }
    }

    private void updateSquareStatus(byte row, byte col, SquareStatus status) {
        cave.getSquare(row, col).setStatus(status);
        map.getSquare(row, col).setStatus(status);
    }

    private void updateNeighborPerceptions(byte row, byte col) {
        cave.updateNeighborPerceptions(row, col);
        map.updateNeighborPerceptions(row, col);
    }

    // --------------
    // HELPER METHODS
    // --------------

    public boolean hasFinished() {
        return treasureFound && leftCave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return startingRow == player.startingRow && startingCol == player.startingCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingRow, startingCol);
    }

}
