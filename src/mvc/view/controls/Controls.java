package mvc.view.controls;

import mvc.controller.Controller;
import mvc.view.controls.selector.ElementSelector;
import mvc.view.controls.selector.SpeedSelector;

import javax.swing.*;
import java.awt.*;

public class Controls extends JPanel {

    private final ElementSelector ELEMENT_SELECTOR;

    public Controls(Controller controller, int windowWidth) {
        this.ELEMENT_SELECTOR = new ElementSelector(controller);

        JLabel nLabel = new JLabel("N");
        SizeSpinner sizeSpinner = new SizeSpinner(controller);
        SpeedSelector speedSelector = new SpeedSelector(controller);

        setPreferredSize(new Dimension(windowWidth, 100));
        setLayout(new GridBagLayout());

        add(nLabel);
        add(sizeSpinner);
        add(ELEMENT_SELECTOR);
        add(speedSelector);
    }

    public ElementSelector getElementSelector() {
        return ELEMENT_SELECTOR;
    }
}
