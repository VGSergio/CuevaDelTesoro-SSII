package mvc.model;

import mvc.model.maze.MazeModel;
import mvc.model.maze.Square;

import static mvc.model.Global.*;

public class Player {

    private final byte startingRow;
    private final byte startingCol;
    private boolean leftCave;
    private MazeModel maze;
    private MazeModel map;
    private byte actualRow;
    private byte actualCol;
    private boolean treasureFound;

    public Player(byte row, byte column) {
        this.startingRow = row;
        this.startingCol = column;
        this.actualRow = startingRow;
        this.actualCol = startingCol;

        this.treasureFound = false;
        this.leftCave = false;
    }

    public void linkMaze(MazeModel maze) {
        this.maze = maze;
        initializeMap();
    }

    private void initializeMap() {
        map = new MazeModel(maze.getMazeSide(), SquareStatus.UNKNOWN);
        map.setSquaresStatus(SquareStatus.UNKNOWN);
        map.getSquare(startingRow, startingCol).setStatus(SquareStatus.PLAYER);
    }

    public void exploreMaze() {
        if (map.getSquare(actualRow, actualCol).notVisited()) {
            getPerceptions();
        }
        updateKnowledge();
        makeDecision();
    }

    private void getPerceptions() {
        // Get the real cave position
        Square actualSquareMaze = maze.getSquare(actualRow, actualCol);

        // Update our map with the maze perceptions
        Square actualSquareMap = map.getSquare(actualRow, actualCol);
        actualSquareMap.setPerceptions(actualSquareMaze.getPerceptions());

        actualSquareMap.setVisited(true);
    }

    private void updateKnowledge() {
        Square[] squares = this.map.getSquares();
        byte mazeSide = this.map.getMazeSide();
        Perceptions[] perceptions = map.getPerceptions();

        for (byte row = 0; row < mazeSide; row++) {
            for (byte col = 0; col < mazeSide; col++) {
                int updatingPosition = getSquarePositionInMaze(row, col, mazeSide);
                Square updatingSquare = squares[updatingPosition];
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
        // We have found the treasure.
        if (treasureFound) {
            // We are in our starting position.
            if (actualRow == startingRow && actualCol == startingCol) {
                leaveCave();
            } else {
                // Returning to the starting position with preference WEST > SOUTH > EAST > NORTH
                if (isWestSafe() && notHasVisited(getWest(), actualCol)) {
                    moveWest();
                } else if (isSouthSafe() && notHasVisited(getSouth(), actualCol)) {
                    moveSouth();
                } else if (isEastSafe() && notHasVisited(actualRow, getEast())) {
                    moveEast();
                } else if (isNorthSafe() && notHasVisited(getNorth(), actualCol)) {
                    moveNorth();
                } else {
                    // If all preferred directions are visited, fall back to the original safe preference
                    if (isWestSafe()) moveWest();
                    else if (isSouthSafe()) moveSouth();
                    else if (isEastSafe()) moveEast();
                    else if (isNorthSafe()) moveNorth();
                }
            }
        } else {
            // Treasure hunting with preference NORTH > EAST > SOUTH > WEST
            if (isNorthSafe() && notHasVisited(getNorth(), actualCol)) {
                moveNorth();
            } else if (isEastSafe() && notHasVisited(actualRow, getEast())) {
                moveEast();
            } else if (isSouthSafe() && notHasVisited(getSouth(), actualCol)) {
                moveSouth();
            } else if (isWestSafe() && notHasVisited(actualRow, getWest())) {
                moveWest();
            } else {
                // If all preferred directions are visited, fall back to the original safe preference
                if (isNorthSafe()) moveNorth();
                else if (isEastSafe()) moveEast();
                else if (isSouthSafe()) moveSouth();
                else if (isWestSafe()) moveWest();
            }
        }
    }


    /// ////////////
    /// ACTIONS ///
    /// ////////////

    private void move(byte nextRow, byte nextCol) {
        if (map.isWithinBounds(nextRow, nextCol)) {
            maze.getSquare(actualRow, actualCol).setStatus(SquareStatus.CLEAN);
            map.getSquare(actualRow, actualCol).setStatus(SquareStatus.CLEAN);

            maze.getSquare(nextRow, nextCol).setStatus(SquareStatus.PLAYER);
            map.getSquare(nextRow, nextCol).setStatus(SquareStatus.PLAYER);

            actualRow = nextRow;
            actualCol = nextCol;
        } else {
            System.out.println(PerceptionType.BANG);
        }
    }

    private void moveNorth() {
        move(getNorth(), actualCol);
    }

    private void moveEast() {
        move(actualRow, getEast());
    }

    private void moveSouth() {
        move(getSouth(), actualCol);
    }

    private void moveWest() {
        move(actualRow, getWest());
    }

    private void leaveCave() {
        // Update actual cave to show that the player has successfully left the cave.
        maze.getSquare(actualRow, actualCol).setStatus(SquareStatus.CLEAN);
        // Update the boolean to allow hasFinished to return true.
        leftCave = true;
    }

    /**
     * The player picks up an item on its position, if there's one.
     */
    private void take() {
        // TODO
    }

    /**
     * The player shoots an arrow in the direction it's looking.
     * The arrow won't stop until it kills a monster or collides with a wall.
     */
    private void shoot() {
        //TODO
    }

    /// ////////////
    /// HELPERS ///
    /// ////////////

    private boolean isPositionSafe(byte row, byte col) {
        if (map.isWithinBounds(row, col)) {
            SquareStatus status = map.getSquare(row, col).getStatus();
            return (status == SquareStatus.TREASURE || status == SquareStatus.PLAYER || status == SquareStatus.CLEAN);
        } else {
            return false;
        }
    }

    private boolean isNorthSafe() {
        return isPositionSafe(getNorth(), actualCol);
    }

    private boolean isEastSafe() {
        return isPositionSafe(actualRow, getEast());
    }

    private boolean isSouthSafe() {
        return isPositionSafe(getSouth(), actualCol);
    }

    private boolean isWestSafe() {
        return isPositionSafe(actualRow, getWest());
    }

    private byte getNorth() {
        return (byte) (actualRow - 1);
    }

    private byte getEast() {
        return (byte) (actualCol + 1);
    }

    private byte getSouth() {
        return (byte) (actualRow + 1);
    }

    private byte getWest() {
        return (byte) (actualCol - 1);
    }

    private byte[] getPerceptionsCount(Square square) {
        int[][] directions = {
                {-1, 0}, // up
                {1, 0},  // down
                {0, -1}, // left
                {0, 1}   // right
        };

        byte[] counts = new byte[PerceptionType.values().length];
        boolean neighbourHasPerceptions = false;

        for (int[] direction : directions) {
            byte newRow = (byte) (actualRow + direction[0]);
            byte newCol = (byte) (actualCol + direction[1]);

            if (map.isWithinBounds(newRow, newCol)) {
                Perceptions neighborPerceptions = map.getSquare(newRow, newCol).getPerceptions();
                if (neighborPerceptions != null) {
                    neighbourHasPerceptions = true;
                    for (PerceptionType perception : PerceptionType.values()) {
                        if (neighborPerceptions.getPerception(perception.ordinal())) {
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

        for (Directions direction : Directions.values()) {
            int[] directionDelta = directions[direction.ordinal()];

            byte newRow = (byte) (row + directionDelta[0]);
            byte newCol = (byte) (col + directionDelta[1]);

            if (map.isWithinBounds(newRow, newCol)) {
                neighbours[direction.ordinal()] = map.getSquare(newRow, newCol);
            } else {
                neighbours[direction.ordinal()] = null;
            }
        }

        return neighbours;
    }

    public boolean hasFinished() {
        return treasureFound && leftCave;
    }

    private boolean notHasVisited(byte row, byte col) {
        if (map.isWithinBounds(row, col)) {
            return map.getSquare(row, col).notVisited();
        }
        return true; // Out of bounds squares are considered visited
    }


    // Define the relative directions (up, down, left, right)
    private final int[][] directions = {
            {-1, 0}, // NORTH
            {0, 1},  // EAST
            {1, 0},  // SOUTH
            {0, -1}, // WEST
    };

    private enum Directions {
        NORTH, EAST, SOUTH, WEST
    }
}
