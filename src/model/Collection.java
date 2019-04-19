package model;

import model.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class Collection {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private HashMap<Integer,Card> allCards = new HashMap<>();
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

    public HashMap<Integer, Card> getAllCards() {
        return allCards;
    }

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }
}
