package mvc.model;

import static mvc.model.Global.CLEAN;

public class Square {

    private final Perceptions PERCEPTIONS = new Perceptions();
    private int STATUS = CLEAN;

    public Square() {
    }

    public int getStatus() {
        return STATUS;
    }

    public void setStatus(int status) {
        this.STATUS = status;
    }
}
