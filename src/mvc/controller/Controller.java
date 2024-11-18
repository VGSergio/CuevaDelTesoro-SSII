package mvc.controller;

import mvc.model.Model;
import mvc.model.cave.Cave;
import mvc.model.cave.Square;
import mvc.view.View;

import static mvc.model.Global.*;

/**
 * The {@code Controller} class acts as the main control mechanism for the
 * Model-View-Controller (MVC) architecture. It manages the interaction between the
 * model (business logic) and the view (user interface) while handling user inputs
 * and triggering updates.
 *
 * <p>This class extends {@code Thread} to enable concurrent execution and utilizes
 * event-driven methods to respond to user actions. It supports functionalities like
 * cave side adjustments, square status changes, and cave exploration initiation.
 *
 * @author Sergio Vega García
 * @author Marc Roman Colom
 * @see Model
 * @see View
 * @see Cave
 */
public class Controller extends Thread {

    private Model model;    // The model component of the MVC pattern
    private View view;      // The view component of the MVC pattern

    private SquareStatus selectedStatus = SQUARE_STATUS_DEFAULT;  // The selected status for cave squares
    private int selectedSpeed = Speed_Constants.DEFAULT_VALUE;    // The selected speed for cave exploration

    /**
     * Entry point of the application. Starts the controller thread.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Controller().start();
    }

    /**
     * The main execution method of the controller. Initializes the model and view
     * components of the MVC architecture.
     */
    @Override
    public void run() {
        initializeModel();
        initializeView();
    }

    /**
     * Initializes the model and sets up the cave's initial state.
     */
    private void initializeModel() {
        model = new Model();
    }

    /**
     * Initializes the view and links it with the controller and model.
     * Exits the program if initialization fails.
     */
    private void initializeView() {
        try {
            view = new View(this, model.getCave());
        } catch (Exception e) {
            System.err.println("Error initializing view: " + e.getMessage());
        }
        if (view == null) {
            System.err.println("View failed to initialize. Exiting program.");
            System.exit(1);
        }
    }

    /**
     * Handles various events triggered by the view or user interactions.
     * Delegates tasks to specific event-handling methods based on the event type.
     *
     * @param event  the type of event
     * @param params optional parameters associated with the event
     */
    public void notify(String event, Object... params) {
        switch (event) {
            case Events_Constants.CAVE_SIDE_CHANGED -> handleCaveSideChanged(castToInt(params[0]));
            case Events_Constants.SQUARE_CLICKED -> handleSquareClicked(castToByte(params[0]), castToByte(params[1]));
            case Events_Constants.STATUS_CHANGED -> handleStatusChanged((String) params[0]);
            case Events_Constants.SPEED_CHANGED -> handleSpeedChanged((String) params[0]);
            case Events_Constants.NEXT_STEP_CLICKED -> handleNextStepClicked();
            case Events_Constants.START_CLICKED -> handleStartClicked();
            case Events_Constants.CAVE_UPDATED -> handleCaveUpdated();
            case Events_Constants.RESET_CLICKED -> handleResetClicked();
            default -> System.err.println("Unexpected event: " + event);
        }
    }

    /**
     * Handles changes in the cave's side length.
     *
     * @param side the new side length of the cave
     */
    private void handleCaveSideChanged(int side) {
        if (model.isStarted()) {
            System.err.println("Cave side can not be changed once started.");
            return;
        }

        model.reset();
        model.getCave().setCaveSide((byte) side);

        view.updateView();
    }

    /**
     * Handles square clicks within the cave and updates their status.
     *
     * @param row    the row index of the clicked square
     * @param column the column index of the clicked square
     */
    private void handleSquareClicked(byte row, byte column) {
        if (!canPlaceItem(row, column)) return;

        Square square = model.getCave().getSquare(row, column);
        SquareStatus status = square.getStatus();

        if (status == selectedStatus) {
            System.out.println("Square already set to selected item. No change made.");
            return;
        }

        if (selectedStatus == SquareStatus.PLAYER) {
            model.addPlayer(row, column);
        }
        if (status == SquareStatus.PLAYER && selectedStatus == SquareStatus.CLEAN) {
            model.removePlayer(row, column);
        }

        updateModelCounts(status, selectedStatus);
        square.setStatus(selectedStatus);
        view.updateView();
    }

    /**
     * Validates whether an item can be placed at the specified position in the cave.
     *
     * @param row    the row index
     * @param column the column index
     * @return {@code true} if the item can be placed; {@code false} otherwise
     */
    private boolean canPlaceItem(byte row, byte column) {
        if (model.isStarted()) {
            System.err.println("Cave cannot be edited once started.");
            return false;
        }

        Cave cave = model.getCave();
        if (selectedStatus == SquareStatus.MONSTER && cave.getAmountOfMonsters() >= Cave_Constants.MAX_MONSTERS) {
            System.err.println("Maximum number of monsters reached.");
            return false;
        }
        if (selectedStatus == SquareStatus.TREASURE && cave.getAmountOfTreasures() >= Cave_Constants.MAX_TREASURES) {
            System.err.println("Maximum number of treasures reached.");
            return false;
        }
        if (selectedStatus == SquareStatus.PLAYER && cave.getAmountOfPlayers() >= Cave_Constants.MAX_PLAYERS) {
            System.err.println("Maximum number of players reached.");
            return false;
        }
        if (selectedStatus != SquareStatus.PLAYER && row == cave.getCaveSide() - 1 && column == 0) {
            System.err.println("Position reserved for a player.");
            return false;
        }

        return true;
    }

    /**
     * Updates the counts for square types in the model when a square's status changes.
     *
     * @param currentStatus the current status of the square
     * @param newStatus     the new status of the square
     */
    private void updateModelCounts(SquareStatus currentStatus, SquareStatus newStatus) {
        Cave cave = model.getCave();
        adjustCaveCount(cave, currentStatus, -1);
        adjustCaveCount(cave, newStatus, 1);
    }

    /**
     * Adjusts the count for a specific type of square (e.g., MONSTER, TREASURE).
     *
     * @param cave   the cave model containing square counts
     * @param status the square status to adjust
     * @param delta  the adjustment value (positive or negative)
     */
    private void adjustCaveCount(Cave cave, SquareStatus status, int delta) {
        switch (status) {
            case MONSTER -> cave.adjustAmountOfMonsters(delta);
            case TREASURE -> cave.adjustAmountOfTreasures(delta);
            case PLAYER -> cave.adjustAmountOfPlayers(delta);
        }
    }

    /**
     * Updates the selected status based on the provided status.
     *
     * <p>The selected status determines the type of square to be placed
     * when the user interacts with the cave. This method also updates
     * the view to reflect the selected status.
     *
     * @param status the name of the status (e.g., MONSTER, HOLE, TREASURE, PLAYER, CLEAN)
     * @throws IllegalStateException if the provided status is not recognized
     */
    private void handleStatusChanged(String status) {
        selectedStatus = switch (status) {
            case Images_Constants.MONSTER -> SquareStatus.MONSTER;
            case Images_Constants.HOLE -> SquareStatus.HOLE;
            case Images_Constants.TREASURE -> SquareStatus.TREASURE;
            case Images_Constants.PLAYER -> SquareStatus.PLAYER;
            case Images_Constants.CLEAN -> SquareStatus.CLEAN;
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
        view.getControls().getStatusSelector().getPicture().setPicture(status);
    }

    /**
     * Updates the selected speed for cave exploration based on the provided speed.
     *
     * <p>The speed determines how fast the cave exploration occurs when the game starts.
     *
     * @param speed the name of the speed option (e.g., SLOW, NORMAL, FAST, MANUAL)
     * @throws IllegalStateException if the provided speed option is not recognized
     */
    private void handleSpeedChanged(String speed) {
        selectedSpeed = switch (speed) {
            case Speed_Constants.SLOW -> Speed_Constants.SLOW_VALUE;
            case Speed_Constants.NORMAL -> Speed_Constants.NORMAL_VALUE;
            case Speed_Constants.FAST -> Speed_Constants.FAST_VALUE;
            case Speed_Constants.MANUAL -> Speed_Constants.MANUAL_VALUE;
            default -> throw new IllegalStateException("Unexpected value: " + speed);
        };
        System.out.println("Speed changed to " + selectedSpeed);
    }

    /**
     * Handles the "Next Step" action in the manual exploration mode.
     *
     * <p>This method is only applicable when the speed is set to manual. It logs
     * an error if the speed is not manual and performs the next step otherwise.
     */
    private void handleNextStepClicked() {
        if (selectedSpeed != Speed_Constants.MANUAL_VALUE) {
            System.err.println("Manual steps only allowed at manual speed.");
            return;
        }
        update();
    }

    /**
     * Handles the start action to begin cave exploration.
     *
     * <p>This method initializes perceptions in the cave, starts a background thread for
     * exploration, and updates the view in a loop. Exploration continues until the cave
     * is fully explored or the thread is interrupted.
     */
    private void handleStartClicked() {
        if (!canStart()) return;

        // Load perceptions
        model.getCave().updateAllPerceptions();

        new Thread(() -> {
            System.out.println("Cave started.");
            model.setStarted(true);
            while (!model.isCaveExplored()) {
                if (selectedSpeed == Speed_Constants.MANUAL_VALUE) {
                    continue;
                }
                update();
                try {
                    Thread.sleep(selectedSpeed);
                } catch (InterruptedException e) {
                    return;
                }
            }
            model.setStarted(false);
        }).start();
    }

    /**
     * Validates whether the cave exploration can start.
     *
     * <p>This method ensures that the cave has at least one monster, one treasure,
     * and one player before allowing the start of exploration. It also checks
     * that the game has not already started.
     *
     * @return {@code true} if the cave can start; {@code false} otherwise
     */
    private boolean canStart() {
        if (model.isStarted()) {
            System.err.println("Cave has already started.");
            return false;
        }

        Cave cave = model.getCave();
        if (cave.getAmountOfMonsters() == 0 || cave.getAmountOfTreasures() == 0 || cave.getAmountOfPlayers() == 0) {
            System.err.println("A monster, a treasure and a player are required to start.");
            return false;
        }

        return true;
    }

    /**
     * Handles updates to the cave and refreshes the view.
     *
     * <p>This method is triggered when the cave state changes, ensuring that
     * the view remains consistent with the model.
     */
    private void handleCaveUpdated() {
        view.updateView();
    }

    private void handleResetClicked() {
        model.reset();
        view.updateView();
        System.out.println("The cave has been reset.");
    }

    /**
     * Safely casts an object to an {@code int}.
     *
     * <p>If the provided parameter is not an {@code Integer}, this method
     * returns {@code 0} as a fallback.
     *
     * @param param the object to cast
     * @return the cast integer, or {@code 0} if the object is not an {@code Integer}
     */
    private int castToInt(Object param) {
        return param instanceof Integer ? (Integer) param : 0;
    }

    /**
     * Safely casts an object to a {@code byte}.
     *
     * <p>If the provided parameter is not a {@code Byte}, this method
     * returns {@code 0} as a fallback.
     *
     * @param param the object to cast
     * @return the cast byte, or {@code 0} if the object is not a {@code Byte}
     */
    private byte castToByte(Object param) {
        return param instanceof Byte ? (Byte) param : 0;
    }

    private void update() {
        model.exploreCave();
        view.updateView();
    }

}
