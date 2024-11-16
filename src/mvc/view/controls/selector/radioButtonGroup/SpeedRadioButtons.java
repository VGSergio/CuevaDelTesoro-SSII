package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import java.util.Arrays;

import static mvc.model.Global.Events_Constants;
import static mvc.model.Global.Speed_Constants;

/**
 * A concrete implementation of {@link RadioButtonGroup} that provides a set of radio buttons
 * for selecting speed options, such as "Slow", "Normal", "Fast", and "Manual".
 *
 * <p>This class initializes the radio button group with predefined speed options using constants
 * defined in the {@link mvc.model.Global.Speed_Constants}. It notifies the controller whenever
 * the speed selection changes.
 *
 * <p>Usage:
 * <ul>
 *   <li>Use this class to create a user interface component for selecting speed settings.</li>
 *   <li>The selected speed is notified to the {@link Controller} with the
 *       {@link mvc.model.Global.Events_Constants#SPEED_CHANGED} action type.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see RadioButtonGroup
 * @see RadioButtonOption
 * @see Controller
 * @see Speed_Constants
 * @see Events_Constants
 */
public class SpeedRadioButtons extends RadioButtonGroup {

    /**
     * Constructs a {@code SpeedRadioButtons} group.
     *
     * <p>The group is associated with the {@link mvc.model.Global.Events_Constants#SPEED_CHANGED}
     * action type and will notify the {@link Controller} whenever a speed option is selected.
     *
     * @param controller the {@link Controller} that will handle speed change events
     */
    public SpeedRadioButtons(Controller controller) {
        super(controller, Events_Constants.SPEED_CHANGED);
    }

    /**
     * Initializes the components of the radio button group.
     *
     * <p>This method defines the available speed options for the group:
     * <ul>
     *   <li>Slow</li>
     *   <li>Normal</li>
     *   <li>Fast</li>
     *   <li>Manual</li>
     * </ul>
     * Each option is associated with a speed constant from {@link mvc.model.Global.Speed_Constants}.
     * The default selection is defined by {@link mvc.model.Global.Speed_Constants#DEFAULT}.
     */
    @Override
    protected void initComponents() {
        // Define the radio button options
        createRadioButtons(Arrays.asList(
                new RadioButtonOption("Slow", Speed_Constants.SLOW),
                new RadioButtonOption("Normal", Speed_Constants.NORMAL),
                new RadioButtonOption("Fast", Speed_Constants.FAST),
                new RadioButtonOption("Manual", Speed_Constants.MANUAL)
        ), Speed_Constants.DEFAULT);
    }
}
