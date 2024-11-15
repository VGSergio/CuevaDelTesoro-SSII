package mvc.model;

import mvc.model.maze.Maze;

import static mvc.model.Global.Maze_Constants;

public class Model {

    private final Maze maze = new Maze(Maze_Constants.MIN_SIDE);
    private boolean started;

    public Model() {
        this.started = false;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean value) {
        this.started = value;
    }

    public Maze getMaze() {
        return maze;
    }

    public void updateMaze() {

    }
}
