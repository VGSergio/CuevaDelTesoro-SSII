package mvc.view.controls.selector;

import mvc.controller.Controller;
import mvc.view.controls.selector.picture.SpeedPicture;
import mvc.view.controls.selector.radioButtonGroup.SpeedRadioButtons;

public class SpeedSelector extends Selector<SpeedRadioButtons, SpeedPicture> {

    public SpeedSelector(Controller controller) {
        super(new SpeedRadioButtons(controller), new SpeedPicture(controller));
    }
}
