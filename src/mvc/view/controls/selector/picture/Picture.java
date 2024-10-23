package mvc.view.controls.selector.picture;

import mvc.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Picture extends JPanel {

    protected static final int IMG_SIZE = 80;
    private static final Map<String, ImageIcon> imageCache = new HashMap<>();
    private final JLabel pictureLabel; // JLabel to display the picture
    protected Controller controller;

    public Picture(Controller controller, String defaultImage) {
        this.controller = controller;
        this.pictureLabel = new JLabel();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));
        add(pictureLabel, BorderLayout.CENTER);

        setPicture(defaultImage);
    }

    private ImageIcon loadCachedImage(String path) {
        return imageCache.computeIfAbsent(path, p -> {
            Image image = new ImageIcon(p).getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        });
    }

    public void setPicture(String image) {
        pictureLabel.setIcon(loadCachedImage(image)); // Set the icon on the internal JLabel
    }
}
