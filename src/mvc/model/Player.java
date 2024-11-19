package mvc.model;

import mvc.model.cave.Cave;
import mvc.model.cave.Map;
import mvc.model.cave.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mvc.model.Global.*;

public class Player {

    private final byte startingRow;
    private final byte startingCol;
    private boolean treasureFound;
    private boolean leftCave;
    private Cave cave;
    private Map map;
    private byte actualRow;
    private byte actualCol;
    private int arrows;

    public Player(byte row, byte column) {
        startingRow = row;
        startingCol = column;
        actualRow = startingRow;
        actualCol = startingCol;

        treasureFound = false;
        leftCave = false;
    }

    public void linkCave(Cave cave) {
        this.cave = cave;
        arrows = cave.getAmountOfMonsters();
        initializeMap();
    }

    private void initializeMap() {
        map = new Map(cave.getCaveSide());
        map.getSquare(startingRow, startingCol).setStatus(SquareStatus.PLAYER);
    }

    public void exploreCave() {
        getPerceptions();
        updateKnowledge();
        makeDecision();
    }

    private void getPerceptions() {
        // Get the real cave position
        Square actualSquareCave = cave.getSquare(actualRow, actualCol);

        // Update our map with the cave perceptions
        Square actualSquareMap = map.getSquare(actualRow, actualCol);
        actualSquareMap.setVisited(true);
        Perceptions perceptions = actualSquareCave.getPerceptions();
        actualSquareMap.setPerceptions(perceptions);
    }

    private void updateKnowledge() {
        byte caveSide = map.getCaveSide();
        Perceptions[] perceptions = map.getPerceptions();

        for (byte row = 0; row < caveSide; row++) {
            for (byte col = 0; col < caveSide; col++) {
                Square updatingSquare = map.getSquare(row, col);
                int updatingPosition = map.getSquarePositionInCave(row, col);
                Perceptions updatingPerceptions = perceptions[updatingPosition];
                Square[] neighbours = getNeighbours(row, col);

                if (updatingPerceptions != null && updatingPerceptions.isClean()) {
                    for (Square neighbour : neighbours) {
                        if (neighbour != null && neighbour.getStatus() == SquareStatus.UNKNOWN) {
                            neighbour.setStatus(SquareStatus.CLEAN);
                        }
                    }
                }

                byte numOfUnknownNeighbours = 0;
                for (Square neighbour : neighbours) {
                    if (neighbour != null && neighbour.getStatus() == SquareStatus.UNKNOWN) {
                        numOfUnknownNeighbours++;
                    }
                }
                if (numOfUnknownNeighbours == 1) {
                    Square unknownNeighbour = null;
                    for (Square neighbour : neighbours) {
                        if (neighbour != null && neighbour.getStatus() == SquareStatus.UNKNOWN) {
                            unknownNeighbour = neighbour;
                        }
                    }
                    if (unknownNeighbour != null) {
                        Perceptions perceptionsPlaced = new Perceptions();
                        for (Square neighbour : neighbours) {
                            if (neighbour != null) {
                                PerceptionType perceptionStatus = mapStatusToPerception(neighbour.getStatus());
                                if (perceptionStatus != null) {
                                    perceptionsPlaced.setPerception(perceptionStatus, true);
                                }
                            }
                        }
                        for (PerceptionType perceptionType : PerceptionType.values()) {
                            if (updatingPerceptions != null && updatingPerceptions.getPerception(perceptionType) != perceptionsPlaced.getPerception(perceptionType)) {
                                unknownNeighbour.setStatus(mapPerceptionToStatus(perceptionType));
                            }
                        }
                    }
                }


                // infer this square status
                if (updatingSquare.getStatus() != SquareStatus.UNKNOWN) {
                    continue;
                }
                // If our neighbours perceptions differ we are clean.
                for (PerceptionType perceptionType : PerceptionType.values()) {
                    SquareStatus perceptionStatus = mapPerceptionToStatus(perceptionType);
                    if (perceptionStatus == null) {
                        continue;
                    }
                    byte validPerceptions = 0;
                    byte count = 0;
                    for (Square neighbour : neighbours) {
                        if (neighbour == null) {
                            continue;
                        }
                        Perceptions neighbourPerceptions = neighbour.getPerceptions();
                        if (neighbourPerceptions == null) {
                            continue;
                        }
                        validPerceptions++;
                        if (neighbourPerceptions.getPerception(perceptionType)) {
                            count++;
                        }
                    }
                    if (validPerceptions > 1) {
                        updatingSquare.setStatus(count == validPerceptions ? perceptionStatus : SquareStatus.CLEAN);
                    }
                }
            }
        }
    }

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

    /// ////////////
    /// CONDITIONS /
    /// ////////////

    private boolean canLeave() {
        return actualRow == startingRow && actualCol == startingCol;
    }

    private boolean canTake() {
        return cave.getSquare(actualRow, actualCol).hasTreasure();
    }

    private boolean shouldShoot() {
        if (arrows <= 0) {
            return false;
        }
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

    /// ////////////
    /// ACTIONS ///
    /// ////////////

    private void moveInDirection(Directions direction) {
        byte[] delta = getDirectionDelta(direction);
        move((byte) (actualRow + delta[0]), (byte) (actualCol + delta[1]));
    }

    private void leaveCave() {
        updateSquareStatus(actualRow, actualCol, SquareStatus.CLEAN);

        // Update the boolean to allow hasFinished to return true.
        leftCave = true;
    }

    /**
     * The player picks up the treasure.
     */
    private void take() {
        treasureFound = true;
        System.out.println("The player takes the treasure");
        cave.getSquare(actualRow, actualCol).setHasTreasure(false);
        map.getSquare(actualRow, actualCol).setHasTreasure(false);
        updateNeighborPerceptions(actualRow, actualCol);
    }

    /**
     * The player shoots an arrow in the direction it's looking.
     * The arrow won't stop until it kills a monster or collides with a wall.
     */
    private void shoot(Directions direction) {
        arrows--;
        System.out.println("The player shoots an arrow to the " + direction);

        boolean monsterHit = false;
        byte[] delta = getDirectionDelta(direction);
        byte newRow = (byte) (actualRow + delta[0]);
        byte newCol = (byte) (actualCol + delta[1]);

        while (cave.isWithinBounds(newRow, newCol)) {
            Square caveSquare = cave.getSquare(newRow, newCol);
            if (caveSquare.getStatus() == SquareStatus.MONSTER) {
                System.out.println(PerceptionType.GROAN);
                System.out.println("Arrow hit a monster!");
                updateSquareStatus(newRow, newCol, SquareStatus.CLEAN);
                updateNeighborPerceptions(newRow, newCol);
                monsterHit = true;
                break;
            }
            newRow += delta[0];
            newCol += delta[1];
        }

        if (!monsterHit) {
            // The arrow hits a wall // Should not happen due to shouldShoot
            System.out.println(PerceptionType.BANG);
            System.out.println("Arrow hit a wall.");
        }
    }

    /// ////////////
    /// HELPERS ///
    /// ////////////

    private void move(byte nextRow, byte nextCol) {
        updateSquareStatus(actualRow, actualCol, SquareStatus.CLEAN);
        updateSquareStatus(nextRow, nextCol, SquareStatus.PLAYER);

        actualRow = nextRow;
        actualCol = nextCol;
    }

    private void updateSquareStatus(byte row, byte col, SquareStatus status) {
        cave.getSquare(row, col).setStatus(status);
        map.getSquare(row, col).setStatus(status);
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

    private void movement(Directions... preferences) {
        for (Directions direction : preferences) {
            if (isSafe(direction)) {
                moveInDirection(direction);
                return;
            }
        }
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

    private Square[] getNeighbours(byte row, byte col) {
        Square[] neighbours = new Square[Directions.values().length];

        byte[][] neighboursRowsAndColumns = getNeighboursRowsAndColumns(row, col);

        for (Directions direction : Directions.values()) {
            byte[] neighbourRowAndColumn = neighboursRowsAndColumns[direction.ordinal()];
            neighbours[direction.ordinal()] = neighbourRowAndColumn == null ? null : map.getSquare(neighbourRowAndColumn[0], neighbourRowAndColumn[1]);
        }

        return neighbours;
    }

    private byte[][] getNeighboursRowsAndColumns(byte row, byte col) {
        byte[][] neighboursRowsAndColumns = new byte[Directions.values().length][2];
        for (Directions direction : Directions.values()) {
            byte[] directionDelta = getDirectionDelta(direction);

            byte newRow = (byte) (row + directionDelta[0]);
            byte newCol = (byte) (col + directionDelta[1]);

            if (map.isWithinBounds(newRow, newCol)) {
                neighboursRowsAndColumns[direction.ordinal()][0] = newRow;
                neighboursRowsAndColumns[direction.ordinal()][1] = newCol;
            } else {
                neighboursRowsAndColumns[direction.ordinal()] = null;
            }
        }
        return neighboursRowsAndColumns;
    }

    private void updateNeighborPerceptions(byte row, byte col) {
        byte[][] neighbors = getNeighboursRowsAndColumns(row, col);
        for (byte[] neighbor : neighbors) {
            if (neighbor != null) {
                cave.updatePerceptions(neighbor[0], neighbor[1]);
                map.updatePerceptions(neighbor[0], neighbor[1]);
            }
        }
    }

    public boolean hasFinished() {
        return treasureFound && leftCave;
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
