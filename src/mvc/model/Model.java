package mvc.model;

import static mvc.model.Global.MIN_MAZE_SIDE;

public class Model {

    private int mazeSide;
    private int amountOfMonsters;
    private int amountOfHoles;
    private int amountOTreasures;
    private Square[] maze;

    public Model() {
        this.mazeSide = MIN_MAZE_SIDE;

        initializeMaze();
    }


    private void initializeMaze() {
        this.maze = new Square[mazeSide * mazeSide];
        for (int i = 0; i < maze.length; i++) {
            this.maze[i] = new Square();
        }

        this.amountOfMonsters = 0;
        this.amountOfHoles = 0;
        this.amountOTreasures = 0;
    }

    public int getMazeSide() {
        return mazeSide;
    }

    public void setMazeSide(int mazeSide) {
        this.mazeSide = mazeSide;
        initializeMaze();
    }

    public int getAmountOfMonsters() {
        return amountOfMonsters;
    }

    public void decreaseAmountOfMonsters() {
        this.amountOfMonsters -= 1;
    }

    public void increaseAmountOfMonsters() {
        this.amountOfMonsters += 1;
    }

    public int getAmountOfHoles() {
        return amountOfHoles;
    }

    public void decreaseAmountOfHoles() {
        this.amountOfHoles -= 1;
    }

    public void increaseAmountOfHoles() {
        this.amountOfHoles += 1;
    }

    public int getAmountOTreasures() {
        return amountOTreasures;
    }

    public void decreaseAmountOfTreasures() {
        this.amountOTreasures -= 1;
    }

    public void increaseAmountOTreasures() {
        this.amountOTreasures += 1;
    }

    public Square[] getMaze() {
        return maze;
    }

    public void setMaze(Square[] maze) {
        this.maze = maze;
    }

}
