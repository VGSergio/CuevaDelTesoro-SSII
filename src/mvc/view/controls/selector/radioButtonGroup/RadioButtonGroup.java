package mvc.view.controls.selector.radioButtonGroup;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class RadioButtonGroup extends JPanel implements ActionListener {

    private final ButtonGroup buttonGroup;
    protected Controller controller;

    public RadioButtonGroup(Controller controller) {
        this.controller = controller;
        this.buttonGroup = new ButtonGroup();
        setLayout(new GridLayout(2, 2));
        initComponents();
    }

    protected void createRadioButtons(List<RadioButtonOption> options, String defaultCommand) {
        for (RadioButtonOption option : options) {
            JRadioButton button = new JRadioButton(option.label());
            button.setActionCommand(option.actionCommand());
            buttonGroup.add(button);
            button.addActionListener(this);
            add(button);
            if (option.actionCommand().equals(defaultCommand)) {
                button.setSelected(true);
            }
        }
    }

    protected abstract void initComponents();
}
