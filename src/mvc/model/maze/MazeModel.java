package mvc.model.maze;

import mvc.model.Perceptions;
import mvc.model.Player;

import static mvc.model.Global.*;

public class MazeModel {

    private final SquareStatus defaultSquareStatus;
    private Square[] squares;
    private byte mazeSide;
    private byte amountOfMonsters;
    private byte amountOfTreasures;
    private byte amountOfPlayers;

    private Player player;

    public MazeModel(byte mazeSide, SquareStatus defaultSquareStatus) {
        this.defaultSquareStatus = defaultSquareStatus;
        setMazeSide(mazeSide);
    }

    /**
     * Initializes the maze structure and resets item counts.
     */
    private void initializeMaze() {
        int totalSquares = mazeSide * mazeSide;
        this.squares = new Square[totalSquares];
        for (int i = 0; i < totalSquares; i++) {
            this.squares[i] = new Square(defaultSquareStatus);
        }

        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
    }

    /**
     * Initializes the player in the maze.
     */
    public void initializePlayer() {
        // The maze always has a player on the bottom left position
        byte row = (byte) (mazeSide - 1);
        byte column = (byte) (0);

        squares[getSquarePositionInMaze(row, column, mazeSide)].setStatus(SquareStatus.PLAYER);
        this.amountOfPlayers = 1;

        player = new Player(row, column);
        player.setMaze(this);
    }

    /**
     * Returns the squares array.
     */
    public Square[] getSquares() {
        return squares;
    }

    /**
     * Retrieves a specific square based on row and column.
     */
    public Square getSquare(byte row, byte column) {
        return squares[getSquarePositionInMaze(row, column, mazeSide)];
    }

    public void setSquaresStatus(SquareStatus status) {
        for (Square square : squares) {
            square.setStatus(status);
        }
    }

    public byte getMazeSide() {
        return mazeSide;
    }

    /**
     * Sets the maze side and initializes the maze structure.
     *
     * @param mazeSide The side length of the maze.
     */
    public void setMazeSide(byte mazeSide) {
        this.mazeSide = mazeSide;
        initializeMaze();
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

    /**
     * Updates perceptions for a specific square by position index.
     */
    public void updatePerceptions(int position) {
        updatePerceptions((byte) (position / mazeSide), (byte) (position % mazeSide));
    }

    /**
     * Updates perceptions for a specific square by row and column.
     */
    public void updatePerceptions(byte row, byte column) {
        Square square = getSquare(row, column);
        Perceptions perceptions = new Perceptions();

        // Calculate perceptions based on neighbors
        for (int[] delta : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            byte neighborRow = (byte) (row + delta[0]);
            byte neighborCol = (byte) (column + delta[1]);

            if (isWithinBounds(neighborRow, neighborCol)) {
                SquareStatus status = getSquare(neighborRow, neighborCol).getStatus();
                PerceptionType perceptionType = squareStatusToPerceptionType(status);
                if (perceptionType != null) {
                    perceptions.setPerception(perceptionType, true);
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

    public void exploreMaze() {
        player.exploreMaze();
    }

    public boolean isMazeExplored() {
        return player.hasFinished();
    }

    public boolean isWithinBounds(byte row, byte column) {
        return row >= 0 && row < mazeSide && column >= 0 && column < mazeSide;
    }
}
