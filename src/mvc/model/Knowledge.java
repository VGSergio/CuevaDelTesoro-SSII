package mvc.model;

import static mvc.model.Global.Perception_Constants;

public class Knowledge {

    private final Perceptions perceptions;
    private byte status;

    public Knowledge() {
        perceptions = new Perceptions();
        this.status = Perception_Constants.UNKNOWN;
    }

    public Perceptions getPerceptions() {
        return perceptions;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
