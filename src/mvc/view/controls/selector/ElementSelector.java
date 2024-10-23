package mvc.view.controls.selector;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static mvc.model.Global.*;

public class ElementSelector extends Selector {


    public ElementSelector(Controller controller) {
        super(controller);
    }

    protected void initComponents() {
        // Configure the radio buttons
        JRadioButton monsterButton = new JRadioButton("Monster");
        monsterButton.setActionCommand(MONSTER_IMAGE);

        JRadioButton holeButton = new JRadioButton("Hole");
        holeButton.setActionCommand(HOLE_IMAGE);

        JRadioButton treasureButton = new JRadioButton("Treasure");
        treasureButton.setActionCommand(TREASURE_IMAGE);

        JRadioButton cleanButton = new JRadioButton("Clean");
        cleanButton.setActionCommand(CLEAN_IMAGE);
        cleanButton.setSelected(true);

        // Group the radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(monsterButton);
        buttonGroup.add(holeButton);
        buttonGroup.add(treasureButton);
        buttonGroup.add(cleanButton);

        // Add a listener to the radio buttons
        monsterButton.addActionListener(this);
        holeButton.addActionListener(this);
        treasureButton.addActionListener(this);
        cleanButton.addActionListener(this);

        //Set up the picture label.
        picture = new JLabel(loadImage(CLEAN_IMAGE));

        // Size for the picture
        picture.setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));

        // Places the radio buttons in a 2x2 matrix
        JPanel radioPanel = new JPanel(new GridLayout(2, 2));
        radioPanel.add(monsterButton);
        radioPanel.add(holeButton);
        radioPanel.add(treasureButton);
        radioPanel.add(cleanButton);

        add(radioPanel, BorderLayout.LINE_START);
        add(picture, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        controller.notify(ELEMENT_CHANGED, e.getActionCommand());
        picture.setIcon(loadImage(e.getActionCommand()));
    }

}
