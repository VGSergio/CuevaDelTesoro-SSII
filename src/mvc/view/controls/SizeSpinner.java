package mvc.view.controls;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;

import static mvc.model.Global.Events_Constants;
import static mvc.model.Global.Maze_Constants;

public class SizeSpinner extends JPanel {

    private final JSpinner spinner;

    public SizeSpinner(Controller controller) {
        // Initialize components
        JLabel nLabel = new JLabel("N");
        nLabel.setToolTipText("Set the maze side size");

        spinner = new JSpinner(new SpinnerNumberModel(Maze_Constants.MIN_SIDE, Maze_Constants.MIN_SIDE, Maze_Constants.MAX_SIDE, 1));
        spinner.setToolTipText("Select the size of one side of the maze");

        spinner.addChangeListener(e -> controller.notify(Events_Constants.MAZE_SIDE_CHANGED, spinner.getValue()));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);

        // Configure layout
        setLayout(new BorderLayout());

        // Add components
        add(nLabel, BorderLayout.LINE_START);
        add(spinner, BorderLayout.CENTER);
    }
}
