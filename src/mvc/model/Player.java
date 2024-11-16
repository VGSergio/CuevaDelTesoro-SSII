package mvc.model;

import mvc.model.maze.MazeModel;
import mvc.model.maze.Square;

import static mvc.model.Global.Status_Constants;

public class Player {

    private static final byte NUM_DIRECTIONS = 4;
    private final byte STARTING_ROW;
    private final byte STARTING_COL;
    private final boolean leftCave;
    private MazeModel maze;
    private MazeModel map;
    private byte actualRow;
    private byte actualCol;
    private boolean treasureFound;
    private byte direction = Direction.UP;

    public Player(byte row, byte column) {
        this.STARTING_ROW = row;
        this.STARTING_COL = column;
        this.actualRow = STARTING_ROW;
        this.actualCol = STARTING_COL;

        this.treasureFound = false;
        this.leftCave = false;
    }

    public void setMaze(MazeModel maze) {
        this.maze = maze;
        map = new MazeModel(maze.getMazeSide(), Status_Constants.UNKNOWN);
        map.setSquaresStatus(Global.Status_Constants.UNKNOWN);
    }

    public void exploreMaze() {
        // Find treasure
        if (!treasureFound) {
            if (!map.getSquare(actualRow, actualCol).isVisited()) {
                getPerceptions();
            }
            updateKnowledge();
            makeDecision();
        }

        // return to the starting position to exit the maze
    }

    private void getPerceptions() {
        Square actualSquareMaze = maze.getSquare(actualRow, actualCol);

        // Update our map with the maze perceptions
        Square actualSquareMap = map.getSquare(actualRow, actualCol);
        actualSquareMap.setPerceptions(actualSquareMaze.getPerceptions());

        actualSquareMap.setVisited(true);
    }

    private void makeDecision() {
        advance();

        // Update view to show changes
//        controller.notify(Events_Constants.MAZE_UPDATED);
    }

    private void updateKnowledge() {

    }

    /**
     * The player advances one position forward
     */
    private void advance() {
        byte nextRow = (byte) (actualRow + switch (direction) {
            case Direction.UP -> -1;
            case Direction.DOWN -> +1;
            default -> 0;
        });
        byte nextCol = (byte) (actualCol + switch (direction) {
            case Direction.RIGHT -> +1;
            case Direction.LEFT -> -1;
            default -> 0;
        });

        if (!maze.isWithinBounds(nextRow, nextCol)) {
            System.out.println("Player collided with a wall");
            treasureFound = true;
            return;
        }

        Square actualSquareMaze = maze.getSquare(actualRow, actualCol);
        Square actualSquareMap = map.getSquare(actualRow, actualCol);
        Square nextSquareMaze = maze.getSquare(nextRow, nextCol);
        Square nextSquareMap = map.getSquare(nextRow, nextCol);

        byte nextStatus = nextSquareMaze.getStatus();
        switch (nextStatus) {
            case Status_Constants.MONSTER -> {
                System.out.println("Player encountered a monster");
            }
            case Status_Constants.HOLE -> {
                System.out.println("Player fell through a hole");
            }
            case Status_Constants.TREASURE -> {
                System.out.println("Player found a treasure");
            }
            case Status_Constants.CLEAN -> {
                System.out.println("Player moved to a new position");
            }
        }

        actualRow = nextRow;
        actualCol = nextCol;

        actualSquareMaze.setStatus(Status_Constants.CLEAN);
        actualSquareMap.setStatus(Status_Constants.CLEAN);

        nextSquareMaze.setStatus(Status_Constants.PLAYER);
        nextSquareMap.setStatus(Status_Constants.PLAYER);
    }

    /**
     * The player makes a 90º right turn / clockwise
     */
    private void turnRight() {
        direction = (byte) ((direction + 1) % NUM_DIRECTIONS);
    }

    /**
     * The player makes a 90º left turn / counterclockwise
     */
    private void turnLeft() {
        direction = (byte) ((direction + NUM_DIRECTIONS - 1) % NUM_DIRECTIONS);
    }

    /**
     * The player picks up an item on its position, if there's one.
     */
    private void take() {
        byte status = maze.getSquare(actualRow, actualCol).getStatus();
        if (status == Status_Constants.TREASURE) {
            System.out.println("Player takes the treasure");
        }
    }

    /**
     * The player shoots an arrow in the direction it's looking.
     * The arrow won't stop until it kills a monster or collides with a wall.
     */
    private void shoot() {

    }

    /**
     * The player leaves the cave.
     * Only possible if the player is on its initial position.
     */
    private void jump() {

    }

    /**
     * The player dies on entering a room with an alive monster or a hole.
     */
    private void die() {

    }

    public boolean hasFinished() {
        return treasureFound && leftCave;
    }

    private static class Direction {
        private static final byte UP = 0;
        private static final byte RIGHT = 1;
        private static final byte DOWN = 2;
        private static final byte LEFT = 3;
    }
}
