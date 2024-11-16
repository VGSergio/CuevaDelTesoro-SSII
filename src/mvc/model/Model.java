package mvc.model;

import mvc.model.maze.MazeModel;

import static mvc.model.Global.Maze_Constants;
import static mvc.model.Global.Status_Constants;

public class Model {

    private final MazeModel mazeModel;
    private boolean started;

    public Model() {
        mazeModel = new MazeModel(Maze_Constants.MIN_SIDE, Status_Constants.CLEAN);
        started = false;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public MazeModel getMaze() {
        return mazeModel;
    }

    public void exploreMaze() {
        mazeModel.exploreMaze();
    }

    public boolean isMazeExplored() {
        return mazeModel.isMazeExplored();
    }
}
