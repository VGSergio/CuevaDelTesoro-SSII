package mvc.view.controls;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;

import static mvc.model.Global.*;

public class SizeSpinner extends JPanel {

    private final JSpinner spinner;

    public SizeSpinner(Controller controller) {
        // Initialize components
        JLabel nLabel = new JLabel("N");
        nLabel.setToolTipText("Set the maze side size");

        spinner = new JSpinner(new SpinnerNumberModel(MIN_MAZE_SIDE, MIN_MAZE_SIDE, MAX_MAZE_SIDE, 1));
        spinner.setToolTipText("Select the size of one side of the maze");

        spinner.addChangeListener(e -> controller.notify(MAZE_SIDE_CHANGED, spinner.getValue()));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);

        // Configure layout
        setLayout(new BorderLayout());

        // Add components
        add(nLabel, BorderLayout.LINE_START);
        add(spinner, BorderLayout.CENTER);
    }
}
