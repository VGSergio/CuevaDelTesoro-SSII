package mvc.model.maze;

import static mvc.model.Global.CLEAN;

public class Square {

    private byte status;

    public Square() {
        this.status = CLEAN;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
