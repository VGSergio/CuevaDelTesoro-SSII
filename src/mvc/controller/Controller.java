package mvc.controller;

import mvc.model.Model;
import mvc.model.Square;
import mvc.view.View;

import static mvc.model.Global.*;

public class Controller extends Thread {

    private Thread thread;

    private final Model MODEL = new Model();
    private View view;

    private byte selectedItem = CLEAN;
    private int selectedSpeed = NORMAL_SPEED_VALUE;

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
            case MAZE_SIDE_CHANGED -> handleMazeSideChanged((byte) params[0]);
            case SQUARE_CLICKED -> handleSquareClicked((byte) params[0], (byte) params[1]);
            case ELEMENT_CHANGED -> handleElementChanged((String) params[0]);
            case SPEED_CHANGED -> handleSpeedChanged((String) params[0]);
            case NEXT_STEP_CLICKED -> handleNextStepClicked();
            default -> System.err.println("Unexpected event");
        }
    }

    private void handleMazeSideChanged(byte size) {
        MODEL.setMazeSide(size);
        view.mazeSizeChanged(size);
    }

    private void handleSquareClicked(byte row, byte column) {
        if (row == MODEL.getMazeSide() - 1 && column == 0) {
            System.err.println("Square reserved to place the player");
            return;
        }
        if (!canPlaceItem()) {
            return;
        }

        Square square = MODEL.getMaze()[row * MODEL.getMazeSide() + column];
        byte status = square.getStatus();

        if (status == selectedItem) {
            return; // Do nothing if both are equal
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
        if (selectedItem == TREASURE && MODEL.getAmountOfTreasures() == MAX_TREASURES) {
            System.err.println("Can only place a maximum of " + MAX_TREASURES + " treasure(s)");
            return false;
        }
        return true;
    }

    private void updateModelCounts(byte currentStatus, byte newStatus) {
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
            case CLEAN_IMAGE -> CLEAN;
            case MONSTER_IMAGE -> MONSTER;
            case HOLE_IMAGE -> HOLE;
            case TREASURE_IMAGE -> TREASURE;
            default -> throw new IllegalStateException("Unexpected value: " + element);
        };
        view.getControls().getElementSelector().getPicture().setPicture(element);
    }

    private void handleSpeedChanged(String element) {
        selectedSpeed = switch (element) {
            case SLOW_SPEED -> SLOW_SPEED_VALUE;
            case NORMAL_SPEED -> NORMAL_SPEED_VALUE;
            case FAST_SPEED -> FAST_SPEED_VALUE;
            case MANUAL_SPEED -> MANUAL_SPEED_VALUE;
            default -> throw new IllegalStateException("Unexpected value: " + element);
        };
        System.out.println("Speed changed to " + selectedSpeed);
    }

    private void handleNextStepClicked() {
        if (selectedSpeed != MANUAL_SPEED_VALUE) {
            System.err.println("No manual step in this speed");
            return;
        }

        System.out.println("Next step");
    }
}
