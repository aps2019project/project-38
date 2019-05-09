package view.windowgraphics;

import controller.window.normalwindow.NormalWindow;

import java.util.ArrayList;
import java.util.Random;

public class   WindowGraphic {
    private static ArrayList<WindowGraphic> windowGraphics = new ArrayList<>();

    String title;
    String titleOfSuper;
    String titleOfSub;

    static {
        windowGraphics.add
                (new WindowGraphic("__==^^ %s ^^==__\n", "%d. %s\n", "%d. back to %s\n"));
        windowGraphics.add
                (new WindowGraphic("...::: %s :::...", "%d: %s\n", "%d: back to %s\n"));
        windowGraphics.add
                (new WindowGraphic("ooo(0)( %s )(0)ooo", "(%d) %s\n", "(%d) back to %s\n"));

    }

    public static WindowGraphic getRandomWindowGraphics() {
        Random random = new Random(System.currentTimeMillis());
        return windowGraphics.get(random.nextInt(windowGraphics.size()));
    }

    WindowGraphic(String title, String titleOfSuper, String titleOfSub) {
        this.title = title;
        this.titleOfSuper = titleOfSuper;
        this.titleOfSub = titleOfSub;
    }

    public void showWindowBody(NormalWindow window) {
        System.out.printf(this.title, window.getWindowTitle().getTitle());
        if (window.getSubWindows().size() > 0) {
            for (int i = 0; i < window.getSubWindows().size(); i++) {
                System.out.printf(titleOfSub, i + 1, window.getSubWindows().get(i).getWindowTitle().getTitleInSuper());
            }
        }
        System.out.printf(titleOfSuper, 0, window.getSuperWindow().getWindowTitle().getTitleInSub());
    }
}
