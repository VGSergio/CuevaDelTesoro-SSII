package mvc.view.controls;

import mvc.controller.Controller;
import mvc.view.controls.selector.ElementSelector;
import mvc.view.controls.selector.SpeedSelector;
import mvc.view.controls.selector.picture.StartPicture;

import javax.swing.*;
import java.awt.*;

public class Controls extends JPanel {

    private final ElementSelector elementSelector;
    private final SizeSpinner sizeSpinner;
    private final SpeedSelector speedSelector;
    private final StartPicture startPicture;

    public Controls(Controller controller, int windowWidth) {
        // Initialize components
        elementSelector = new ElementSelector(controller);
        sizeSpinner = new SizeSpinner(controller);
        speedSelector = new SpeedSelector(controller);
        startPicture = new StartPicture(controller);

        // Configure panel and add components
        configurePanel(windowWidth);
        initComponents();
    }

    private void configurePanel(int windowWidth) {
        setPreferredSize(new Dimension(windowWidth, 100));
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        addComponent(sizeSpinner, 0);
        addComponent(elementSelector, 1);
        addComponent(speedSelector, 2);
        addComponent(startPicture, 3);
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
        return elementSelector;
    }
}
