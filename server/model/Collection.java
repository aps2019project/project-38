package model;

import java.io.Serializable;

public class Collection implements Serializable {
    private Deck mainDeck;

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }
}
