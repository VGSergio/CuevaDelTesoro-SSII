package mvc.view.controls.selector.picture;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class Picture extends JPanel {

    protected static final byte IMG_SIZE = 80;
    private static final Map<String, ImageIcon> IMAGE_CACHE = new HashMap<>();
    private final JLabel pictureLabel; // JLabel to display the picture

    public Picture(String defaultImage) {
        pictureLabel = new JLabel();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));
        add(pictureLabel, BorderLayout.CENTER);

        setPicture(defaultImage);
    }

    /**
     * Loads an image from the cache or creates a new scaled instance if not cached.
     *
     * @param path the path to the image file
     * @return the scaled ImageIcon or a default error icon if file not found
     */
    private ImageIcon loadCachedImage(String path) {
        if (path == null || path.isEmpty()) {
            System.err.println("Image path is invalid: " + path);
            return null;
        }

        File imageFile = new File(path);
        if (!imageFile.exists()) {
            System.err.println("Image file not found: " + path);
            return null;
        }

        return IMAGE_CACHE.computeIfAbsent(path, p -> {
            Image image = new ImageIcon(p).getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        });
    }

    /**
     * Sets the picture for the label.
     *
     * @param imagePath the path to the image file
     */
    public void setPicture(String imagePath) {
        pictureLabel.setIcon(loadCachedImage(imagePath)); // Set the icon on the internal JLabel
    }
}
