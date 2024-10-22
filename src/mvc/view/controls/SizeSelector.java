package mvc.view.controls;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;

import static mvc.model.Global.*;

public class SizeSelector extends JSpinner {

    private final Controller controller;

    public SizeSelector(Controller controller) {
        this.controller = controller;

        setModel(new SpinnerNumberModel(MIN_MAZE_SIDE, MIN_MAZE_SIDE, MAX_MAZE_SIDE, 1));
        changeListener();
    }

    public Dimension getPreferredSize() {
        return new Dimension(100,50);
    }

    private void changeListener() {
        addChangeListener(e -> {
            controller.notify(MAZE_SIDE_CHANGED, (Integer) getValue());
        });
    }
}
