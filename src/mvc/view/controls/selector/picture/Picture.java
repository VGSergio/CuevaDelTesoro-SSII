package mvc.view.controls.selector.picture;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * A JPanel-based component for displaying and managing a resizable image.
 *
 * <p>This abstract class provides functionality to display a picture, with support for caching
 * and resizing. Images are cached for improved performance, and they are scaled to a fixed size.
 * Subclasses can extend this class to add more functionality or customize its behavior.
 *
 * @see javax.swing.JPanel
 * @see javax.swing.ImageIcon
 * @see java.util.Map
 * @see java.awt.Image
 *
 * @author Sergio Vega García
 */
public abstract class Picture extends JPanel {

    /**
     * The fixed size (width and height) for displayed images.
     */
    protected static final byte IMG_SIZE = 80;

    /**
     * A cache for storing previously loaded and scaled {@link ImageIcon} instances.
     */
    private static final Map<String, ImageIcon> IMAGE_CACHE = new HashMap<>();

    /**
     * The JLabel used to display the picture within this component.
     */
    private final JLabel pictureLabel;

    /**
     * Constructs a new {@code Picture} panel with a default image.
     *
     * @param defaultImage the path to the default image to display
     */
    public Picture(String defaultImage) {
        pictureLabel = new JLabel();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));
        add(pictureLabel, BorderLayout.CENTER);

        setPicture(defaultImage);
    }

    /**
     * Loads an image from the cache or creates a new scaled {@link ImageIcon} if not cached.
     *
     * <p>If the image is not already in the cache, this method scales the image to {@link #IMG_SIZE}
     * and stores it in the cache. If the file is not found or the path is invalid, it logs an error
     * and returns {@code null}.
     *
     * @param path the path to the image file
     * @return the scaled {@link ImageIcon}, or {@code null} if the file is invalid or not found
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
     * Updates the displayed picture in this component.
     *
     * <p>The method attempts to load the image from the given path. If successful, the image
     * is scaled and displayed. If the image cannot be loaded, no changes are made to the label.
     *
     * @param imagePath the path to the image file
     */
    public void setPicture(String imagePath) {
        pictureLabel.setIcon(loadCachedImage(imagePath)); // Set the icon on the internal JLabel
    }
}
