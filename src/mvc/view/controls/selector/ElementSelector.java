package mvc.view.controls.selector;

import mvc.controller.Controller;
import mvc.view.controls.selector.picture.ElementPicture;
import mvc.view.controls.selector.radioButtonGroup.ElementRadioButtons;

public class ElementSelector extends Selector<ElementRadioButtons, ElementPicture> {

    public ElementSelector(Controller controller) {
        super(new ElementRadioButtons(controller), new ElementPicture());
    }
}
