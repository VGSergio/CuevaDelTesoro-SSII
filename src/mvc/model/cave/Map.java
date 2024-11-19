package mvc.model.cave;

import mvc.model.Global.SquareStatus;

public class Map extends CaveModel {

    public Map(byte caveSide) {
        super(caveSide);
    }

    @Override
    protected SquareStatus getInitialStatus() {
        return SquareStatus.UNKNOWN;
    }
}
