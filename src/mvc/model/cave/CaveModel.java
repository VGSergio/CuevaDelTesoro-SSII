package mvc.model.cave;

import mvc.model.Perceptions;
import mvc.model.Player;

import java.util.Arrays;

import static mvc.model.Global.*;

public class CaveModel {

    private final SquareStatus defaultSquareStatus;
    private Square[] squares;
    private byte caveSide;
    private byte amountOfMonsters;
    private byte amountOfTreasures;
    private byte amountOfPlayers;

    private Player player;

    public CaveModel(byte caveSide, SquareStatus defaultSquareStatus) {
        this.defaultSquareStatus = defaultSquareStatus;
        setCaveSide(caveSide);
    }

    /**
     * Initializes the cave structure and resets item counts.
     */
    private void initializeCave() {
        int totalSquares = caveSide * caveSide;
        this.squares = new Square[totalSquares];
        for (int i = 0; i < totalSquares; i++) {
            this.squares[i] = new Square(defaultSquareStatus);
        }

        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
    }

    /**
     * Initializes the player in the cave.
     */
    public void initializePlayer() {
        // The cave always has a player on the bottom left position
        byte row = (byte) (caveSide - 1);
        byte column = (byte) (0);

        squares[getSquarePositionInCave(row, column, caveSide)].setStatus(SquareStatus.PLAYER);
        this.amountOfPlayers = 1;

        player = new Player(row, column);
        player.linkCave(this);
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
        return squares[getSquarePositionInCave(row, column, caveSide)];
    }

    public void setSquaresStatus(SquareStatus status) {
        for (Square square : squares) {
            square.setStatus(status);
        }
    }

    public byte getCaveSide() {
        return caveSide;
    }

    /**
     * Sets the cave side and initializes the cave structure.
     *
     * @param caveSide The side length of the cave.
     */
    public void setCaveSide(byte caveSide) {
        this.caveSide = caveSide;
        initializeCave();
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
        updatePerceptions((byte) (position / caveSide), (byte) (position % caveSide));
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
                PerceptionType perceptionType = mapStatusToPerception(status);
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

    public void exploreCave() {
        player.exploreCave();
    }

    public boolean isCaveExplored() {
        return player.hasFinished();
    }

    public boolean isWithinBounds(byte row, byte column) {
        return row >= 0 && row < caveSide && column >= 0 && column < caveSide;
    }

    public Perceptions[] getPerceptions() {
        return Arrays.stream(squares).map(Square::getPerceptions).toArray(Perceptions[]::new);
    }
}
