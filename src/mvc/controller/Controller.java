package mvc.controller;

import mvc.model.Model;
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

    public void notify(String event, int size){
        if (!event.equals(MAZE_SIDE_CHANGED)){
            return;
        }

        MODEL.setMazeSide(size);
        view.mazeSizeChanged(size);
    }

    public void notify(String event, int row, int column){
        if (!event.equals(SQUARE_CLICKED) ||                        // Not the expected event
                (row == MODEL.getMazeSide() - 1 && column == 0)     // Tries to place selectedItem onto the user square
        ) {
            return;
        }

        view.getMaze().placeElement(selectedItem, row, column);
    }

    public void notify(String event, String element) {
        if (event.equals(ELEMENT_CHANGED)) {
            selectedItem = switch (element) {
                case MONSTER_IMAGE -> MONSTER;
                case HOLE_IMAGE -> HOLE;
                case TREASURE_IMAGE -> TREASURE;
                case CLEAN_IMAGE -> -1;
                default -> throw new IllegalStateException("Unexpected value: " + element);
            };
        } else if (event.equals(SPEED_CHANGED)) {
            System.out.println("Speed changed to " + element);
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
}
