package mvc.view.controls.selector.picture;

import mvc.controller.Controller;
import mvc.model.Global.Events_Constants;
import mvc.model.Global.Images_Constants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A concrete implementation of {@link Picture} that represents a "Start" button or image.
 *
 * <p>This class is designed to display a "Start" image and notify the {@link Controller}
 * when the picture is clicked. The notification event is defined by
 * {@link mvc.model.Global.Events_Constants#START_CLICKED}.
 *
 * <p>Usage:
 * <ul>
 *   <li>Instantiate this class by providing a {@link Controller} to handle click events.</li>
 *   <li>The displayed image is initially set to {@link mvc.model.Global.Images_Constants#START}.</li>
 * </ul>
 *
 * @see Picture
 * @see mvc.controller.Controller
 * @see mvc.model.Global.Events_Constants
 * @see mvc.model.Global.Images_Constants
 *
 * @author Sergio Vega García
 */
public class StartPicture extends Picture {

    /**
     * Constructs a {@code StartPicture} with a default image and click handling.
     *
     * <p>The default image is set to {@link mvc.model.Global.Images_Constants#START}.
     * A mouse click on this picture notifies the {@link Controller} with the event
     * {@link mvc.model.Global.Events_Constants#START_CLICKED}.
     *
     * @param controller the {@link Controller} that will handle the click event
     */
    public StartPicture(Controller controller) {
        super(Images_Constants.START);

        // Add a mouse listener to handle clicks on the picture
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.notify(Events_Constants.START_CLICKED);
            }
        });
    }
}
