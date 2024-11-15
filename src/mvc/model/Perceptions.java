package mvc.model;

import java.util.Arrays;

import static mvc.model.Global.Perception_Constants;

public class Perceptions {

    private final boolean[] perceptions;

    public Perceptions() {
        this.perceptions = new boolean[Perception_Constants.NUMBER_OF_PERCEPTIONS];
        Arrays.fill(this.perceptions, false);
    }

    public boolean[] getPerceptions() {
        return this.perceptions;
    }

    public void setMonsterPerception(boolean value) {
        this.perceptions[Perception_Constants.MONSTER] = value;
    }

    public boolean getMonsterPerception() {
        return this.perceptions[Perception_Constants.MONSTER];
    }

    public void setHolePerception(boolean value) {
        this.perceptions[Perception_Constants.HOLE] = value;
    }

    public boolean getHolePerception() {
        return this.perceptions[Perception_Constants.HOLE];
    }

    public void setTreasurePerception(boolean value) {
        this.perceptions[Perception_Constants.TREASURE] = value;
    }

    public boolean getTreasurePerception() {
        return this.perceptions[Perception_Constants.TREASURE];
    }

    public void setWallPerception(boolean value) {
        this.perceptions[Perception_Constants.WALL] = value;
    }

    public boolean getWallPerception() {
        return this.perceptions[Perception_Constants.WALL];
    }

    public String toString() {
        return "Stinks? " + getMonsterPerception() + " Wind? " + getHolePerception() + " Brilliance? " + getTreasurePerception();
    }
}
