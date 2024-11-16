package mvc.view.controls.selector;

import mvc.view.controls.selector.picture.Picture;
import mvc.view.controls.selector.radioButtonGroup.RadioButtonGroup;

import javax.swing.*;
import java.awt.*;

/**
 * A generic JPanel-based component that combines a {@link RadioButtonGroup} and a {@link Picture}.
 *
 * <p>This class provides a layout for displaying a group of radio buttons alongside an image.
 * The radio buttons and the picture are provided as generic parameters, allowing for flexible
 * use with different types of {@link RadioButtonGroup} and {@link Picture} subclasses.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by providing a {@link RadioButtonGroup} and a {@link Picture} subclass.</li>
 *   <li>Use {@link #getPicture()} to access the picture component.</li>
 * </ul>
 *
 * <p>Layout:
 * <ul>
 *   <li>The radio button group is displayed on the left (LINE_START).</li>
 *   <li>The picture is displayed in the center (CENTER).</li>
 * </ul>
 *
 * @param <T> the type of {@link RadioButtonGroup} to be used
 * @param <U> the type of {@link Picture} to be used
 * @author Sergio Vega García
 * @see RadioButtonGroup
 * @see Picture
 * @see javax.swing.JPanel
 * <p>
 */
public class Selector<T extends RadioButtonGroup, U extends Picture> extends JPanel {

    /**
     * The picture component displayed in the center of the selector.
     */
    private final U picture;

    /**
     * Constructs a {@code Selector} component with the given radio buttons and picture.
     *
     * <p>The selector is arranged in a {@link BorderLayout}, with the radio buttons placed
     * at the start (LINE_START) and the picture placed in the center (CENTER).
     *
     * @param radioButtons the {@link RadioButtonGroup} component to display
     * @param picture      the {@link Picture} component to display
     */
    public Selector(T radioButtons, U picture) {
        this.picture = picture;

        // Configure panel layout
        setLayout(new BorderLayout());

        // Add components to the panel
        add(radioButtons, BorderLayout.LINE_START);
        add(picture, BorderLayout.CENTER);
    }

    /**
     * Returns the picture component of this selector.
     *
     * @return the {@link Picture} component
     */
    public U getPicture() {
        return picture;
    }
}
