package controller.window;

import model.Account;
import model.cards.CardFactory;

public class LoadWindow extends Window {

    @Override
    public void main() {
        //todo after making files
        Account.loadAccounts();
        CardFactory.main();
        Window.openWindow(new IntroWindow());
    }
}
