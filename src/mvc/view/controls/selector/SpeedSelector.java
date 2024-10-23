package mvc.view.controls.selector;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static mvc.model.Global.*;

public class SpeedSelector extends Selector {

    public SpeedSelector(Controller controller) {
        super(controller);
    }

    protected void initComponents() {
        // Configure the radio buttons
        JRadioButton slowButton = new JRadioButton("Slow");
        slowButton.setActionCommand(SLOW_SPEED);

        JRadioButton normalButton = new JRadioButton("Normal");
        normalButton.setActionCommand(NORMAL_SPEED);
        normalButton.setSelected(true);

        JRadioButton fastButton = new JRadioButton("Fast");
        fastButton.setActionCommand(FAST_SPEED);

        JRadioButton manualButton = new JRadioButton("Manual");
        manualButton.setActionCommand(MANUAL_SPEED);

        // Group the radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(slowButton);
        buttonGroup.add(normalButton);
        buttonGroup.add(fastButton);
        buttonGroup.add(manualButton);

        // Add a listener to the radio buttons
        slowButton.addActionListener(this);
        normalButton.addActionListener(this);
        fastButton.addActionListener(this);
        manualButton.addActionListener(this);

        // Set up the picture label.
        picture = new JLabel(loadImage(NEXT_IMAGE));

        // Size for the picture
        picture.setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));

        // Places the radio buttons in a 2x2 matrix
        JPanel radioPanel = new JPanel(new GridLayout(2, 2));
        radioPanel.add(slowButton);
        radioPanel.add(normalButton);
        radioPanel.add(fastButton);
        radioPanel.add(manualButton);

        add(radioPanel, BorderLayout.LINE_START);
        add(picture, BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        controller.notify(SPEED_CHANGED, e.getActionCommand());
    }
}
