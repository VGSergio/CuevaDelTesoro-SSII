package mvc.model;

import java.util.Arrays;

import static mvc.model.Global.*;

public class Perceptions {
    private final boolean[] perceptions;

    public Perceptions() {
        perceptions = new boolean[4];
        Arrays.fill(perceptions, false);
    }

    public boolean[] getPerceptions() {
        return perceptions;
    }

    public boolean hasStink(){
        return perceptions[MONSTER];
    }

    public void setStink(boolean stink) {
        perceptions[MONSTER] = stink;
    }

    public boolean hasWind(){
        return perceptions[HOLE];
    }

    public void setWind(boolean wind) {
        perceptions[HOLE] = wind;
    }

    public boolean hasRadiance(){
        return perceptions[TREASURE];
    }

    public void setRadiance(boolean radiance) {
        perceptions[TREASURE] = radiance;
    }

    public boolean hasBang(){
        return perceptions[WALL];
    }

    public void setBang(boolean bang) {
        perceptions[WALL] = bang;
    }
}
