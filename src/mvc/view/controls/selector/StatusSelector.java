package mvc.view.controls.selector;

import mvc.controller.Controller;
import mvc.view.controls.selector.picture.StatusPicture;
import mvc.view.controls.selector.radioButtonGroup.StatusRadioButtons;

/**
 * A specialized implementation of {@link Selector} that pairs an {@link StatusRadioButtons}
 * component with an {@link StatusPicture} component.
 *
 * <p>This class is designed to allow users to select a status (e.g., Monster, Hole, etc.)
 * via radio buttons and display the corresponding image. It leverages the generic
 * {@link Selector} class to combine these components into a cohesive UI element.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by passing a {@link Controller} to handle events from the radio buttons.</li>
 *   <li>The {@link StatusRadioButtons} component allows status selection.</li>
 *   <li>The {@link StatusPicture} component displays the selected status's image.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see Selector
 * @see StatusRadioButtons
 * @see StatusPicture
 * @see mvc.controller.Controller
 */
public class StatusSelector extends Selector<StatusRadioButtons, StatusPicture> {

    /**
     * Constructs an {@code statusSelector} with the given controller.
     *
     * <p>The selector combines an {@link StatusRadioButtons} component for status selection
     * and an {@link StatusPicture} component for displaying the selected status's image.
     *
     * @param controller the {@link Controller} responsible for handling events from the radio buttons
     */
    public StatusSelector(Controller controller) {
        super(new StatusRadioButtons(controller), new StatusPicture());
    }
}
