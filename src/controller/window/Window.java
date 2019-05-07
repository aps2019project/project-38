package controller.window;

import java.util.ArrayList;

public abstract class Window {
    private static ArrayList<Window> openWindows = new ArrayList<>();

    public static boolean runLastOpenWindow() {
        if (openWindows.size() > 0) {
            openWindows.get(openWindows.size() - 1).main();
            return true;
        }
        return false;
    }

    public void openWindow() {
        openWindows.add(this);
    }

    public void closeWindow() {
        openWindows.remove(this);
    }

    public abstract void main();
}
