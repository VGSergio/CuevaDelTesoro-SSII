package mvc.view.controls;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;

import static mvc.model.Global.Events_Constants;
import static mvc.model.Global.Cave_Constants;

/**
 * A JPanel-based component for selecting the side of a cave.
 *
 * <p>This class provides a labeled spinner that allows users to set the side length of a cave.
 * It uses a {@link JSpinner} to limit the side to a range defined by
 * {@link Cave_Constants#MIN_SIDE} and {@link Cave_Constants#MAX_SIDE}.
 * When the spinner value changes, it notifies the {@link Controller} with the event
 * {@link mvc.model.Global.Events_Constants#CAVE_SIDE_CHANGED}.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by passing a {@link Controller} to handle side change events.</li>
 *   <li>The spinner is non-editable, ensuring only valid numeric values can be selected.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see JSpinner
 * @see mvc.controller.Controller
 * @see Cave_Constants
 * @see mvc.model.Global.Events_Constants
 */
public class SideSpinner extends JPanel {

    /**
     * The spinner component for selecting the cave side.
     */
    private final JSpinner spinner;

    /**
     * Constructs a {@code SideSpinner} with the specified controller.
     *
     * <p>This constructor initializes the spinner with a range of valid sides
     * (from {@link Cave_Constants#MIN_SIDE} to {@link Cave_Constants#MAX_SIDE}) and
     * attaches a listener to notify the {@link Controller} when the value changes.
     *
     * @param controller the {@link Controller} responsible for handling side change events
     */
    public SideSpinner(Controller controller) {
        // Initialize components
        JLabel nLabel = new JLabel("N");
        nLabel.setToolTipText("Set the cave side");

        spinner = new JSpinner(new SpinnerNumberModel(Cave_Constants.MIN_SIDE, Cave_Constants.MIN_SIDE, Cave_Constants.MAX_SIDE, 1));
        spinner.setToolTipText("Select the side of the cave");

        // Add change listener to notify the controller when the spinner value changes
        spinner.addChangeListener(e -> controller.notify(Events_Constants.CAVE_SIDE_CHANGED, spinner.getValue()));

        // Make the spinner text field non-editable
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);

        // Configure layout
        setLayout(new BorderLayout());

        // Add components
        add(nLabel, BorderLayout.LINE_START);
        add(spinner, BorderLayout.CENTER);
    }
}
