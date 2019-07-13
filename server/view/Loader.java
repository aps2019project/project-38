package view;


import model.Account;
import model.Deck;
import model.Shop;
import model.cards.CardFactory;
import server.net.MatchMaker;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public interface Loader {

    static void loadAll() {
        Deck.deckLevelBuilder();
        new LoadedImages();
        new LoadedScenes();
        Account.loadAccounts();
        MatchMaker.makeMatchMakingThreads();
        CardFactory.main();
        Shop.getShop().loadShop();
    }
}
