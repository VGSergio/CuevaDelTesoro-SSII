package mvc.model;

import mvc.model.cave.CaveModel;

import java.util.ArrayList;
import java.util.List;

import static mvc.model.Global.Cave_Constants;

public class Model {

    private final CaveModel caveModel;
    private final List<Player> players;
    private boolean started;

    public Model() {
        caveModel = new CaveModel(Cave_Constants.MIN_SIDE);
        started = false;
        players = new ArrayList<>();
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public CaveModel getCave() {
        return caveModel;
    }

    public void addPlayer(byte row, byte col) {
        players.add(new Player(row, col));
    }

    public void removePlayer(byte row, byte col) {
        players.remove(new Player(row, col));
    }

    public void exploreCave() {
        for (Player player : players) {
            player.linkCave(caveModel);
            player.exploreCave();
        }
    }

    public boolean isCaveExplored() {
        return players.stream().allMatch(Player::hasFinished);
    }
}
