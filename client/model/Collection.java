package model;

import client.net.*;
import view.fxmlControllers.AlertController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Collection implements Serializable {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private HashMap<String, Deck> allDecks = new HashMap<>();
    private HashMap<String, Integer> howManyCard = new HashMap<>();
    private Deck mainDeck;

    public static Box<Boolean> createDeckResult = new Box<>();
    public boolean createDeck(String deckName) {
        Encoder.sendPackage(Message.CreateDeck, deckName);
        Digikala.wait(createDeckResult);
        return createDeckResult.obj;
    }

    public static Box<Boolean> deleteDeckResult = new Box<>();
    public boolean deleteDeck(String deckName) {
        Encoder.sendPackage(Message.DeleteDeck, deckName);
        Digikala.wait(deleteDeckResult);
        return deleteDeckResult.obj;
    }

    public static Box<Boolean> addCardToDeckResult = new Box<>();
    public boolean addCardToDeck(String cardName, String deckName) {
        Encoder.sendPackage(Message.AddCardToDeck, cardName, deckName);
        Digikala.wait(addCardToDeckResult);
        return addCardToDeckResult.obj;
    }

    public static Box<Boolean> removeCardFromDeckResult = new Box<>();
    public boolean removeCardFromDeck(String cardName, String deckName) {
        Encoder.sendPackage(Message.RemoveCardFromDeck, cardName, deckName);
        Digikala.wait(removeCardFromDeckResult);
        return removeCardFromDeckResult.obj;
    }

    public boolean validateDeck(String deckName) {
        Deck deck = Account.activeAccount.getCollection().getAllDecks().get(deckName);
        return deck.getCardIDs().size() == 20 && deck.getHero() != null;
    }

    public static Box<Boolean> selectMainDeckResult = new Box<>();
    public boolean selectMainDeck(String deckName) {
        Encoder.sendPackage(Message.SelectMainDeck, deckName);
        Digikala.wait(selectMainDeckResult);
        return selectMainDeckResult.obj;
    }

    public static Box<Boolean> renameDeckResult = new Box<>();
    public boolean renameDeck(String deckName, String newName) {
        Encoder.sendPackage(Message.RenameDeck, deckName, newName);
        Digikala.wait(renameDeckResult);
        return renameDeckResult.obj;
    }

    public static Collection getCollection() {
        return Account.activeAccount.getCollection();
    }


    public static void deselectMainDeck() {
        Encoder.sendMessage(Message.DeselectMainDeck);
    }

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public ArrayList<String> getDecks() {
        return decks;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public HashMap<String, Integer> getHowManyCard() {
        return howManyCard;
    }

    public HashMap<String, Deck> getAllDecks() {
        return allDecks;
    }
}
