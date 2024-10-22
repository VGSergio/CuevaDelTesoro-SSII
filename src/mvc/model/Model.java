package mvc.model;

import static mvc.model.Global.MIN_MAZE_SIDE;

public class Model {

    private int mazeSide;
    private int amountOfMonsters;
    private int amountOfHoles;
    private Square[] maze;

    public Model() {
        this.mazeSide = MIN_MAZE_SIDE;
        this.maze = new Square[mazeSide * mazeSide];
        this.amountOfMonsters = 0;
        this.amountOfHoles = 0;
    }


    public int getMazeSide() {
        return mazeSide;
    }

    public void setMazeSide(int mazeSide) {
        this.mazeSide = mazeSide;
    }

    public int getAmountOfMonsters() {
        return amountOfMonsters;
    }

    public void setAmountOfMonsters(int amountOfMonsters) {
        this.amountOfMonsters = amountOfMonsters;
    }

    public int getAmountOfHoles() {
        return amountOfHoles;
    }

    public void setAmountOfHoles(int amountOfHoles) {
        this.amountOfHoles = amountOfHoles;
    }

    public Square[] getMaze() {
        return maze;
    }

    public void setMaze(Square[] maze) {
        this.maze = maze;
    }

}
