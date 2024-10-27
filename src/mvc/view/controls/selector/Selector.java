package mvc.view.controls.selector;

import javax.swing.*;
import java.awt.*;

public class Selector<T extends JPanel, U extends JPanel> extends JPanel {

    private final U picture;

    public Selector(T radioButtons, U picture) {
        this.picture = picture;

        // Configure panel
        setLayout(new BorderLayout());

        // Add components
        add(radioButtons, BorderLayout.LINE_START);
        add(picture, BorderLayout.CENTER);
    }

    public U getPicture() {
        return picture;
    }
}
