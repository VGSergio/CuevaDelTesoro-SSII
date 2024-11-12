package mvc.model;

import mvc.model.maze.Maze;

import static mvc.model.Global.MIN_MAZE_SIDE;

public class Model {

    private boolean isRunning;

    private final Maze MAZE = new Maze(MIN_MAZE_SIDE);
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
