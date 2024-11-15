package mvc.model;

import mvc.model.maze.MazeModel;

import static mvc.model.Global.Maze_Constants;

public class Model {

    private final MazeModel mazeModel = new MazeModel(Maze_Constants.MIN_SIDE);
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

    public MazeModel getMaze() {
        return mazeModel;
    }

    public void updateMaze() {

    }
}
