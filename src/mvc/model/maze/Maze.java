package mvc.model.maze;

import mvc.model.Perceptions;

import static mvc.model.Global.Perception_Constants;
import static mvc.model.Global.getSquarePositionInMaze;

public class Maze {

    private Square[] squares;

    private byte mazeSide;
    private byte amountOfMonsters;
    private byte amountOfTreasures;
    private byte amountOfPlayers;

    public Maze(byte mazeSide) {
        setMazeSide(mazeSide);
    }

    private void initializeMaze() {
        int totalSquares = mazeSide * mazeSide;
        this.squares = new Square[totalSquares];
        for (int i = 0; i < totalSquares; i++) {
            this.squares[i] = new Square();
        }

        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
        this.amountOfPlayers = 0;
    }

    public Square[] getSquares() {
        return squares.clone();
    }

    public Square getSquare(byte row, byte column) {
        return squares[getSquarePositionInMaze(row, column, mazeSide)];
    }

    public byte[] getSquaresStatus() {
        byte[] status = new byte[squares.length];
        for (int i = 0; i < status.length; i++) {
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

    public int getMazeLength() {
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

    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    public void adjustAmountOfPlayers(int delta) {
        this.amountOfPlayers += (byte) delta;
    }

    public void updatePerceptions(int position) {
        updatePerceptions(squares[position], (byte) (position / mazeSide), (byte) (position % mazeSide));
    }

    public void updatePerceptions(byte row, byte column) {
        updatePerceptions(squares[getSquarePositionInMaze(row, column, mazeSide)], row, column);
    }

    private void updatePerceptions(Square square, byte row, byte column) {
        Perceptions perceptions = new Perceptions();

        // Retrieve neighbors and calculate perceptions
        for (int[] delta : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            byte neighborRow = (byte) (row + delta[0]);
            byte neighborCol = (byte) (column + delta[1]);

            if (neighborRow >= 0 && neighborRow < mazeSide && neighborCol >= 0 && neighborCol < mazeSide) {
                Square neighbor = getSquare(neighborRow, neighborCol);
                switch (neighbor.getStatus()) {
                    case Perception_Constants.MONSTER -> perceptions.setMonsterPerception(true);
                    case Perception_Constants.HOLE -> perceptions.setHolePerception(true);
                    case Perception_Constants.TREASURE -> perceptions.setTreasurePerception(true);
                    case Perception_Constants.WALL ->
                            perceptions.setWallPerception(true); // Typically, walls shouldn't trigger updates
                    case Perception_Constants.CLEAN, Perception_Constants.PLAYER -> {
                    } // No perception for these
                    default -> throw new IllegalStateException("Unexpected status: " + square.getStatus());
                }
            }
        }

        square.setPerceptions(perceptions);
    }

    public void updateAllPerceptions() {
        for (int i = 0; i < squares.length; i++) {
            updatePerceptions(i);
        }
    }

}
