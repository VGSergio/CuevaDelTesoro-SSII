package mvc.model.cave;

import mvc.model.Perceptions;

import static mvc.model.Global.*;

public class CaveModel {

    private byte caveSide;
    private Square[] squares;

    private byte amountOfMonsters;
    private byte amountOfTreasures;
    private byte amountOfPlayers;

    public CaveModel(byte caveSide) {
        setCaveSide(caveSide);
    }

    /**
     * Initializes the cave structure and resets item counts.
     */
    private void initializeCave() {
        int totalSquares = caveSide * caveSide;
        this.squares = new Square[totalSquares];
        for (int i = 0; i < totalSquares; i++) {
            this.squares[i] = new Square();
            this.squares[i].setStatus(SquareStatus.CLEAN);
        }

        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
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
     * Updates perceptions for a specific square by row and column.
     */
    public void updatePerceptions(byte row, byte column) {
        Square square = getSquare(row, column);
        Perceptions perceptions = new Perceptions();

        // Calculate perceptions based on neighbors
        for (int[] delta : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            byte neighborRow = (byte) (row + delta[0]);
            byte neighborCol = (byte) (column + delta[1]);

            if (isWithinBounds(neighborRow, neighborCol, caveSide)) {
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
        for (int position = 0; position < squares.length; position++) {
            updatePerceptions((byte) (position / caveSide), (byte) (position % caveSide));
        }
    }

}
