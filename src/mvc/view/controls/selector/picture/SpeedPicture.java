package mvc.view.controls.selector.picture;

import mvc.controller.Controller;
import mvc.model.Global.Events_Constants;
import mvc.model.Global.Images_Constants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A concrete implementation of {@link Picture} that represents a speed-related picture.
 *
 * <p>This class is designed to display a speed-related image (such as "Next Step") and
 * notifies the {@link Controller} when the picture is clicked. The notification event
 * is defined by {@link mvc.model.Global.Events_Constants#NEXT_STEP_CLICKED}.
 *
 * <p>Usage:
 * <ul>
 *   <li>Instantiate this class by providing a {@link Controller} to handle click events.</li>
 *   <li>The displayed image is initially set to {@link mvc.model.Global.Images_Constants#NEXT_STEP}.</li>
 * </ul>
 *
 * @see Picture
 * @see mvc.controller.Controller
 * @see mvc.model.Global.Events_Constants
 * @see mvc.model.Global.Images_Constants
 *
 * @author Sergio Vega García
 */
public class SpeedPicture extends Picture {

    /**
     * Constructs a {@code SpeedPicture} with a default image and click handling.
     *
     * <p>The default image is set to {@link mvc.model.Global.Images_Constants#NEXT_STEP}.
     * A mouse click on this picture notifies the {@link Controller} with the event
     * {@link mvc.model.Global.Events_Constants#NEXT_STEP_CLICKED}.
     *
     * @param controller the {@link Controller} that will handle the click event
     */
    public SpeedPicture(Controller controller) {
        super(Images_Constants.NEXT_STEP);

        // Add a mouse listener to handle clicks on the picture
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.notify(Events_Constants.NEXT_STEP_CLICKED);
            }
        });
    }
}
