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
        Perceptions perceptions = actualSquareCave.getPerceptions();
        actualSquareMap.setPerceptions(perceptions);

        actualSquareMap.setVisited(true);

        // Update actualSquareMap neighbours perceptions counter
        Square[] neighbours = getNeighbours(actualRow, actualCol);
        for (Square neighbour : neighbours) {
            if (neighbour != null) {
                for (PerceptionType perception : PerceptionType.values()) {
                    if (perceptions.getPerception(perception)) {
                        neighbour.adjustPerceptionsCounter(perception, 1);
                    }
                }
            }
        }
    }

    private void updateKnowledge() {
        Square[] squares = map.getSquares();
        byte caveSide = map.getCaveSide();
        Perceptions[] perceptions = map.getPerceptions();

        for (byte row = 0; row < caveSide; row++) {
            for (byte col = 0; col < caveSide; col++) {
                int updatingPosition = map.getSquarePositionInCave(row, col);
                Perceptions updatingPerceptions = perceptions[updatingPosition];

                // Without perceptions there's no reasoning to do.
                if (updatingPerceptions == null) {
                    continue;
                }

                Square[] neighbours = getNeighbours(row, col);
                // All perceptions to false mean that the neighbours are clean.
                if (updatingPerceptions.isClean()) {
                    for (Square square : neighbours) {
                        if ((square != null) && (square.getStatus() == SquareStatus.UNKNOWN)) {
                            square.setStatus(SquareStatus.CLEAN);
                        }
                    }
                } else {
                    for (Square square : neighbours) {
                        if (square != null) {
                            byte[] perceptionsCountNeighbour = getPerceptionsCount(square);
                            if (perceptionsCountNeighbour != null) {
                                for (PerceptionType perception : PerceptionType.values()) {
                                    if (perceptionsCountNeighbour[perception.ordinal()] >= 2) {
                                        square.setStatus(mapPerceptionToStatus(perception));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void makeDecision() {
        if (treasureFound) {
            if (canLeave()) {
                leaveCave();
            } else if (canTake()) {
                take();
            } else if (shouldShoot()) {
                shoot(getMonsterDirection());
            } else {
                prioritizeMovement(Directions.WEST, Directions.SOUTH, Directions.EAST, Directions.NORTH);
            }
        } else {
            prioritizeMovement(Directions.NORTH, Directions.EAST, Directions.SOUTH, Directions.WEST);
        }
    }

    /// ////////////
    /// CONDITIONS /
    /// ////////////

    private boolean canLeave() {
        return actualRow == startingRow && actualCol == startingCol;
    }

    private boolean canTake() {
        return map.getSquare(actualRow, actualCol).getStatus() == SquareStatus.TREASURE;
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
        updateSquareStatus(actualRow, actualCol, SquareStatus.CLEAN);
        updateNeighborPerceptions(actualRow, actualCol);
    }

    /**
     * The player shoots an arrow in the direction it's looking.
     * The arrow won't stop until it kills a monster or collides with a wall.
     */
    private void shoot(Directions direction) {
        arrows--;

        byte[] delta = getDirectionDelta(direction);
        byte newRow = (byte) (actualRow + delta[0]);
        byte newCol = (byte) (actualCol + delta[1]);

        while (map.isWithinBounds(newRow, newCol)) {
            Square caveSquare = cave.getSquare(newRow, newCol);
            if (caveSquare.getStatus() == SquareStatus.MONSTER) {
                System.out.println("Arrow hit a monster!");
                System.out.println(PerceptionType.GROAN);
                caveSquare.setStatus(SquareStatus.CLEAN);
                updateNeighborPerceptions(newRow, newCol);
                break;
            }
            newRow += delta[0];
            newCol += delta[1];
        }
        // The arrow hits a wall // Should not happen due to shouldShoot
        System.out.println("Arrow hit a wall.");
        System.out.println(PerceptionType.BANG);
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

    private void prioritizeMovement(Directions... preferences) {
        for (Directions direction : preferences) {
            if (isSafe(direction) && notHasVisited(direction)) {
                moveInDirection(direction);
                return;
            }
        }
        // Fallback to safe moves if all are visited
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
            return (status == SquareStatus.TREASURE || status == SquareStatus.PLAYER || status == SquareStatus.CLEAN);
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

    private byte[] getPerceptionsCount(Square square) {
        byte[] counts = new byte[PerceptionType.values().length];
        boolean neighbourHasPerceptions = false;

        for (Directions direction : Directions.values()) {
            byte[] delta = getDirectionDelta(direction);
            byte newRow = (byte) (actualRow + delta[0]);
            byte newCol = (byte) (actualCol + delta[1]);

            if (map.isWithinBounds(newRow, newCol)) {
                Perceptions neighborPerceptions = map.getSquare(newRow, newCol).getPerceptions();
                if (neighborPerceptions != null) {
                    neighbourHasPerceptions = true;
                    for (PerceptionType perception : PerceptionType.values()) {
                        if (neighborPerceptions.getPerception(perception)) {
                            counts[perception.ordinal()]++;
                        }
                    }
                }
            }
        }
        return neighbourHasPerceptions ? counts : null;
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
        List<Square> squaresInDirection = new ArrayList<Square>();
        byte[] delta = getDirectionDelta(direction);
        byte newRow = (byte) (actualRow + delta[0]);
        byte newCol = (byte) (actualCol + delta[1]);

        while (map.isWithinBounds(newRow, newCol)) {
            Square caveSquare = cave.getSquare(newRow, newCol);
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
