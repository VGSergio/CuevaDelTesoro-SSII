package mvc.model;

import mvc.model.cave.CaveModel;

import static mvc.model.Global.Cave_Constants;
import static mvc.model.Global.SquareStatus;

public class Model {

    private final CaveModel caveModel;
    private boolean started;

    public Model() {
        caveModel = new CaveModel(Cave_Constants.MIN_SIDE, SquareStatus.CLEAN);
        started = false;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public CaveModel getMaze() {
        return caveModel;
    }

    public void exploreMaze() {
        caveModel.exploreMaze();
    }

    public boolean isMazeExplored() {
        return caveModel.isMazeExplored();
    }
}
