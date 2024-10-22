package mvc.view.controls;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Controls extends JPanel {

    private final int WINDOW_WIDTH;

    public Controls(Controller controller, int windowsWidth) {
        this.WINDOW_WIDTH = windowsWidth;

        configure();
        initComponents(controller);
    }

    private void configure(){
        setLayout(new GridBagLayout());
    }

    private void initComponents(Controller controller) {
        add(new JLabel("N"));
        add(new SizeSelector(controller));
    }

    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, 100);
    }

}
