package controller;

import controller.window.LoadWindow;
import controller.window.Window;
import model.cards.CardFactory;

public class Main {
    public static void main(String[] args) {
        Window.openWindow(new LoadWindow());
        while (true) {
            if (!Window.runLastOpenWindow()) {
                break;
            }
        }
    }
}
