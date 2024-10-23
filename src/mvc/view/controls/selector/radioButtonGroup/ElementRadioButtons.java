package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import static mvc.model.Global.*;

public class ElementRadioButtons extends RadioButtonGroup {

    public ElementRadioButtons(Controller controller) {
        super(controller);
    }

    @Override
    protected void initComponents() {
        // Define the radio button options
        createRadioButtons(Arrays.asList(
                new RadioButtonOption("Monster", MONSTER_IMAGE),
                new RadioButtonOption("Hole", HOLE_IMAGE),
                new RadioButtonOption("Treasure", TREASURE_IMAGE),
                new RadioButtonOption("Clean", CLEAN_IMAGE)
        ), CLEAN_IMAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.notify(ELEMENT_CHANGED, e.getActionCommand());
    }
}
