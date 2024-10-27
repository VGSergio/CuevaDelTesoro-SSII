package mvc.view.controls;

import mvc.controller.Controller;
import mvc.view.controls.selector.picture.Picture;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static mvc.model.Global.START_CLICKED;
import static mvc.model.Global.START_IMAGE;

public class StartPicture extends Picture {

    public StartPicture(Controller controller) {
        super(controller, START_IMAGE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.notify(START_CLICKED);
            }
        });
    }

}
