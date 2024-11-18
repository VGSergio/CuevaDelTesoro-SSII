package mvc.view.controls;

import mvc.controller.Controller;
import mvc.view.controls.selector.SpeedSelector;
import mvc.view.controls.selector.StatusSelector;
import mvc.view.controls.selector.picture.ResetPicture;
import mvc.view.controls.selector.picture.StartPicture;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel-based component that serves as the main control panel of the application.
 *
 * <p>This class provides a user interface for controlling various aspects of the application,
 * such as status selection, side adjustment, speed selection, and starting actions. It organizes
 * these controls into a grid layout for a clean and intuitive layout.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance of this class by providing a {@link Controller} and the desired window width.</li>
 *   <li>The control panel includes:
 *       <ul>
 *           <li>{@link StatusSelector} for status-related options.</li>
 *           <li>{@link SideSpinner} for adjusting the side.</li>
 *           <li>{@link SpeedSelector} for speed selection.</li>
 *           <li>{@link StartPicture} for initiating actions via a clickable image.</li>
 *       </ul>
 *   </li>
 *   <li>The panel uses {@link GridBagLayout} for flexible component arrangement.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see StatusSelector
 * @see SpeedSelector
 * @see StartPicture
 * @see SideSpinner
 * @see mvc.controller.Controller
 * <p>
 */
public class Controls extends JPanel {

    /**
     * Picture component for reset actions.
     */
    private final ResetPicture resetPicture;

    /**
     * Spinner for adjusting side-related parameters.
     */
    private final SideSpinner sideSpinner;

    /**
     * Selector for status-related options.
     */
    private final StatusSelector statusSelector;

    /**
     * Selector for speed-related options.
     */
    private final SpeedSelector speedSelector;

    /**
     * Picture component for starting actions.
     */
    private final StartPicture startPicture;

    /**
     * Constructs a {@code Controls} panel with the specified controller and window width.
     *
     * <p>This constructor initializes all subcomponents and organizes them into a panel
     * using a {@link GridBagLayout}.
     *
     * @param controller  the {@link Controller} responsible for handling user interactions
     * @param windowWidth the width of the panel, used to set its preferred size
     */
    public Controls(Controller controller, int windowWidth) {
        // Initialize components
        resetPicture = new ResetPicture(controller);
        sideSpinner = new SideSpinner(controller);
        statusSelector = new StatusSelector(controller);
        speedSelector = new SpeedSelector(controller);
        startPicture = new StartPicture(controller);

        // Configure panel and add components
        configurePanel(windowWidth);
        initComponents();
    }

    /**
     * Configures the panel's layout and size.
     *
     * <p>The layout is set to {@link GridBagLayout}, and the preferred size is defined
     * based on the provided window width.
     *
     * @param windowWidth the width of the panel
     */
    private void configurePanel(int windowWidth) {
        setPreferredSize(new Dimension(windowWidth, 100));
        setLayout(new GridBagLayout());
    }

    /**
     * Initializes and adds components to the panel.
     *
     * <p>Each component is added to the panel with specific constraints to ensure proper alignment.
     */
    private void initComponents() {
        byte gridX = 0;
        addComponent(resetPicture, gridX++);
        addComponent(sideSpinner, gridX++);
        addComponent(statusSelector, gridX++);
        addComponent(speedSelector, gridX++);
        addComponent(startPicture, gridX);
    }

    /**
     * Adds a component to the panel with specific grid constraints.
     *
     * @param component the component to add
     * @param gridX     the grid column position for the component
     */
    private void addComponent(Component component, int gridX) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, gridX == 0 ? 5 : 15, 0, gridX == 0 ? 5 : 15);
        gbc.anchor = GridBagConstraints.CENTER;
        add(component, gbc);
    }

    /**
     * Returns the {@link StatusSelector} component of this panel.
     *
     * @return the {@link StatusSelector} instance
     */
    public StatusSelector getStatusSelector() {
        return statusSelector;
    }
}
