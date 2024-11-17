package mvc.model.cave;

import mvc.model.Perceptions;

import static mvc.model.Global.*;

public class Cave extends CaveModel {

    private byte amountOfMonsters;
    private byte amountOfTreasures;
    private byte amountOfPlayers;

    public Cave(byte caveSide) {
        super(caveSide);
        initializeItemCounts();
    }

    @Override
    protected SquareStatus getInitialStatus() {
        return SquareStatus.CLEAN;
    }

    /**
     * Initializes item counts.
     */
    private void initializeItemCounts() {
        this.amountOfMonsters = 0;
        this.amountOfTreasures = 0;
    }

    /**
     * Updates perceptions for a specific square by row and column.
     */
    private void updatePerceptions(byte row, byte column) {
        Square square = getSquare(row, column);
        Perceptions perceptions = new Perceptions();

        // Calculate perceptions based on neighbors
        for (byte[] delta : DIRECTIONS_DELTAS) {
            byte neighborRow = (byte) (row + delta[0]);
            byte neighborCol = (byte) (column + delta[1]);

            if (isWithinBounds(neighborRow, neighborCol)) {
                PerceptionType perceptionType = mapStatusToPerception(getSquare(neighborRow, neighborCol).getStatus());
                if (perceptionType != null) {
                    perceptions.setPerception(perceptionType, true);
                }
            }
        }
        square.setPerceptions(perceptions);
    }

    public void setCaveSide(byte caveSide) {
        this.caveSide = caveSide;
        initializeSquares();
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

    public void updateAllPerceptions() {
        for (int position = 0; position < squares.length; position++) {
            updatePerceptions((byte) (position / caveSide), (byte) (position % caveSide));
        }
    }
}
