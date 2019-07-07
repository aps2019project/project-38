package view;

import model.Account;
import model.Deck;
import model.cards.CardFactory;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public interface Loader {

    static void loadAll() {
        //todo after making files
        CardFactory.main();
        Deck.deckLevelBuilder();
        Account.loadAccounts();
        new LoadedImages();
        new LoadedScenes();
    }
}
