package mvc.view.controls.selector;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Selector extends JPanel implements ActionListener {

    protected final Controller controller;

    protected final int IMG_SIZE = 80;
    protected JLabel picture;

    public Selector(Controller controller) {
        this.controller = controller;
        initComponents();
    }

    protected void initComponents() {
    }

    protected ImageIcon loadImage(String path) {
        return new ImageIcon(
                new ImageIcon(path).                                                // load the image
                        getImage().                                                 // transform it
                        getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH)   // scale it
        );                                                                          // transform it back
    }

}
