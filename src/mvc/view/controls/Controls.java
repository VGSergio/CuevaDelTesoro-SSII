package mvc.view.controls;

import mvc.controller.Controller;
import mvc.view.controls.selector.ElementSelector;
import mvc.view.controls.selector.SpeedSelector;

import javax.swing.*;
import java.awt.*;

public class Controls extends JPanel {

    public Controls(Controller controller, int windowWidth) {

        configure(windowWidth);
        initComponents(controller);
    }

    private void configure(int width) {
        setPreferredSize(new Dimension(width, 100));
        setLayout(new GridBagLayout());
    }

    private void initComponents(Controller controller) {
        add(new JLabel("N"));
        add(new SizeSelector(controller));
        add(new ElementSelector(controller));
        add(new SpeedSelector(controller));
    }

}
