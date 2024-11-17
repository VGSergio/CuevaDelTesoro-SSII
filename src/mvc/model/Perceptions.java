package mvc.model;

import java.util.Arrays;

import static mvc.model.Global.PerceptionType;

public class Perceptions {

    private final boolean[] perceptions;

    public Perceptions() {
        this.perceptions = new boolean[PerceptionType.values().length];
        Arrays.fill(this.perceptions, false); // Initialize all perceptions to false
    }

    public boolean getPerception(PerceptionType perceptionType) {
        return perceptions[perceptionType.ordinal()];
    }

    public void setPerception(PerceptionType perceptionType, boolean value) {
        this.perceptions[perceptionType.ordinal()] = value;
    }

    public boolean isClean() {
        for (boolean value : perceptions) {
            if (value) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Stench: " + getPerception(PerceptionType.STENCH) +
                "\nBreeze: " + getPerception(PerceptionType.BREEZE) +
                "\nRadiance: " + getPerception(PerceptionType.RADIANCE) +
                "\nBang: " + getPerception(PerceptionType.BANG) +
                "\nGroan: " + getPerception(PerceptionType.GROAN);
    }
}
