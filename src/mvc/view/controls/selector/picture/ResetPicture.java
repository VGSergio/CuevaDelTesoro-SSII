package mvc.view.controls.selector.picture;

import mvc.controller.Controller;
import mvc.model.Global.Events_Constants;
import mvc.model.Global.Images_Constants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A concrete implementation of {@link Picture} that represents a "Reset" button or image.
 *
 * <p>This class is designed to display a "Reset" image and notify the {@link Controller}
 * when the picture is clicked. The notification event is defined by
 * {@link Events_Constants#RESET_CLICKED}.
 *
 * <p>Usage:
 * <ul>
 *   <li>Instantiate this class by providing a {@link Controller} to handle click events.</li>
 *   <li>The displayed image is initially set to {@link Images_Constants#RESET}.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see Picture
 * @see Controller
 * @see Events_Constants
 * @see Images_Constants
 */
public class ResetPicture extends Picture {

    /**
     * Constructs a {@code ResetPicture} with a default image and click handling.
     *
     * <p>The default image is set to {@link Images_Constants#RESET}.
     * A mouse click on this picture notifies the {@link Controller} with the event
     * {@link Events_Constants#RESET_CLICKED}.
     *
     * @param controller the {@link Controller} that will handle the click event
     */
    public ResetPicture(Controller controller) {
        super(Images_Constants.RESET);

        // Add a mouse listener to handle clicks on the picture
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.notify(Events_Constants.RESET_CLICKED);
            }
        });
    }
}
