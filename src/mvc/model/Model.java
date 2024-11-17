package mvc.model;

import mvc.model.cave.Cave;

import java.util.ArrayList;
import java.util.List;

import static mvc.model.Global.Cave_Constants;

public class Model {

    private final Cave cave;
    private final List<Player> players;
    private boolean started;

    public Model() {
        cave = new Cave(Cave_Constants.MIN_SIDE);
        started = false;
        players = new ArrayList<>();
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Cave getCave() {
        return cave;
    }

    public void addPlayer(byte row, byte col) {
        Player player = new Player(row, col);
        player.linkCave(cave);
        players.add(player);
    }

    public void removePlayer(byte row, byte col) {
        players.remove(new Player(row, col));
    }

    public void exploreCave() {
        for (Player player : players) {
            player.exploreCave();
        }
    }

    public boolean isCaveExplored() {
        return players.stream().allMatch(Player::hasFinished);
    }
}
