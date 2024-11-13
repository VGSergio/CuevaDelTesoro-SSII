package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import java.util.Arrays;

import static mvc.model.Global.*;

public class SpeedRadioButtons extends RadioButtonGroup {

    public SpeedRadioButtons(Controller controller) {
        super(controller, SPEED_CHANGED);
    }

    @Override
    protected void initComponents() {
        // Define the radio button options
        createRadioButtons(Arrays.asList(
                new RadioButtonOption("Slow", SLOW_SPEED),
                new RadioButtonOption("Normal", NORMAL_SPEED),
                new RadioButtonOption("Fast", FAST_SPEED),
                new RadioButtonOption("Manual", MANUAL_SPEED)
        ), NORMAL_SPEED);
    }
}
