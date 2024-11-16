package mvc.controller;

import mvc.model.Model;
import mvc.model.maze.MazeModel;
import mvc.model.maze.Square;
import mvc.view.View;

import static mvc.model.Global.*;

public class Controller extends Thread {

    private Model model;
    private View view;

    private byte selectedStatus = Status_Constants.DEFAULT;
    private int selectedSpeed = Speed_Constants.DEFAULT_VALUE;

    public static void main(String[] args) {
        new Controller().start();
    }

    @Override
    public void run() {
        initializeModel();
        initializeView();
    }

    private void initializeModel() {
        model = new Model();
        model.getMaze().initializePlayer();
    }

    private void initializeView() {
        try {
            view = new View(this, model.getMaze());
        } catch (Exception e) {
            System.err.println("Error initializing view: " + e.getMessage());
        }
        if (view == null) {
            System.err.println("View failed to initialize. Exiting program.");
            System.exit(1); // Fail-fast approach
        }
    }

    public void notify(String event, Object... params) {
        switch (event) {
            case Events_Constants.MAZE_SIDE_CHANGED -> handleMazeSideChanged(castToInt(params[0]));
            case Events_Constants.SQUARE_CLICKED -> handleSquareClicked(castToByte(params[0]), castToByte(params[1]));
            case Events_Constants.STATUS_CHANGED -> handleElementChanged((String) params[0]);
            case Events_Constants.SPEED_CHANGED -> handleSpeedChanged((String) params[0]);
            case Events_Constants.NEXT_STEP_CLICKED -> handleNextStepClicked();
            case Events_Constants.START_CLICKED -> handleStartClicked();
            case Events_Constants.MAZE_UPDATED -> handleMazeUpdated();
            default -> System.err.println("Unexpected event: " + event);
        }
    }

    private void handleMazeSideChanged(int size) {
        if (model.isStarted()) {
            System.err.println("Maze size can not be changed once started.");
            return;
        }

        model.getMaze().setMazeSide((byte) size);
        model.getMaze().initializePlayer();

        view.updateView();
    }

    private void handleSquareClicked(byte row, byte column) {
        if (!canPlaceItem(row, column)) return;

        Square square = model.getMaze().getSquare(row, column);
        byte status = square.getStatus();

        if (status == selectedStatus) {
            System.out.println("Square already set to selected item. No change made.");
            return;
        } // Do nothing if already set to selected item

        updateModelCounts(status, selectedStatus);
        square.setStatus(selectedStatus);
        view.updateView();
    }

    private boolean canPlaceItem(byte row, byte column) {
        if (model.isStarted()) {
            System.err.println("Maze cannot be edited once started.");
            return false;
        }

        MazeModel mazeModel = model.getMaze();
        if (selectedStatus == Status_Constants.MONSTER && mazeModel.getAmountOfMonsters() >= Maze_Constants.MAX_MONSTERS) {
            System.err.println("Maximum number of monsters reached.");
            return false;
        }
        if (selectedStatus == Status_Constants.TREASURE && mazeModel.getAmountOfTreasures() >= Maze_Constants.MAX_TREASURES) {
            System.err.println("Maximum number of treasures reached.");
            return false;
        }
        if (selectedStatus == Status_Constants.PLAYER && mazeModel.getAmountOfPlayers() >= Maze_Constants.MAX_PLAYERS) {
            System.err.println("Maximum number of players reached.");
            return false;
        }
        if (selectedStatus != Status_Constants.PLAYER && row == mazeModel.getMazeSide() - 1 && column == 0) {
            System.err.println("Position reserved for a player.");
            return false;
        }

        return true;
    }

    private void updateModelCounts(byte currentStatus, byte newStatus) {
        MazeModel mazeModel = model.getMaze();
        adjustMazeCount(mazeModel, currentStatus, -1);
        adjustMazeCount(mazeModel, newStatus, 1);
    }

    private void adjustMazeCount(MazeModel mazeModel, byte status, int delta) {
        switch (status) {
            case Status_Constants.MONSTER -> mazeModel.adjustAmountOfMonsters(delta);
            case Status_Constants.TREASURE -> mazeModel.adjustAmountOfTreasures(delta);
            case Status_Constants.PLAYER -> mazeModel.adjustAmountOfPlayers(delta);
        }
    }

    private void handleElementChanged(String element) {
        selectedStatus = switch (element) {
            case Images_Constants.MONSTER -> Status_Constants.MONSTER;
            case Images_Constants.HOLE -> Status_Constants.HOLE;
            case Images_Constants.TREASURE -> Status_Constants.TREASURE;
            case Images_Constants.PLAYER -> Status_Constants.PLAYER;
            case Images_Constants.CLEAN -> Status_Constants.CLEAN;
            default -> throw new IllegalStateException("Unexpected value: " + element);
        };
        view.getControls().getStatusSelector().getPicture().setPicture(element);
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

        // Load perceptions
        model.getMaze().updateAllPerceptions();

        new Thread(() -> {
            System.out.println("Maze started.");
            model.setStarted(true);
            while (!model.isMazeExplored()) {
                model.exploreMaze();
                view.updateView();
                try {
                    Thread.sleep(selectedSpeed);
                } catch (InterruptedException e) {
                    return;
                }
            }
            model.setStarted(false);
        }).start();
    }

    private boolean canStart() {
        if (model.isStarted()) {
            System.err.println("Maze has already started.");
            return false;
        }

        MazeModel mazeModel = model.getMaze();
        if (mazeModel.getAmountOfMonsters() == 0 || mazeModel.getAmountOfTreasures() == 0 || mazeModel.getAmountOfPlayers() == 0) {
            System.err.println("A monster, a treasure and a player are required to start.");
            return false;
        }

        return true;
    }

    private void handleMazeUpdated() {
        view.updateView();
    }

    private int castToInt(Object param) {
        return param instanceof Integer ? (Integer) param : 0;
    }

    private byte castToByte(Object param) {
        return param instanceof Byte ? (Byte) param : 0;
    }
}
