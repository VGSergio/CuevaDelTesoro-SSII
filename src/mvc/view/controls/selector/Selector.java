package mvc.view.controls.selector;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Selector<T extends JPanel, U extends JPanel> extends JPanel {

    private final T radioButtons;
    private final U picture;

    public Selector(Controller controller, T radioButtons, U picture) {
        this.radioButtons = radioButtons;
        this.picture = picture;

        setLayout(new BorderLayout());
        add(radioButtons, BorderLayout.LINE_START);
        add(picture, BorderLayout.CENTER);
    }

    public T getRadioButtons() {
        return radioButtons;
    }

    public U getPicture() {
        return picture;
    }
}
