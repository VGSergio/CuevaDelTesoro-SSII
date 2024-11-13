package mvc.model;

import mvc.model.maze.Maze;
import mvc.model.maze.Square;

import static mvc.model.Global.*;

public class Player {

    private final Maze MAZE;
    private final byte STARTING_ROW;
    private final byte STARTING_COL;
    private final Knowledge[] BC;
    private final byte actualRow;
    private final byte actualCol;
    private final boolean foundTreasure;
    private int arrows;

    public Player(Maze maze, byte row, byte column) {
        // Maze
        this.MAZE = maze;

        // Player data
        this.STARTING_ROW = row;
        this.STARTING_COL = column;
        this.actualRow = STARTING_ROW;
        this.actualCol = STARTING_COL;
        this.foundTreasure = false;
        this.arrows = maze.getAmountOfMonsters();

        this.BC = new Knowledge[maze.getMazeLength()];
        for (int i = 0; i < BC.length; i++) {
            this.BC[i] = new Knowledge();
        }
        BC[getSquarePositionInMaze(actualRow, actualCol, maze.getMazeSide())].setStatus(CLEAN); // The starting position is safe
        updatePerceptions();
    }

    private static Knowledge getKnowledge(int[] positions, int mazeLength, Square[] squares) {
        Knowledge perceptions = new Knowledge();
        boolean stink = false;
        boolean wind = false;
        boolean radiance = false;

        for (int pos : positions) {
            if (pos < 0 || pos >= mazeLength) {
                continue;
            }

            byte status = squares[pos].getStatus();

            switch (status) {
                case MONSTER:
                    stink = true;
                    break;
                case HOLE:
                    wind = true;
                    break;
                case TREASURE:
                    radiance = true;
                    break;
                default:
                    break;
            }
        }

        perceptions.setStink(stink);
        perceptions.setWind(wind);
        perceptions.setRadiance(radiance);
        return perceptions;
    }

    private void updatePerceptions() {
        byte mazeSide = MAZE.getMazeSide();
        int mazeLength = MAZE.getMazeLength();
        Square[] squares = MAZE.getSquares();

        int playerPosition = getSquarePositionInMaze(actualRow, actualCol, mazeSide);
        int[] positions = {
                (actualRow - 1) * mazeSide + actualCol, // up
                (actualRow + 1) * mazeSide + actualCol, // down
                playerPosition - 1,                     // left
                playerPosition + 1                      // right
        };

        Knowledge perceptions = getKnowledge(positions, mazeLength, squares);

        BC[playerPosition] = perceptions;
    }

    private void throwArrow(String direction) {
        arrows--;
        switch (direction) {
            case "up":
                System.out.println("up");
                break;
            case "down":
                System.out.println("down");
                break;
            case "left":
                System.out.println("left");
                break;
            case "right":
                System.out.println("right");
                break;
            default:
                System.err.println("unknown direction");
                break;
        }

        // update knowledge
        // update maze
    }

    private void exploreMaze() {
        // find treasure
        while (!foundTreasure) {
            updatePerceptions();
            makeDecision();
            updateKnowledge();
        }
        // return to the starting position to exit the maze

    }

    private void makeDecision() {

    }

    private void updateKnowledge() {

    }

    private boolean hasFinished() {
        return foundTreasure && actualRow == STARTING_ROW && actualCol == STARTING_COL;
    }
}
