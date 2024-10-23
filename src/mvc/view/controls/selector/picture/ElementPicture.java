package mvc.view.controls.selector.picture;

import mvc.controller.Controller;

import static mvc.model.Global.CLEAN_IMAGE;

public class ElementPicture extends Picture {

    public ElementPicture(Controller controller) {
        super(controller, CLEAN_IMAGE);
    }

}
