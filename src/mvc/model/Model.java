package mvc.model;

import static mvc.model.Global.MIN_MAZE_SIDE;
import static mvc.model.Global.PLAYER;

public class Model {

    private byte mazeSide;
    private byte amountOfMonsters;
    private byte amountOfTreasures;
    private Square[] maze;

    public Model() {
        this.mazeSide = MIN_MAZE_SIDE;

        initializeMaze();
    }


    private void initializeMaze() {
        this.maze = new Square[mazeSide * mazeSide];
        for (int i = 0; i < maze.length; i++) {
            this.maze[i] = new Square();
        }

        // Set bottom left corner status to have a player "(1, 1)"
        maze[(mazeSide - 1) * mazeSide].setStatus(PLAYER);

        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
    }

    public byte getMazeSide() {
        return mazeSide;
    }

    public void setMazeSide(byte mazeSide) {
        this.mazeSide = mazeSide;
        initializeMaze();
    }

    public byte getAmountOfMonsters() {
        return amountOfMonsters;
    }

    public void decreaseAmountOfMonsters() {
        this.amountOfMonsters -= 1;
    }

    public void increaseAmountOfMonsters() {
        this.amountOfMonsters += 1;
    }

    public byte getAmountOfTreasures() {
        return amountOfTreasures;
    }

    public void decreaseAmountOfTreasures() {
        this.amountOfTreasures -= 1;
    }

    public void increaseAmountOTreasures() {
        this.amountOfTreasures += 1;
    }

    public Square[] getMaze() {
        return maze;
    }

    public byte[] getMazeSquares(){
        byte[] squares = new byte[maze.length];
        for (int i = 0; i < maze.length; i++) {
            squares[i] = maze[i].getStatus();
        }
        return squares;
    }

}
