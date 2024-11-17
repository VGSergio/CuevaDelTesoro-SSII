package mvc.view;

import mvc.controller.Controller;
import mvc.model.cave.Cave;
import mvc.view.controls.Controls;

import javax.swing.*;

/**
 * The main application window for the cave application.
 *
 * <p>This class extends {@link JFrame} and serves as the primary view in the MVC architecture.
 * It combines the {@link Controls} panel for user interactions and the {@link CaveView} panel
 * for visualizing the cave. The layout and components are configured for a seamless user experience.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by passing a {@link Controller} and a {@link Cave}.</li>
 *   <li>Call {@link #updateView()} to refresh the cave view after changes to the model.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see JFrame
 * @see Controls
 * @see CaveView
 * @see mvc.controller.Controller
 * @see Cave
 * <p>
 */
public class View extends javax.swing.JFrame {

    /**
     * The controls panel for user interactions.
     */
    private final Controls controls;

    /**
     * The panel for displaying the cave.
     */
    private final CaveView caveView;

    /**
     * Constructs a {@code View} with the specified controller and cave model.
     *
     * <p>The view is initialized with a fixed width of 800 pixels, and it combines
     * the {@link Controls} panel and the {@link CaveView} panel in a vertical layout.
     *
     * @param controller the {@link Controller} to handle user interactions
     * @param cave       the {@link Cave} representing the cave data
     */
    public View(Controller controller, Cave cave) {
        int width = 800;

        controls = new Controls(controller, width);
        caveView = new CaveView(controller, width, cave);

        configure();
        initComponents();
        showView();
    }

    /**
     * Configures the layout of the frame.
     *
     * <p>The content pane is set to use a vertical {@link BoxLayout},
     * arranging the {@link Controls} panel above the {@link CaveView} panel.
     */
    private void configure() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    /**
     * Initializes and adds the components to the frame.
     *
     * <p>The {@link Controls} panel and {@link CaveView} panel are added to the frame
     * in the configured vertical layout.
     */
    private void initComponents() {
        add(controls);
        add(caveView);
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
     * Updates the view by refreshing the cave display.
     *
     * <p>This method invokes {@link CaveView#updateCave()} to redraw the cave
     * based on the current state of the {@link Cave}.
     */
    public void updateView() {
        caveView.updateCave();
    }
}
