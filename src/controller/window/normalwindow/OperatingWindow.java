package controller.window.normalwindow;

public abstract class OperatingWindow extends NormalWindow {

    public OperatingWindow(ChoosingWindow choosingWindow, String titleInSuper) {
        super(choosingWindow, new WindowTitle(titleInSuper));
    }

    @Override
    public abstract void main();
}