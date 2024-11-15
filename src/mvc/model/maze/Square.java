package mvc.model.maze;

import mvc.model.Global.Perception_Constants;
import mvc.model.Perceptions;

public class Square {

    private byte status;
    private Perceptions perceptions;

    public Square() {
        this.status = Perception_Constants.CLEAN;
        this.perceptions = new Perceptions();
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
}
