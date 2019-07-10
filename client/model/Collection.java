package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.fxmlControllers.AlertController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Collection implements Serializable { //todo MOEINI فک کنم کلاینت دِک و کالکشن و شاپ نمیخواد
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private HashMap<String, Deck> allDecks = new HashMap<>();
    private HashMap<String, Integer> howManyCard = new HashMap<>();
    private Deck mainDeck;

    public boolean createDeck(String deckName) {
        if (decks.contains(deckName)) {
            AlertController.setAndShow("There is already a deck with this name");
            return false;
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deckName);
        getAllDecks().put(deckName, deck);
        Deck.getAllDecks().put(deckName, deck);
//        DeckController.putADeckToList(deckName);
        AlertController.setAndShow("Deck created");
        return true;
        //todo server
    }

    public boolean deleteDeck(String deckName) {
        //todo
//        if (!getDecks().contains(deckName)) {
//            AlertController.setAndShow("There is no deck with this name");
//            return false;
//        }
        //todo
        Deck deck = Account.activeAccount.getCollection().getAllDecks().get(deckName);
        if (this.getMainDeck() != null && this.getMainDeck().equals(deck)) {
            this.setMainDeck(null);
        }
        getDecks().remove(deckName);
        getAllDecks().remove(deckName);
        Deck.getAllDecks().remove(deckName);
        AlertController.setAndShow("Deck deleted");
        return true;
        //todo server
    }

    public boolean addCardToDeck(String cardName, String deckName) {
        //todo
//        if (!Account.activeAccount.getCollection().getAllDecks().containsKey(deckName)) {
//            AlertController.setAndShow("There is no deck with this name");
//            return false;
//        }
        //todo
        Deck deck = Account.activeAccount.getCollection().getAllDecks().get(deckName);
        int cardID = getIDByName(cardName);
        //todo
//        if (!this.getCardIDs().contains(cardID)) {
//            AlertController.setAndShow("There is no card with this name in collection cards");
//            return false;
//        }
        //todo
        Card card = Card.getAllCards().get(cardID);
        if (card instanceof Hero) {
            if (deck.getHero() != null) {
                AlertController.setAndShow("There is already a hero in this deck. You can't add any other");
                return false;
            } else {
                deck.setHero((Hero) card);
                deck.getCardIDs().add(cardID);
                AlertController.setAndShow("Card added to deck successfully");
                return true;
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem() != null) {
                AlertController.setAndShow("There is an item in this deck");
                return false;
            } else {
                deck.setItem((Spell) card);
                deck.getCardIDs().add(cardID);
                AlertController.setAndShow("Card added to deck successfully");
                return true;
            }
        }
        if (deck.getCardIDs().size() == 20) {
            AlertController.setAndShow("You have 20 cards in your deck. You couldn't put any other card");
            return false;
        }
        //todo
//        int numberOfCard = 0;
//        for (int ID : deck.getCardIDs()) {
//            if (ID == cardID) {
//                numberOfCard++;
//            }
//        }
//        if (numberOfCard >= Collection.getCollection().howManyCard.get(cardName)) {
//            AlertController.setAndShow("You can't add this card to your deck. You haven't enough number of it in your collection");
//            return false;
//        }
        //todo
        deck.getCardIDs().add(cardID);
        if (card instanceof Warrior) {
            deck.minions.add(card);
        } else {
            deck.spells.add(card);
        }
        AlertController.setAndShow("Card added to deck successfully");
        return true;
        //todo server
    }

    public boolean removeCardFromDeck(String cardName, String deckName) {
        int cardID = getIDByName(cardName);
        //todo
//        if (!Account.activeAccount.getCollection().getAllDecks().containsKey(deckName)) {
//            AlertController.setAndShow("There is no deck with this name");
//            return false;
//        }
//        if (!Card.getAllCards().containsKey(cardID)) {
//            AlertController.setAndShow("There is no card with this name in this deck");
//            return false;
//        }
        //todo
        Deck deck = Account.activeAccount.getCollection().getAllDecks().get(deckName);
        Card card = Card.getAllCards().get(cardID);
        if (card instanceof Hero) {
            if (deck.getHero().equals((Hero) card)) {
                deck.setHero(null);
                AlertController.setAndShow("Card removed from deck successfully");
                return true;
            } else {
                AlertController.setAndShow("There is no card with this name in this deck");
                return false;
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem().equals((Spell) card)) {
                deck.setItem(null);
                AlertController.setAndShow("Card removed from deck successfully");
                return true;
            } else {
                AlertController.setAndShow("There is no card with this name in this deck");
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
            AlertController.setAndShow("Card removed from deck successfully");
            return true;
        } else {
            AlertController.setAndShow("There is no card with this name in this deck");
            return false;
        }
        //todo server
    }

    public boolean validateDeck(String deckName, boolean showMessage) {
        //todo
//        if (!Account.activeAccount.getCollection().getAllDecks().containsKey(deckName)) {
//            if (showMessage) {
//                AlertController.setAndShow("There is no deck with this name");
//            }
//            return false;
//        }
        //todo
        Deck deck = Account.activeAccount.getCollection().getAllDecks().get(deckName);
        if (deck.getCardIDs().size() != 20 || deck.getHero() == null) {
            if (showMessage) {
                AlertController.setAndShow("This deck is not valid");
            }
            return false;
        }
        if (showMessage) {
            AlertController.setAndShow("This deck is valid");
        }
        return true;
        //todo server
    }

    public boolean selectMainDeck(String deckName) {
        //todo
//        if (!Account.activeAccount.getCollection().getAllDecks().containsKey(deckName)) {
//            AlertController.setAndShow("There is no deck with this name");
//            return false;
//        }
        //todo
        if (validateDeck(deckName, false)) {
            setMainDeck(Account.activeAccount.getCollection().getAllDecks().get(deckName));
            AlertController.setAndShow("This deck selected as main successfully");
            return true;
        } else {
            AlertController.setAndShow("This deck is not valid");
            return false;
        }
        //todo server
    }

    public boolean renameDeck(String deckName, String newName) {
        //todo
//        if (!Account.activeAccount.getCollection().getAllDecks().containsKey(deckName)) {
//            AlertController.setAndShow("There is no deck with this name");
//            return false;
//        }
        //todo
        if (Account.activeAccount.getCollection().getAllDecks().containsKey(newName)) {
            AlertController.setAndShow("You have an another deck with this name");
            return false;
        }
        Deck deck = Account.activeAccount.getCollection().getAllDecks().get(deckName);
        deck.setName(newName);
        allDecks.remove(deckName);
        decks.remove(deckName);
        allDecks.put(newName, deck);
        decks.add(newName);
        return true;
        //todo server
    }

    private int getIDByName(String cardName) {
        for (int ID : Shop.getShop().getCardIDs()) {
            if (Card.getAllCards().get(ID).name.equals(cardName)) {
                return ID;
            }
        }
        return -1;
    }

    //***

    public static Collection getCollection() {
        return Account.activeAccount.getCollection();
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
        //todo server
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
