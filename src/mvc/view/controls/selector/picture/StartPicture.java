package mvc.view.controls.selector.picture;

import mvc.controller.Controller;
import mvc.model.Global.Events_Constants;
import mvc.model.Global.Images_Constants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPicture extends Picture {

    public StartPicture(Controller controller) {
        super(Images_Constants.START);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.notify(Events_Constants.START_CLICKED);
            }
        });
    }

}
