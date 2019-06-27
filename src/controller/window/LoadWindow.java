package controller.window;

import model.Account;
import model.Deck;
import model.cards.CardFactory;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public class LoadWindow extends Window {

    @Override
    public void main() {
        //todo after making files
//        CardFactory.main();
//        Deck.deckLevelBuilder();
        Account.loadAccounts();
        new LoadedImages();
        new LoadedScenes();
//        new IntroWindow().openWindow();
    }
}
