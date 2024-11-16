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

    public boolean isClean(){
        for (boolean value : perceptions){
            if (value){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Stench: " + getPerception(PerceptionType.STENCH.ordinal()) +
                "\nBreeze: " + getPerception(PerceptionType.BREEZE.ordinal()) +
                "\nRadiance: " + getPerception(PerceptionType.RADIANCE.ordinal()) +
                "\nBang: " + getPerception(PerceptionType.BANG.ordinal()) +
                "\nGroan: " + getPerception(PerceptionType.GROAN.ordinal());
    }
}
