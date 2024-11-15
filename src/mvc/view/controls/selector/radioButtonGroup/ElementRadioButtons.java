package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import java.util.Arrays;

import static mvc.model.Global.Events_Constants;
import static mvc.model.Global.Images_Constants;

public class ElementRadioButtons extends RadioButtonGroup {

    public ElementRadioButtons(Controller controller) {
        super(controller, Events_Constants.ELEMENT_CHANGED);
    }

    @Override
    protected void initComponents() {
        // Define the radio button options
        createRadioButtons(Arrays.asList(
                new RadioButtonOption("Monster", Images_Constants.MONSTER),
                new RadioButtonOption("Hole", Images_Constants.HOLE),
                new RadioButtonOption("Treasure", Images_Constants.TREASURE),
                new RadioButtonOption("Clean", Images_Constants.CLEAN),
                new RadioButtonOption("Player", Images_Constants.PLAYER)
        ), Images_Constants.CLEAN);
    }
}
