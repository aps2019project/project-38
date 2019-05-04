package controller.window;

import java.util.ArrayList;

public abstract class Window {
    private static ArrayList<Window> openWindows = new ArrayList<>();

    public static boolean runLastOpenWindow() {
        if (openWindows.size() > 0) {
            openWindows.get(openWindows.size() - 1);
            return true;
        }
        return false;
    }

    public static void openWindow(Window window) {
        openWindows.add(window);
    }

    public static void closeWindow(Window window) {
        openWindows.remove(window);
    }

    public abstract void main();
}
