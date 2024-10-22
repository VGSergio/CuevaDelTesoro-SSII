package mvc.controller;

import mvc.model.Model;
import mvc.view.View;

import static mvc.model.Global.MAZE_SIDE_CHANGED;
import static mvc.model.Global.SQUARE_CLICKED;

public class Controller extends Thread {

    private Thread thread;

    private final Model MODEL = new Model();
    private View view;

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

    public void notify(String event){
        switch (event){
            case MAZE_SIDE_CHANGED:
                System.out.println(MAZE_SIDE_CHANGED);
                break;
            case SQUARE_CLICKED:
                System.out.println(SQUARE_CLICKED);
                break;
            default:
                System.out.println("Default");
                break;
        }
    }

    public void notify(String event, int size){
        if (!event.equals(MAZE_SIDE_CHANGED)){
            return;
        }

        view.mazeSizeChanged(size);
    }

    public void notify(String event, int row, int column){
        if (!event.equals(SQUARE_CLICKED)){
            return;
        }

        view.getMaze().placeElement(1, row, column);
    }
}
