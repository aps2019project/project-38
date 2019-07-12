package view;

import model.Deck;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public interface Loader {

    static void loadAll() {
        //todo after making files
        System.out.println(1);
        Deck.deckLevelBuilder();
        System.out.println(2);
        new LoadedImages();
        System.out.println(3);
        new LoadedScenes();
    }
}
