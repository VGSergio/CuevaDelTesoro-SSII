package mvc.model;

import mvc.model.maze.Maze;
import mvc.model.maze.Square;

import static mvc.model.Global.*;

public class Player {

    private final Maze MAZE;
    private Knowledge[] BC;

    private int arrows;

    public Player(Maze maze, byte row, byte column) {
        this.MAZE = maze;
        this.arrows = maze.getAmountOfMonsters();

        this.BC = new Knowledge[maze.getSquares().length];
        for (int i = 0; i < BC.length; i++) {
            this.BC[i] = new Knowledge();
        }
        BC[row * maze.getMazeSide() + column].setStatus(CLEAN); // The starting position is safe
        updatePerceptions(row, column);
    }

    private void updatePerceptions(byte row, byte column) {
        byte mazeSide = MAZE.getMazeSide();
        int mazeLength = mazeSide * mazeSide;
        Square[] squares = MAZE.getSquares();

        int[] positions = {
                (row - 1) * mazeSide + column, // up
                (row + 1) * mazeSide + column, // down
                row * mazeSide + column - 1,   // left
                row * mazeSide + column + 1    // right
        };

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

        BC[row * mazeSide + column] = perceptions;
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

}
