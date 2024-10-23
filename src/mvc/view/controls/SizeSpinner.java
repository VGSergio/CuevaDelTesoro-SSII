package mvc.view.controls;

import mvc.controller.Controller;

import javax.swing.*;

import static mvc.model.Global.*;

public class SizeSpinner extends JSpinner {

    private final Controller controller;

    public SizeSpinner(Controller controller) {
        this.controller = controller;
        configure();
    }

    private void configure() {
        setModel(new SpinnerNumberModel(MIN_MAZE_SIDE, MIN_MAZE_SIDE, MAX_MAZE_SIDE, 1));
        changeListener();
    }

    private void changeListener() {
        addChangeListener(e -> controller.notify(MAZE_SIDE_CHANGED, getValue()));
    }
}
