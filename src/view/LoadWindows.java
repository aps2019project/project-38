package view;

import model.Account;
import model.Deck;
import model.cards.CardFactory;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public class LoadWindows {

    public void main() {
        //todo after making files
        CardFactory.main();
        Deck.deckLevelBuilder();
        Account.loadAccounts();
        new LoadedImages();
        new LoadedScenes();
    }
}
