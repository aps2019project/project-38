package controller.window;

import model.cards.CardFactory;

public class LoadWindow extends Window {

    @Override
    public void main() {
        //todo after making files
        CardFactory.main();
        Window.openWindow(new IntroWindow());
    }
}
