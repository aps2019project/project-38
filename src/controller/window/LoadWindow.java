package controller.window;

import model.Account;
import model.Deck;
import model.cards.CardFactory;

public class LoadWindow extends Window {

    @Override
    public void main() {
        //todo after making files
        CardFactory.main();
        Deck.deckLevelBuilder();
        Account.loadAccounts();
        Window.openWindow(new IntroWindow());
    }
}
