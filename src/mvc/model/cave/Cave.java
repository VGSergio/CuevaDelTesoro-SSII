package mvc.model.cave;

import mvc.model.Perceptions;

import static mvc.model.Global.*;

public class Cave extends CaveModel {

    private byte amountOfMonsters;
    private byte amountOfTreasures;
    private byte amountOfPlayers;

    public Cave(byte caveSide) {
        super(caveSide);
        cleanCave();
        initializeItemCounts();
    }

    private void cleanCave() {
        for (Square square : squares) {
            square.setStatus(SquareStatus.CLEAN);
        }
    }

    /**
     * Initializes item counts.
     */
    private void initializeItemCounts() {
        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
    }

    public void setCaveSide(byte caveSide) {
        this.caveSide = caveSide;
        initializeSquares();
        cleanCave();
        initializeItemCounts();
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
        for (int position = 0; position < squares.length; position++) {
            updatePerceptions((byte) (position / caveSide), (byte) (position % caveSide));
        }
    }
}
