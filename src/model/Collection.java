package model;

import model.cards.Card;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Integer> cards = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;

    //***
    public static void deleteCardFromDeck(int cardID, String deckName) {

    }

    //***
    public ArrayList<Card> getHeroes() {
        return null;
    }

    public void createDeck(String name) {

    }

    public void deleteDeck(String name) {

    }

    public void addCardToDeck(int cardID, String deckName) {

    }

    public void selectMainDeck(String deckName) {

    }

    public void search(String name) {

    }

    //***

    public ArrayList<Integer> getCards() {
        return cards;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }
}
