package mvc.model;

import mvc.model.maze.MazeModel;
import mvc.model.maze.Square;

import static mvc.model.Global.Perception_Constants;
import static mvc.model.Global.getSquarePositionInMaze;

public class Player {

    private final MazeModel MAZEModel;
    private final byte STARTING_ROW;
    private final byte STARTING_COL;
    private final Knowledge[] BC;
    private final byte actualRow;
    private final byte actualCol;
    private final boolean foundTreasure;
    private int arrows;

    public Player(MazeModel mazeModel, byte row, byte column) {
        // Maze
        this.MAZEModel = mazeModel;

        // Player data
        this.STARTING_ROW = row;
        this.STARTING_COL = column;
        this.actualRow = STARTING_ROW;
        this.actualCol = STARTING_COL;
        this.foundTreasure = false;
        this.arrows = mazeModel.getAmountOfMonsters();

        this.BC = new Knowledge[mazeModel.getMazeLength()];
        for (int i = 0; i < BC.length; i++) {
            this.BC[i] = new Knowledge();
        }
        BC[getSquarePositionInMaze(actualRow, actualCol, mazeModel.getMazeSide())].setStatus(Perception_Constants.CLEAN); // The starting position is safe
        updatePerceptions();
    }

    private static Knowledge getKnowledge(int[] positions, int mazeLength, Square[] squares) {
        Knowledge knowledge = new Knowledge();
        boolean monster = false;
        boolean hole = false;
        boolean treasure = false;

        for (int pos : positions) {
            if (pos < 0 || pos >= mazeLength) {
                continue;
            }

            byte status = squares[pos].getStatus();

            switch (status) {
                case Perception_Constants.MONSTER:
                    monster = true;
                    break;
                case Perception_Constants.HOLE:
                    hole = true;
                    break;
                case Perception_Constants.TREASURE:
                    treasure = true;
                    break;
                default:
                    break;
            }
        }

        Perceptions perceptions = knowledge.getPerceptions();

        perceptions.setMonsterPerception(monster);
        perceptions.setHolePerception(hole);
        perceptions.setHolePerception(treasure);
        return knowledge;
    }

    private void updatePerceptions() {
        byte mazeSide = MAZEModel.getMazeSide();
        int mazeLength = MAZEModel.getMazeLength();
        Square[] squares = MAZEModel.getSquares();

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

    public void exploreMaze() {
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

    public boolean hasFinished() {
        return foundTreasure && actualRow == STARTING_ROW && actualCol == STARTING_COL;
    }
}
