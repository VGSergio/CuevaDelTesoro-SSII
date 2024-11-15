package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import java.util.Arrays;

import static mvc.model.Global.Events_Constants;
import static mvc.model.Global.Speed_Constants;

public class SpeedRadioButtons extends RadioButtonGroup {

    public SpeedRadioButtons(Controller controller) {
        super(controller, Events_Constants.SPEED_CHANGED);
    }

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
