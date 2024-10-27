package mvc.model;

import java.util.Arrays;

import static mvc.model.Global.*;

public class Knowledge {

    private final boolean[] perceptions;
    private byte status;

    public Knowledge() {
        perceptions = new boolean[NUMBER_OF_PERCEPTIONS];
        Arrays.fill(perceptions, false);
        this.status = UNKNOWN;
    }

    public boolean[] getPerceptions() {
        return perceptions;
    }

    public boolean isStink() {
        return perceptions[MONSTER];
    }

    public void setStink(boolean stink) {
        perceptions[MONSTER] = stink;
    }

    public boolean isWind() {
        return perceptions[HOLE];
    }

    public void setWind(boolean wind) {
        perceptions[HOLE] = wind;
    }

    public boolean isRadiance() {
        return perceptions[TREASURE];
    }

    public void setRadiance(boolean radiance) {
        perceptions[TREASURE] = radiance;
    }

    public boolean issBang() {
        return perceptions[WALL];
    }

    public void setBang(boolean bang) {
        perceptions[WALL] = bang;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
