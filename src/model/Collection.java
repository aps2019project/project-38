package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.fxmlControllers.AlertController;
import view.fxmlControllers.DeckController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Collection implements Serializable {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private HashMap<String, Deck> allDecks = new HashMap<>();
    private HashMap<String, Integer> howManyCard = new HashMap<>();
    private Deck mainDeck;

    public boolean createDeck(String deckName) {
        if (decks.contains(deckName)) {
            AlertController.setAndShowAndGetResultByAnAlertController("There is already a deck with this name", false);
            return false;
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deckName);
        getAllDecks().put(deckName, deck);
        Deck.getAllDecks().put(deckName, deck);
        DeckController.putADeckToList(deckName);
        AlertController.setAndShowAndGetResultByAnAlertController("Deck created", false);
        return true;
    }

    public boolean deleteDeck(String deckName) {
        //todo
        if (!getDecks().contains(deckName)) {
            AlertController.setAndShowAndGetResultByAnAlertController("There is no deck with this name", false);
            return false;
        }
        //todo
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        if (this.getMainDeck() != null && this.getMainDeck().equals(deck)) {
            this.setMainDeck(null);
        }
        getDecks().remove(deckName);
        getAllDecks().remove(deckName);
        Deck.getAllDecks().remove(deckName);
        DeckController.removeADeckFromList(deckName);
        AlertController.setAndShowAndGetResultByAnAlertController("Deck deleted", false);
        return true;
    }

    public boolean addCardToDeck(String cardName, String deckName) {
        //todo
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            AlertController.setAndShowAndGetResultByAnAlertController("There is no deck with this name", false);
            return false;
        }
        //todo
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        int cardID = getIDByName(cardName);
        //todo
        if (!this.getCardIDs().contains(cardID)) {
            AlertController.setAndShowAndGetResultByAnAlertController("There is no card with this name in collection cards", false);
            return false;
        }
        //todo
        Card card = Card.getAllCards().get(cardID);
        if (card instanceof Hero) {
            if (deck.getHero() != null) {
                AlertController.setAndShowAndGetResultByAnAlertController("There is already a hero in this deck. You can't add any other", false);
                return false;
            } else {
                deck.setHero((Hero) card);
                AlertController.setAndShowAndGetResultByAnAlertController("Card added to deck successfully", false);
                return true;
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem() != null) {
                AlertController.setAndShowAndGetResultByAnAlertController("There is an item in this deck", false);
                return false;
            } else {
                deck.setItem((Spell) card);
                AlertController.setAndShowAndGetResultByAnAlertController("Card added to deck successfully", false);
                return true;
            }
        }
        if (deck.getCardIDs().size() == 20) {
            AlertController.setAndShowAndGetResultByAnAlertController("You have 20 cards in your deck. You couldn't put any other card", false);
            return false;
        }
        //todo
        int numberOfCard = 0;
        for (int ID : deck.getCardIDs()) {
            if (ID == cardID) {
                numberOfCard++;
            }
        }
        if (numberOfCard >= Collection.getCollection().howManyCard.get(cardName)) {
            AlertController.setAndShowAndGetResultByAnAlertController("You can't add this card to your deck. You haven't enough number of it in your collection", false);
            return false;
        }
        //todo
        deck.getCardIDs().add(cardID);
        if (card instanceof Warrior) {
            deck.minions.add(card);
        } else {
            deck.spells.add(card);
        }
        AlertController.setAndShowAndGetResultByAnAlertController("Card added to deck successfully", false);
        return true;
    }

    public boolean removeCardFromDeck(String cardName, String deckName) {
        int cardID = getIDByName(cardName);
        //todo
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            AlertController.setAndShowAndGetResultByAnAlertController("There is no deck with this name", false);
            return false;
        }
        if (!Card.getAllCards().containsKey(cardID)) {
            AlertController.setAndShowAndGetResultByAnAlertController("There is no card with this name in this deck", false);
            return false;
        }
        //todo
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        Card card = Card.getAllCards().get(cardID);
        if (card instanceof Hero) {
            if (deck.getHero().equals((Hero) card)) {
                deck.setHero(null);
                AlertController.setAndShowAndGetResultByAnAlertController("Card removed from deck successfully", false);
                return true;
            } else {
                AlertController.setAndShowAndGetResultByAnAlertController("There is no card with this name in this deck", false);
                return false;
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem().equals((Spell) card)) {
                deck.setItem(null);
                AlertController.setAndShowAndGetResultByAnAlertController("Card removed from deck successfully", false);
                return true;
            } else {
                AlertController.setAndShowAndGetResultByAnAlertController("There is no card with this name in this deck", false);
                return false;
            }
        }
        if (deck.getCardIDs().contains(cardID)) {
            deck.getCardIDs().remove((Integer) cardID);
            if (card instanceof Warrior) {
                deck.minions.remove(card);
            } else {
                deck.spells.remove(card);
            }
            AlertController.setAndShowAndGetResultByAnAlertController("Card removed from deck successfully", false);
            return true;
        } else {
            AlertController.setAndShowAndGetResultByAnAlertController("There is no card with this name in this deck", false);
            return false;
        }
    }

    public boolean validateDeck(String deckName, boolean showMessage) {
        //todo
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            if (showMessage) {
                AlertController.setAndShowAndGetResultByAnAlertController("There is no deck with this name", false);
            }
            return false;
        }
        //todo
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        if (deck.getCardIDs().size() != 20 || deck.getHero() == null) {
            if (showMessage) {
                AlertController.setAndShowAndGetResultByAnAlertController("This deck is not valid", false);
            }
            return false;
        }
        if (showMessage) {
            AlertController.setAndShowAndGetResultByAnAlertController("This deck is valid", false);
        }
        return true;
    }

    public boolean selectMainDeck(String deckName) {
        //todo
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            AlertController.setAndShowAndGetResultByAnAlertController("There is no deck with this name", false);
            return false;
        }
        //todo
        if (validateDeck(deckName, false)) {
            setMainDeck(Account.getActiveAccount().getCollection().getAllDecks().get(deckName));
            AlertController.setAndShowAndGetResultByAnAlertController("This deck selected as main successfully", false);
            return true;
        } else {
            AlertController.setAndShowAndGetResultByAnAlertController("This deck is not valid", false);
            return false;
        }
    }

    public boolean renameDeck(String deckName, String newName) {
        //todo
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            AlertController.setAndShowAndGetResultByAnAlertController("There is no deck with this name", false);
            return false;
        }
        //todo
        if (Account.getActiveAccount().getCollection().getAllDecks().containsKey(newName)) {
            AlertController.setAndShowAndGetResultByAnAlertController("You have an another deck with this name", false);
            return false;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        deck.setName(newName);
        allDecks.remove(deckName);
        decks.remove(deckName);
        allDecks.put(newName, deck);
        return true;
    }

    private int getIDByName(String cardName) {
        for (int ID : Shop.getShop().getCardIDs()) {
            if (Card.getAllCards().get(ID).getName().equals(cardName)) {
                return ID;
            }
        }
        return -1;
    }

    //***

    public static Collection getCollection() {
        return Account.getActiveAccount().getCollection();
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
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
