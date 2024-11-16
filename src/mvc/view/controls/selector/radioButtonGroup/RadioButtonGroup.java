package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * An abstract class representing a group of radio buttons within a JPanel.
 *
 * <p>This class provides a base implementation for creating and managing a group of
 * radio buttons, allowing event notifications to be sent to a {@link Controller}.
 * Subclasses must implement the {@link #initComponents()} method to initialize any
 * specific components.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create a subclass and implement the {@link #initComponents()} method.</li>
 *   <li>Use {@link #createRadioButtons(List, String)} to dynamically populate the
 *       group with radio buttons.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see JPanel
 * @see Controller
 * @see ButtonGroup
 */
public abstract class RadioButtonGroup extends JPanel {

    /**
     * The controller responsible for handling events from this radio button group.
     */
    protected final Controller controller;

    /**
     * The Swing ButtonGroup that manages the radio button selection.
     */
    private final ButtonGroup buttonGroup;

    /**
     * A string representing the type of action to notify the controller about.
     */
    private final String actionType;

    /**
     * Constructs a new {@code RadioButtonGroup}.
     *
     * @param controller the {@link Controller} that will handle the events triggered by this group
     * @param actionType a string representing the type of action to notify the controller about
     */
    public RadioButtonGroup(Controller controller, String actionType) {
        this.controller = controller;
        this.actionType = actionType;
        this.buttonGroup = new ButtonGroup();

        setLayout(new GridLayout(0, 2)); // Optionally customizable layout
        initComponents();
    }

    /**
     * Dynamically creates radio buttons based on the provided options and adds them to the panel.
     *
     * <p>The first button whose action command matches the {@code defaultCommand} will be
     * selected by default. If no match is found, an error message is logged.
     *
     * @param options        a {@link List} of {@link RadioButtonOption} representing the radio button options
     * @param defaultCommand the action command of the radio button to select by default
     */
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

    /**
     * Handles the action event triggered by a radio button click.
     *
     * <p>This method notifies the {@link Controller} with the specified action type
     * and the action command of the selected radio button.
     *
     * @param e the {@link ActionEvent} representing the radio button action
     */
    private void handleActionEvent(ActionEvent e) {
        controller.notify(actionType, e.getActionCommand());
    }

    /**
     * Initializes any specific components required for the radio button group.
     *
     * <p>Subclasses must implement this method to define how the group should be
     * initialized (e.g., adding static labels, predefined buttons, or other components).
     */
    protected abstract void initComponents();
}
