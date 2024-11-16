package mvc.view.controls;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;

import static mvc.model.Global.Events_Constants;
import static mvc.model.Global.Maze_Constants;

/**
 * A JPanel-based component for selecting the size of a maze.
 *
 * <p>This class provides a labeled spinner that allows users to set the side length of a maze.
 * It uses a {@link JSpinner} to limit the size to a range defined by
 * {@link mvc.model.Global.Maze_Constants#MIN_SIDE} and {@link mvc.model.Global.Maze_Constants#MAX_SIDE}.
 * When the spinner value changes, it notifies the {@link Controller} with the event
 * {@link mvc.model.Global.Events_Constants#MAZE_SIDE_CHANGED}.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by passing a {@link Controller} to handle size change events.</li>
 *   <li>The spinner is non-editable, ensuring only valid numeric values can be selected.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see JSpinner
 * @see mvc.controller.Controller
 * @see mvc.model.Global.Maze_Constants
 * @see mvc.model.Global.Events_Constants
 */
public class SizeSpinner extends JPanel {

    /**
     * The spinner component for selecting the maze side size.
     */
    private final JSpinner spinner;

    /**
     * Constructs a {@code SizeSpinner} with the specified controller.
     *
     * <p>This constructor initializes the spinner with a range of valid sizes
     * (from {@link Maze_Constants#MIN_SIDE} to {@link Maze_Constants#MAX_SIDE}) and
     * attaches a listener to notify the {@link Controller} when the value changes.
     *
     * @param controller the {@link Controller} responsible for handling size change events
     */
    public SizeSpinner(Controller controller) {
        // Initialize components
        JLabel nLabel = new JLabel("N");
        nLabel.setToolTipText("Set the maze side size");

        spinner = new JSpinner(new SpinnerNumberModel(Maze_Constants.MIN_SIDE, Maze_Constants.MIN_SIDE, Maze_Constants.MAX_SIDE, 1));
        spinner.setToolTipText("Select the size of one side of the maze");

        // Add change listener to notify the controller when the spinner value changes
        spinner.addChangeListener(e -> controller.notify(Events_Constants.MAZE_SIDE_CHANGED, spinner.getValue()));

        // Make the spinner text field non-editable
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);

        // Configure layout
        setLayout(new BorderLayout());

        // Add components
        add(nLabel, BorderLayout.LINE_START);
        add(spinner, BorderLayout.CENTER);
    }
}
