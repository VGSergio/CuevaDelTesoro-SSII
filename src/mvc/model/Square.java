package mvc.model;

public class Square {

    private final Perceptions PERCEPTIONS = new Perceptions();
    private int STATUS = -1;

    public Square() {
    }

    public int getStatus() {
        return STATUS;
    }

    public void setStatus(int status) {
        this.STATUS = status;
    }
}
