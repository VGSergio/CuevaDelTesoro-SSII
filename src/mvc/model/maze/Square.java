package mvc.model.maze;

import mvc.model.Global.SquareStatus;
import mvc.model.Perceptions;

public class Square {

    private SquareStatus status;
    private Perceptions perceptions;
    private boolean visited;

    public Square(SquareStatus defaultStatus) {
        status = defaultStatus;
        perceptions = null;
        visited = false;
    }

    public SquareStatus getStatus() {
        return status;
    }

    public void setStatus(SquareStatus status) {
        this.status = status;
    }

    public Perceptions getPerceptions() {
        return perceptions;
    }

    public void setPerceptions(Perceptions perceptions) {
        this.perceptions = perceptions;
    }

    public boolean notVisited() {
        return !visited;
    }

    public void setVisited(boolean value) {
        visited = value;
    }
}
