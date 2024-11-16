package mvc.model.maze;

import mvc.model.Global.Perception_Constants;
import mvc.model.Perceptions;

public class Square {

    private byte status;
    private Perceptions perceptions;
    private boolean visited;

    public Square() {
        status = Perception_Constants.CLEAN;
        perceptions = new Perceptions();
        visited = false;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Perceptions getPerceptions() {
        return perceptions;
    }

    public void setPerceptions(Perceptions perceptions) {
        this.perceptions = perceptions;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean value) {
        visited = value;
    }
}
