package mvc.model.maze;

import static mvc.model.Global.PLAYER;
import static mvc.model.Global.getSquarePositionInMaze;

public class Maze {

    private Square[] squares;

    private byte mazeSide;
    private byte amountOfMonsters;
    private byte amountOfTreasures;

    public Maze(byte mazeSide) {
        setMazeSide(mazeSide);
    }

    private void initializeMaze() {
        int totalSquares = mazeSide * mazeSide;
        this.squares = new Square[totalSquares];
        for (int i = 0; i < totalSquares; i++) {
            this.squares[i] = new Square();
        }

        // Set bottom left corner status to have a player "(1, 1)"
        squares[(mazeSide - 1) * mazeSide].setStatus(PLAYER);

        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
    }

    public Square[] getSquares() {
        return squares;
    }

    public Square getSquare(byte row, byte column) {
        return squares[getSquarePositionInMaze(row, column, mazeSide)];
    }

    public byte[] getSquaresStatus() {
        int totalSquares = getMazeLength();
        byte[] status = new byte[totalSquares];
        for (int i = 0; i < totalSquares; i++) {
            status[i] = squares[i].getStatus();
        }
        return status;
    }

    public byte getMazeSide() {
        return mazeSide;
    }

    public void setMazeSide(byte mazeSide) {
        this.mazeSide = mazeSide;
        initializeMaze();
    }

    public int getMazeLength(){
        return squares.length;
    }

    public byte getAmountOfMonsters() {
        return amountOfMonsters;
    }

    public void adjustAmountOfMonsters(int delta) {
        this.amountOfMonsters += (byte) delta;
    }

    public byte getAmountOfTreasures() {
        return amountOfTreasures;
    }

    public void adjustAmountOfTreasures(int delta) {
        this.amountOfTreasures += (byte) delta;
    }
}
