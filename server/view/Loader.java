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
        CardFactory.main();
        Account.loadAccounts();
        Deck.deckLevelBuilder();
        new LoadedScenes();
        new LoadedImages();
        Shop.getShop().loadShop();
        MatchMaker.makeMatchMakingThreads();
    }
}
