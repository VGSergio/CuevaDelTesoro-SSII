package mvc.view.controls.selector.picture;

import static mvc.model.Global.Images_Constants;

/**
 * A concrete implementation of {@link Picture} that represents a status image.
 *
 * <p>This class is designed to display a default status image by leveraging the
 * {@link Picture} superclass. The default image path is defined by
 * {@link mvc.model.Global.Images_Constants#DEFAULT}.
 *
 * <p>Usage:
 * <ul>
 *   <li>Instantiate this class to display a status picture with the default image initially loaded.</li>
 *   <li>Use the {@link Picture#setPicture(String)} method to update the displayed image.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see Picture
 * @see mvc.model.Global.Images_Constants
 * @see mvc.model.Global.Images_Constants#DEFAULT
 */
public class StatusPicture extends Picture {

    /**
     * Constructs an {@code StatusPicture} with the default image.
     *
     * <p>The default image path is defined by {@link mvc.model.Global.Images_Constants#DEFAULT}.
     * This default image is displayed when the component is first created.
     */
    public StatusPicture() {
        super(Images_Constants.DEFAULT);
    }
}
