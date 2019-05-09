package controller.window.normalwindow;

import controller.window.Window;
import view.Request;
import view.windowgraphics.WindowGraphic;

import java.util.ArrayList;

public class NormalWindow extends Window {
    private WindowTitle windowTitle;
    private ArrayList<NormalWindow> subWindows = new ArrayList<>();
    private NormalWindow superWindow;
    private WindowGraphic graphic;

    @Override
    public void main() {
        while (true) {
            if (graphic == null) {
                WindowGraphic.getRandomWindowGraphics().showWindowBody(this);
            }else {
                graphic.showWindowBody(this);
            }
            String request = Request.getNextRequest();
        }
    }

    public WindowTitle getWindowTitle() {
        return windowTitle;
    }

    public ArrayList<NormalWindow> getSubWindows() {
        return subWindows;
    }

    public NormalWindow getSuperWindow() {
        return superWindow;
    }
}
