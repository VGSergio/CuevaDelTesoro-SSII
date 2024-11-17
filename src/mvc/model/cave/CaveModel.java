package mvc.model.cave;

import static mvc.model.Global.getSquarePositionInCave;

public abstract class CaveModel {

    protected byte caveSide;
    protected Square[] squares;

    public CaveModel(byte caveSide) {
        this.caveSide = caveSide;
        initializeSquares();
    }

    /**
     * Initializes the squares array.
     */
    protected void initializeSquares() {
        squares = new Square[caveSide * caveSide];
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square();
        }
    }

    /**
     * Retrieves a square at a given position.
     */
    public Square getSquare(int pos) {
        return squares[pos];
    }

    /**
     * Retrieves a square at a given row and column.
     */
    public Square getSquare(byte row, byte col) {
        return squares[getSquarePositionInCave(row, col, caveSide)];
    }

    /**
     * Returns the squares array.
     */
    public Square[] getSquares() {
        return squares;
    }

    public byte getCaveSide() {
        return caveSide;
    }

    /**
     * Computes whether a row and column are valid given a caveSide.
     *
     * @param row      The row to check.
     * @param column   The column to check.
     * @return Whether row anc column are valid or not.
     */
    public boolean isWithinBounds(byte row, byte column) {
        return row >= 0 && row < caveSide && column >= 0 && column < caveSide;
    }

}
