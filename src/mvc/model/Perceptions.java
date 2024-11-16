package mvc.model;

import java.util.Arrays;

import static mvc.model.Global.PerceptionType;

public class Perceptions {

    private final boolean[] perceptions;

    public Perceptions() {
        this.perceptions = new boolean[PerceptionType.values().length];
        Arrays.fill(this.perceptions, false); // Initialize all perceptions to false
    }

    public boolean getPerception(int perceptionType) {
        return perceptions[perceptionType];
    }

    public void setPerception(PerceptionType perceptionType, boolean value) {
        this.perceptions[perceptionType.ordinal()] = value;
    }

    @Override
    public String toString() {
        return "Hedor: " + getPerception(PerceptionType.MONSTER.ordinal()) +
                "\nBrisa: " + getPerception(PerceptionType.HOLE.ordinal()) +
                "\nResplandor: " + getPerception(PerceptionType.TREASURE.ordinal()) +
                "\nGolpe: " + getPerception(PerceptionType.WALL.ordinal()) +
                "\nGemido: " + getPerception(PerceptionType.MOAN.ordinal());
    }
}
