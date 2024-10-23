package mvc.controller;

import mvc.model.Model;
import mvc.model.Square;
import mvc.view.View;

import static mvc.model.Global.*;

public class Controller extends Thread {

    private Thread thread;

    private final Model MODEL = new Model();
    private View view;

    private int selectedItem = -1;

    public static void main(String[] args) {
        new Controller().start();
    }

    @Override
    public void run(){
        createThread();
        startThread();
        stopThread();
    }

    private void createThread() {
        thread = new Thread(() -> view = new View(this, MODEL.getMazeSide()));
    }

    private void startThread() {
        thread.start();
    }

    private void stopThread() {
        try {
            thread.join();
            thread = null;
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void notify(String event, Object... params) {
        switch (event) {
            case MAZE_SIDE_CHANGED -> handleMazeSideChanged((int) params[0]);
            case SQUARE_CLICKED -> handleSquareClicked((int) params[0], (int) params[1]);
            case ELEMENT_CHANGED -> handleElementChanged((String) params[0]);
            case SPEED_CHANGED -> handleSpeedChanged((String) params[0]);
            default -> System.err.println("Unexpected event");
        }
    }

    private void handleMazeSideChanged(int size) {
        MODEL.setMazeSide(size);
        view.mazeSizeChanged(size);
    }

    private void handleSquareClicked(int row, int column) {
        if (row == MODEL.getMazeSide() - 1 && column == 0) {
            System.err.println("Square reserved to place the player");
            return;
        }
        if (!canPlaceItem()) {
            return;
        }

        Square square = MODEL.getMaze()[row * MODEL.getMazeSide() + column];
        int status = square.getStatus();

        if (status == -1 && selectedItem == -1) {
            return; // Do nothing if both are -1
        }

        updateModelCounts(status, selectedItem);
        square.setStatus(selectedItem);
        view.getMaze().placeElement(selectedItem, row, column);
    }

    private boolean canPlaceItem() {
        if (selectedItem == MONSTER && MODEL.getAmountOfMonsters() == MAX_MONSTERS) {
            System.err.println("Can only place a maximum of " + MAX_MONSTERS + " monster(s)");
            return false;
        }
        if (selectedItem == TREASURE && MODEL.getAmountOTreasures() == MAX_TREASURES) {
            System.err.println("Can only place a maximum of " + MAX_TREASURES + " treasure(s)");
            return false;
        }
        return true;
    }

    private void updateModelCounts(int currentStatus, int newStatus) {
        // currentStatus == -1. newStatus != -1 -> Increase newStatus
        // currentStatus != -1. newStatus == -1 -> Decrease currentStatus
        // currentStatus != -1. newStatus != -1 -> Decrease currentStatus, increase newStatus

        switch (currentStatus) {
            case MONSTER:
                MODEL.decreaseAmountOfMonsters();
                break;
            case HOLE:
                MODEL.decreaseAmountOfHoles();
                break;
            case TREASURE:
                MODEL.decreaseAmountOfTreasures();
                break;
            default:
                break; // Do nothing for status -1 or unrecognized values
        }

        switch (newStatus) {
            case MONSTER:
                MODEL.increaseAmountOfMonsters();
                break;
            case HOLE:
                MODEL.increaseAmountOfHoles();
                break;
            case TREASURE:
                MODEL.increaseAmountOTreasures();
                break;
            default:
                break; // Do nothing for selectedItem -1 or unrecognized values
        }
    }

    private void handleElementChanged(String element) {
        selectedItem = switch (element) {
            case MONSTER_IMAGE -> MONSTER;
            case HOLE_IMAGE -> HOLE;
            case TREASURE_IMAGE -> TREASURE;
            case CLEAN_IMAGE -> -1;
            default -> throw new IllegalStateException("Unexpected value: " + element);
        };
    }

    private void handleSpeedChanged(String element) {
        int speed = switch (element) {
            case SLOW_SPEED -> 1_000;
            case NORMAL_SPEED -> 500;
            case FAST_SPEED -> 250;
            case MANUAL_SPEED -> -1;
            default -> throw new IllegalStateException("Unexpected value: " + element);
        };
        System.out.println("Speed changed to " + speed);
    }
}
