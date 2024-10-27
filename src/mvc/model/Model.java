package mvc.model;

import mvc.model.maze.Maze;

import static mvc.model.Global.MIN_MAZE_SIDE;

public class Model {

    private boolean isRunning;

    private final Maze MAZE = new Maze(MIN_MAZE_SIDE);

    public Model() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Maze getMaze() {
        return MAZE;
    }

}
