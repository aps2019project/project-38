package view;


import model.Deck;
import model.Shop;
import model.cards.CardFactory;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public interface Loader {

    static void loadAll() {
        //todo after making files
        Deck.deckLevelBuilder();
        new LoadedImages();
        new LoadedScenes();

        //todo amir or hashem were should I load below things?
        CardFactory.main();
        Shop.getShop().loadShop();
    }
}
