package mvc.model.cave;

import mvc.model.Perceptions;

import java.util.Arrays;

import static mvc.model.Global.getSquarePositionInCave;

public class Map {

    private byte caveSide;
    private Square[] squares;

    public Map(byte caveSide) {
        this.caveSide = caveSide;
        initializeSquares();
    }

    private void initializeSquares() {
        squares = new Square[caveSide * caveSide];
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square();
        }
    }

    public byte getCaveSide() {
        return caveSide;
    }

    public void setCaveSide(byte caveSide) {
        this.caveSide = caveSide;
        initializeSquares();
    }

    public Square[] getSquares() {
        return squares;
    }

    public Square getSquare(int pos) {
        return squares[pos];
    }

    public Square getSquare(byte row, byte col) {
        return squares[getSquarePositionInCave(row, col, caveSide)];
    }

    public Perceptions[] getPerceptions() {
        return Arrays.stream(squares).map(Square::getPerceptions).toArray(Perceptions[]::new);
    }

    public boolean isWithinBounds(byte row, byte column) {
        return row >= 0 && row < caveSide && column >= 0 && column < caveSide;
    }
}
