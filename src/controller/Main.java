package controller;

import controller.window.Window;

public class Main {
    public static void main(String[] args) {
        while (true) {
            if (Window.runLastOpenWindow()) {
                break;
            }
        }
    }
}
