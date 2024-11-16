package mvc.view;

import mvc.controller.Controller;
import mvc.model.maze.MazeModel;
import mvc.view.controls.Controls;

import javax.swing.*;

/**
 * The main application window for the maze application.
 *
 * <p>This class extends {@link JFrame} and serves as the primary view in the MVC architecture.
 * It combines the {@link Controls} panel for user interactions and the {@link MazeView} panel
 * for visualizing the maze. The layout and components are configured for a seamless user experience.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by passing a {@link Controller} and a {@link MazeModel}.</li>
 *   <li>Call {@link #updateView()} to refresh the maze view after changes to the model.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see JFrame
 * @see Controls
 * @see MazeView
 * @see mvc.controller.Controller
 * @see mvc.model.maze.MazeModel
 * <p>
 */
public class View extends javax.swing.JFrame {

    /**
     * The controls panel for user interactions.
     */
    private final Controls controls;

    /**
     * The panel for displaying the maze.
     */
    private final MazeView mazeView;

    /**
     * Constructs a {@code View} with the specified controller and maze model.
     *
     * <p>The view is initialized with a fixed width of 800 pixels, and it combines
     * the {@link Controls} panel and the {@link MazeView} panel in a vertical layout.
     *
     * @param controller the {@link Controller} to handle user interactions
     * @param mazeModel  the {@link MazeModel} representing the maze data
     */
    public View(Controller controller, MazeModel mazeModel) {
        int width = 800;

        controls = new Controls(controller, width);
        mazeView = new MazeView(controller, width, mazeModel);

        configure();
        initComponents();
        showView();
    }

    /**
     * Configures the layout of the frame.
     *
     * <p>The content pane is set to use a vertical {@link BoxLayout},
     * arranging the {@link Controls} panel above the {@link MazeView} panel.
     */
    private void configure() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    /**
     * Initializes and adds the components to the frame.
     *
     * <p>The {@link Controls} panel and {@link MazeView} panel are added to the frame
     * in the configured vertical layout.
     */
    private void initComponents() {
        add(controls);
        add(mazeView);
    }

    /**
     * Configures the frame's properties and makes it visible.
     *
     * <p>The frame is set to:
     * <ul>
     *   <li>Not be resizable</li>
     *   <li>Have a default close operation of {@code EXIT_ON_CLOSE}</li>
     *   <li>Be titled "Cave of the treasure"</li>
     *   <li>Be centered on the screen</li>
     * </ul>
     */
    private void showView() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Cave of the treasure");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Returns the {@link Controls} panel for accessing user interaction components.
     *
     * @return the {@link Controls} instance
     */
    public Controls getControls() {
        return controls;
    }

    /**
     * Updates the view by refreshing the maze display.
     *
     * <p>This method invokes {@link MazeView#updateMaze()} to redraw the maze
     * based on the current state of the {@link MazeModel}.
     */
    public void updateView() {
        mazeView.updateMaze();
    }
}
