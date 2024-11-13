package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public abstract class RadioButtonGroup extends JPanel {

    protected final Controller controller;
    private final ButtonGroup buttonGroup;
    private final String actionType;

    public RadioButtonGroup(Controller controller, String actionType) {
        this.controller = controller;
        this.buttonGroup = new ButtonGroup();
        this.actionType = actionType;

        setLayout(new GridLayout(0, 2)); // Optionally customizable layout
        initComponents();
    }

    protected void createRadioButtons(List<RadioButtonOption> options, String defaultCommand) {
        boolean defaultSet = false;
        for (RadioButtonOption option : options) {
            JRadioButton button = new JRadioButton(option.label());
            button.setActionCommand(option.actionCommand());
            buttonGroup.add(button);
            button.addActionListener(this::handleActionEvent);
            add(button);

            // Select default button if command matches
            if (option.actionCommand().equals(defaultCommand)) {
                button.setSelected(true);
                defaultSet = true;
            }
        }

        // Log if the default selection was not found
        if (!defaultSet) {
            System.err.println("Default command not found in options: " + defaultCommand);
        }
    }

    private void handleActionEvent(ActionEvent e) {
        // Notify the controller with the action type and command of the selected radio button
        controller.notify(actionType, e.getActionCommand());
    }

    protected abstract void initComponents();
}
