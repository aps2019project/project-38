package controller.window.normalwindow;

import controller.window.Window;

public abstract class NormalWindow extends Window {
    WindowTitle windowTitle;
    Window superWindow;

    public NormalWindow(Window superWindow, WindowTitle windowTitle) {
        this.superWindow = superWindow;
        this.windowTitle = windowTitle;
    }

    public WindowTitle getWindowTitle() {
        return windowTitle;
    }

    public Window getSuperWindow() {
        return superWindow;
    }
}