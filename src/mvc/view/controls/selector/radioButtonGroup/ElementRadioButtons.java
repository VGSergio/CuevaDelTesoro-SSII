package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import java.util.Arrays;

import static mvc.model.Global.Events_Constants;
import static mvc.model.Global.Images_Constants;

/**
 * A concrete implementation of {@link RadioButtonGroup} that represents a set of radio buttons
 * for selecting elements like "Monster", "Hole", "Treasure", etc.
 *
 * <p>This class initializes the radio button group with predefined options related to game elements,
 * such as Monster, Hole, and Player, using constants defined in the {@link mvc.model.Global} class.
 * It notifies the controller when the selected element changes.
 *
 * @author Sergio Vega García
 * @see RadioButtonGroup
 * @see Events_Constants
 * @see Images_Constants
 * @see Controller
 */
public class ElementRadioButtons extends RadioButtonGroup {

    /**
     * Constructs an {@code ElementRadioButtons} group.
     *
     * <p>The group is associated with the {@link mvc.model.Global.Events_Constants#ELEMENT_CHANGED}
     * action type and will notify the {@link Controller} whenever a selection is made.
     *
     * @param controller the {@link Controller} that will handle element change events
     */
    public ElementRadioButtons(Controller controller) {
        super(controller, Events_Constants.ELEMENT_CHANGED);
    }

    /**
     * Initializes the components of the radio button group.
     *
     * <p>This method defines the available options for the group:
     * <ul>
     *   <li>Monster</li>
     *   <li>Hole</li>
     *   <li>Treasure</li>
     *   <li>Clean</li>
     *   <li>Player</li>
     * </ul>
     * Each option is associated with an image constant from {@link mvc.model.Global.Images_Constants}.
     * The default selection is defined by {@link mvc.model.Global.Images_Constants#DEFAULT}.
     */
    @Override
    protected void initComponents() {
        // Define the radio button options
        createRadioButtons(Arrays.asList(
                new RadioButtonOption("Monster", Images_Constants.MONSTER),
                new RadioButtonOption("Hole", Images_Constants.HOLE),
                new RadioButtonOption("Treasure", Images_Constants.TREASURE),
                new RadioButtonOption("Clean", Images_Constants.CLEAN),
                new RadioButtonOption("Player", Images_Constants.PLAYER)
        ), Images_Constants.DEFAULT);
    }
}
