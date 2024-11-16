package mvc.model;

import java.util.Arrays;

import static mvc.model.Global.Perception_Constants;

public class Perceptions {

    private final boolean[] perceptions;

    public Perceptions() {
        this.perceptions = new boolean[Perception_Constants.NUMBER_OF_PERCEPTIONS];
        Arrays.fill(this.perceptions, false); // Initialize all perceptions to false
    }

    public boolean getPerception(int perceptionType) {
        return perceptions[perceptionType];
    }

    public void setPerception(int perceptionType, boolean value) {
        this.perceptions[perceptionType] = value;
    }

    @Override
    public String toString() {
        return "Hedor: " + getPerception(Perception_Constants.MONSTER) +
                "\nBrisa: " + getPerception(Perception_Constants.HOLE) +
                "\nResplandor: " + getPerception(Perception_Constants.TREASURE) +
                "\nGolpe: " + getPerception(Perception_Constants.WALL) +
                "\nGemido: " + getPerception(Perception_Constants.MOAN);
    }

    public boolean isAValidPerception(int perceptionType) {
        return perceptionType >= 0 && perceptionType < Perception_Constants.NUMBER_OF_PERCEPTIONS;
    }
}
