package mvc.view.controls;

import mvc.controller.Controller;
import mvc.view.controls.selector.ElementSelector;
import mvc.view.controls.selector.SpeedSelector;

import javax.swing.*;
import java.awt.*;

public class Controls extends JPanel {

    private final JLabel mazeSide;
    private final SizeSelector sizeSelector;
    private final ElementSelector elementSelector;
    private final SpeedSelector speedSelector;

    public Controls(Controller controller, int windowWidth) {
        this.mazeSide = new JLabel("N");
        this.sizeSelector = new SizeSelector(controller);
        this.elementSelector = new ElementSelector(controller);
        this.speedSelector = new SpeedSelector(controller);


        setPreferredSize(new Dimension(windowWidth, 100));
        setLayout(new GridBagLayout());

        add(mazeSide);
        add(sizeSelector);
        add(elementSelector);
        add(speedSelector);
    }

    public JLabel getMazeSide() {
        return mazeSide;
    }

    public SizeSelector getSizeSelector() {
        return sizeSelector;
    }

    public ElementSelector getElementSelector() {
        return elementSelector;
    }

    public SpeedSelector getSpeedSelector() {
        return speedSelector;
    }
}
