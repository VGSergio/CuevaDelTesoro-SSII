package mvc.controller;

import mvc.model.Model;
import mvc.model.maze.Maze;
import mvc.model.maze.Square;
import mvc.view.View;

import static mvc.model.Global.*;

public class Controller extends Thread {

    private final Model MODEL = new Model();
    private View view;

    private byte selectedItem = Perception_Constants.CLEAN;
    private int selectedSpeed = Speed_Constants.NORMAL_VALUE;

    public static void main(String[] args) {
        new Controller().start();
    }

    @Override
    public void run() {
        initializeView();

        while (MODEL.isStarted()) {
            MODEL.updateMaze();
        }
    }

    private void initializeView() {
        try {
            view = new View(this, MODEL.getMaze().getMazeSide(), MODEL.getMaze().getSquaresStatus());
        } catch (Exception e) {
            System.err.println("Error initializing view: " + e.getMessage());
        }
    }

    public void notify(String event, Object... params) {
        switch (event) {
            case Events_Constants.MAZE_SIDE_CHANGED -> handleMazeSideChanged(castToInt(params[0]));
            case Events_Constants.SQUARE_CLICKED -> handleSquareClicked(castToByte(params[0]), castToByte(params[1]));
            case Events_Constants.ELEMENT_CHANGED -> handleElementChanged((String) params[0]);
            case Events_Constants.SPEED_CHANGED -> handleSpeedChanged((String) params[0]);
            case Events_Constants.NEXT_STEP_CLICKED -> handleNextStepClicked();
            case Events_Constants.START_CLICKED -> handleStartClicked();
            default -> System.err.println("Unexpected event: " + event);
        }
    }

    private void handleMazeSideChanged(int size) {
        if (MODEL.isStarted()) {
            System.err.println("Maze size can not be changed once started.");
            return;
        }
        MODEL.getMaze().setMazeSide((byte) size);
        view.mazeSizeChanged(MODEL.getMaze().getMazeSide(), MODEL.getMaze().getSquaresStatus());
    }

    private void handleSquareClicked(byte row, byte column) {
        if (!canPlaceItem(row, column)) return;

        Square square = MODEL.getMaze().getSquare(row, column);
        byte status = square.getStatus();

        if (status == selectedItem) return; // Do nothing if already set to selected item

        updateModelCounts(status, selectedItem);
        square.setStatus(selectedItem);
        view.getMaze().placeElement(selectedItem, row, column);
    }

    private boolean canPlaceItem(byte row, byte column) {
        if (MODEL.isStarted()) {
            System.err.println("Maze cannot be edited once started.");
            return false;
        }
        if (selectedItem == Perception_Constants.CLEAN) {
            return true;
        }
        if (selectedItem == Perception_Constants.MONSTER && MODEL.getMaze().getAmountOfMonsters() >= Maze_Constants.MAX_MONSTERS) {
            System.err.println("Maximum number of monsters reached.");
            return false;
        }
        if (selectedItem == Perception_Constants.TREASURE && MODEL.getMaze().getAmountOfTreasures() >= Maze_Constants.MAX_TREASURES) {
            System.err.println("Maximum number of treasures reached.");
            return false;
        }
        if (selectedItem != Perception_Constants.PLAYER && row == MODEL.getMaze().getMazeSide() - 1 && column == 0) {
            System.err.println("Position reserved for a player.");
            return false;
        }

        return true;
    }

    private void updateModelCounts(byte currentStatus, byte newStatus) {
        Maze maze = MODEL.getMaze();

        // Adjust the count for current status (decrease)
        adjustMazeCount(maze, currentStatus, -1);

        // Adjust the count for new status (increase)
        adjustMazeCount(maze, newStatus, 1);
    }

    private void adjustMazeCount(Maze maze, byte status, int delta) {
        switch (status) {
            case Perception_Constants.MONSTER -> maze.adjustAmountOfMonsters(delta);
            case Perception_Constants.TREASURE -> maze.adjustAmountOfTreasures(delta);
            case Perception_Constants.PLAYER -> maze.adjustAmountOfPlayers(delta);
        }
    }

    private void handleElementChanged(String element) {
        selectedItem = switch (element) {
            case Images_Constants.CLEAN -> Perception_Constants.CLEAN;
            case Images_Constants.MONSTER -> Perception_Constants.MONSTER;
            case Images_Constants.HOLE -> Perception_Constants.HOLE;
            case Images_Constants.TREASURE -> Perception_Constants.TREASURE;
            case Images_Constants.PLAYER -> Perception_Constants.PLAYER;
            default -> throw new IllegalStateException("Unexpected value: " + element);
        };
        view.getControls().getElementSelector().getPicture().setPicture(element);
    }

    private void handleSpeedChanged(String element) {
        selectedSpeed = switch (element) {
            case Speed_Constants.SLOW -> Speed_Constants.SLOW_VALUE;
            case Speed_Constants.NORMAL -> Speed_Constants.NORMAL_VALUE;
            case Speed_Constants.FAST -> Speed_Constants.FAST_VALUE;
            case Speed_Constants.MANUAL -> Speed_Constants.MANUAL_VALUE;
            default -> throw new IllegalStateException("Unexpected value: " + element);
        };
        System.out.println("Speed changed to " + selectedSpeed);
    }

    private void handleNextStepClicked() {
        if (selectedSpeed != Speed_Constants.MANUAL_VALUE) {
            System.err.println("Manual steps only allowed at manual speed.");
            return;
        }
        System.out.println("Next step executed.");
    }

    private void handleStartClicked() {
        if (!canStart()) return;

        MODEL.getMaze().updateAllPerceptions();
        MODEL.setStarted(true);

        System.out.println("Maze started.");
    }

    private boolean canStart() {
        if (MODEL.isStarted()) {
            System.err.println("Maze has already started.");
            return false;
        }

        Maze maze = MODEL.getMaze();
        if (maze.getAmountOfMonsters() == 0 || maze.getAmountOfTreasures() == 0) {
            System.err.println("A monster and treasure are required to start.");
            return false;
        }

        if (maze.getAmountOfTreasures() != maze.getAmountOfPlayers()) {
            System.err.println("Each player has to be able to get a treasure.");
            return false;
        }
        return true;
    }

    private int castToInt(Object param) {
        return param instanceof Integer ? (Integer) param : 0;
    }

    private byte castToByte(Object param) {
        return param instanceof Byte ? (Byte) param : 0;
    }
}
