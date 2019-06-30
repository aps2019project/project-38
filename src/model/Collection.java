package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import view.Message;
import view.Utility;
import view.fxmlControllers.CreateDeckController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Collection implements Serializable {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private HashMap<String, Deck> allDecks = new HashMap<>();
    private HashMap<String, Integer> howManyCard = new HashMap<>();
    private Deck mainDeck;

    public void createDeck(String deckName) {
        for (String template : getDecks()) {
            if (template.toLowerCase().equals(deckName.toLowerCase())) {
                Utility.showMessage("There is already a deck with this name");
                return;
            }
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deckName);
        getAllDecks().put(deckName, deck);
        CreateDeckController.putANewDeckToList(deckName);
        Utility.showMessage("Deck created :)");
    }

    public void deleteDeck(String deckName) {
        boolean isDeckNameValid = false;
        for (String template : getDecks()) {
            if (template.equals(deckName)) {
                isDeckNameValid = true;
            }
        }
        if (!isDeckNameValid) {
            Utility.showMessage("There is no deck with this name :(");
            return;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        if (this.getMainDeck() != null && this.getMainDeck().equals(deck)) {
            this.setMainDeck(null);
        }
        getDecks().remove(deckName);
        getAllDecks().remove(deckName);
        CreateDeckController.removeADeckFromList(deckName);
        Utility.showMessage("Deck deleted :)");
    }

    public void addCardToDeck(String cardName, String deckName) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Utility.showMessage("There is no deck with this name :(");
            return;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        int cardID = getIDByName(cardName);
        if (!this.getCardIDs().contains(cardID)) {
            Utility.showMessage("There is no card with this name in collection cards :(");
            return;
        }
        Card card = Card.getAllCards().get(cardID);
        if (card instanceof Hero) {
            if (deck.getHero() != null) {
                Utility.showMessage("There is already a hero in this deck. You can't add any other");
                return;
            } else {
                deck.setHero((Hero) card);
                Utility.showMessage("Card added to deck successfully :)");
                return;
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem() != null) {
                Utility.showMessage("There is an item in this deck");
                return;
            } else {
                deck.setItem((Spell) card);
                Utility.showMessage("Card added to deck successfully :)");
                return;
            }
        }
        if (deck.getCardIDs().size() == 20) {
            Utility.showMessage("You have 20 cards in your deck. You couldn't put any other card");
            return;
        }
        int numberOfCard = 0;
        for (int ID : deck.getCardIDs()) {
            if (ID == cardID) {
                numberOfCard++;
            }
        }
        if (numberOfCard > Collection.getCollection().howManyCard.get(cardName)) {
            Utility.showMessage("You can't add this card to your deck. You haven't enough number of it in your collection");
            return;
        }
        deck.getCardIDs().add(cardID);
        Utility.showMessage("Card added to deck successfully :)");
    }

    public void removeCardFromDeck(String cardName, String deckName) {
        int cardID = getIDByName(cardName);
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Utility.showMessage("There is no deck with this name :(");
            return;
        }
        if (!Card.getAllCards().containsKey(cardID)) {
            Utility.showMessage("There is no card with this name in this deck :(");
            return;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        Card card = Card.getAllCards().get(cardID);

        if (card instanceof Hero) {
            if (deck.getHero().equals((Hero) card)) {
                deck.setHero(null);
                Utility.showMessage("Card removed from deck successfully :)");
                return;
            } else {
                Utility.showMessage("There is no card with this name in this deck :(");
                return;
            }
        }
        if (card instanceof Spell) {
            if (deck.getItem().equals((Spell) card)) {
                deck.setItem(null);
                Utility.showMessage("Card removed from deck successfully :)");
                return;
            } else {
                Utility.showMessage("There is no card with this name in this deck :(");
                return;
            }
        }
        if (deck.getCardIDs().contains(cardID)) {
            deck.getCardIDs().remove((Integer) cardID);
            Utility.showMessage("Card removed from deck successfully :)");
        } else {
            Utility.showMessage("There is no card with this name in this deck :(");
        }
    }

    public boolean validateDeck(String deckName) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Utility.showMessage("There is no deck with this name :(");
            return false;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        if (deck.getCardIDs().size() != 20 || deck.getHero() == null) {
            Utility.showMessage("This deck is not valid :(");
            return false;
        }
        Utility.showMessage("This deck is valid :)");
        return true;
    }

    public void selectMainDeck(String deckName) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Utility.showMessage("There is no deck with this name :(");
            return;
        }
        if (validateDeck(deckName)) {
            setMainDeck(Account.getActiveAccount().getCollection().getAllDecks().get(deckName));
            Utility.showMessage("This deck selected as main successfully :)");
        } else {
            Utility.showMessage("This deck is not valid :(");
        }
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
