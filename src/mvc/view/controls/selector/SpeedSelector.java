package mvc.view.controls.selector;

import mvc.controller.Controller;
import mvc.view.controls.selector.picture.SpeedPicture;
import mvc.view.controls.selector.radioButtonGroup.SpeedRadioButtons;

/**
 * A specialized implementation of {@link Selector} that pairs a {@link SpeedRadioButtons}
 * component with a {@link SpeedPicture} component.
 *
 * <p>This class allows users to select a speed setting (e.g., Slow, Normal, Fast, Manual)
 * via radio buttons and displays a speed-related image. It utilizes the generic
 * {@link Selector} class to organize these components into a cohesive UI element.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by passing a {@link Controller} to handle events from the radio buttons.</li>
 *   <li>The {@link SpeedRadioButtons} component allows users to select a speed setting.</li>
 *   <li>The {@link SpeedPicture} component reacts to events, such as clicks, and notifies the controller.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see Selector
 * @see SpeedRadioButtons
 * @see SpeedPicture
 * @see mvc.controller.Controller
 */
public class SpeedSelector extends Selector<SpeedRadioButtons, SpeedPicture> {

    /**
     * Constructs a {@code SpeedSelector} with the given controller.
     *
     * <p>The selector combines a {@link SpeedRadioButtons} component for speed selection
     * and a {@link SpeedPicture} component for displaying and interacting with speed-related events.
     *
     * @param controller the {@link Controller} responsible for handling events from the components
     */
    public SpeedSelector(Controller controller) {
        super(new SpeedRadioButtons(controller), new SpeedPicture(controller));
    }
}
