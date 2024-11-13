package mvc.model;

import mvc.model.maze.Maze;

import static mvc.model.Global.MIN_MAZE_SIDE;

public class Model {

    private final Maze MAZE = new Maze(MIN_MAZE_SIDE);
    private boolean isRunning;
    private Player player;

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
