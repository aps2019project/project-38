package view;

import model.Deck;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public interface Loader {

    static void loadAll() {
        //todo after making files
        Deck.deckLevelBuilder();
        new LoadedImages();
        new LoadedScenes();
    }
}
