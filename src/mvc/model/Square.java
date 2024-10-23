package mvc.model;

import static mvc.model.Global.CLEAN;

public class Square {

    private final Perceptions PERCEPTIONS = new Perceptions();
    private byte STATUS = CLEAN;

    public Square() {
    }

    public byte getStatus() {
        return STATUS;
    }

    public void setStatus(byte status) {
        this.STATUS = status;
    }
}
