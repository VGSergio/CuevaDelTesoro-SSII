package mvc.view.controls.selector.picture;

import mvc.controller.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static mvc.model.Global.NEXT_STEP_CLICKED;
import static mvc.model.Global.NEXT_STEP_IMAGE;

public class SpeedPicture extends Picture {

    public SpeedPicture(Controller controller) {
        super(NEXT_STEP_IMAGE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.notify(NEXT_STEP_CLICKED);
            }
        });
    }
}
