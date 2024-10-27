package mvc.view.controls;

import mvc.controller.Controller;
import mvc.view.controls.selector.ElementSelector;
import mvc.view.controls.selector.SpeedSelector;

import javax.swing.*;
import java.awt.*;

public class Controls extends JPanel {

    private final ElementSelector ELEMENT_SELECTOR;

    public Controls(Controller controller, int windowWidth) {
        this.ELEMENT_SELECTOR = new ElementSelector(controller);

        // Initialize components
        SizeSpinner sizeSpinner = new SizeSpinner(controller);
        SpeedSelector speedSelector = new SpeedSelector(controller);
        StartPicture startPicture = new StartPicture(controller);

        // Configure panel
        configurePanel(windowWidth);

        // Add components
        addComponent(sizeSpinner, 0);
        addComponent(ELEMENT_SELECTOR, 1);
        addComponent(speedSelector, 2);
        addComponent(startPicture, 3);
    }

    private void configurePanel(int windowWidth) {
        setPreferredSize(new Dimension(windowWidth, 100));
        setLayout(new GridBagLayout());
    }

    private void addComponent(Component component, int gridX) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, gridX == 0 ? 5 : 30, 0, gridX == 3 ? 5 : 30);
        gbc.anchor = GridBagConstraints.CENTER;
        add(component, gbc);
    }

    public ElementSelector getElementSelector() {
        return ELEMENT_SELECTOR;
    }
}
