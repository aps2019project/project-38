package controller;

import controller.window.LoadWindow;
import controller.window.Window;

public class Main {
    public static void main(String[] args) {
        new LoadWindow().openWindow();
        while (true) {
            if (!Window.runLastOpenWindow()) {
                break;
            }
        }
    }
}
