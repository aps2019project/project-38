package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Collection implements Serializable {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private HashMap<String, Integer> howManyCard = new HashMap<>();
    private Deck mainDeck = Deck.getDeckLevels().get(0); //danger

    //***
    public static Collection getCollection() {
        return Account.getActiveAccount().getCollection();
    }

    public int searchInCollectionCards(String cardName) {
        int numberOf = 0;
        Card card = null;
        for (int ID : getCardIDs()) {
            card = Card.getAllCards().get(ID);
            if (card.getName().equals(cardName)) {
                numberOf++;
            }
        }
        return numberOf;
    }

    public void createDeck(String deckName) {
        for (String template : getDecks()) {
            if (template.toLowerCase().equals(deckName.toLowerCase())) {
                Message.thereIsADeckWhitThisName();
                return;
            }
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deckName);
        Deck.getLowerCaseNamesToOriginalName().put(deckName.toLowerCase(), deckName);
        Deck.getAllDecks().put(deckName, deck);
        Message.deckCreated();
    }

    public void deleteDeck(String deckName) {
        boolean isDeckNameValid = false;
        for (String template : getDecks()) {
            if (template.toLowerCase().equals(deckName.toLowerCase())) {
                isDeckNameValid = true;
            }
        }
        if (!isDeckNameValid) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        Deck.getLowerCaseNamesToOriginalName().remove(deckName.toLowerCase(), deckName);
        Deck.getAllDecks().remove(deckName);
        getDecks().remove(deckName);
        Message.deckDeleted();
    }

    public void addCardToDeck(String cardName, String deckName) {
        if (!Deck.getLowerCaseNamesToOriginalName().containsKey(deckName.toLowerCase())) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        Deck deck = Deck.getAllDecks().get(Deck.getLowerCaseNamesToOriginalName().get(deckName.toLowerCase()));
        int cardID = getIDFromName(cardName);
        if (!getCardIDs().contains(cardID)) {
            Message.thereIsNoCardWithThisNameInCollection();
            return;
        }
        Card card = Card.getAllCards().get(cardID);
        if (deck.getCardIDs().contains(cardID)) {
            Message.thereIsACardWithThisNameInThisDeck();
            return;
        }
        if (deck.getCardIDs().size() == 20) {
            Message.have20CardsInThisDeck();
            return;
        }
        if (card instanceof Warrior) {
            Warrior warrior = (Warrior) card;
            if (warrior instanceof Hero) {
                if (deck.getHero() != null) {
                    Message.thereIsAHeroInThisDeck();
                    return;
                } else {
                    deck.setHero((Hero) card);
                }
            }
        }
        if (Spell.checkIsItem(card)) {
            deck.setItem((Spell) card);
        }
        int numberOf = 0;
        for (int ID : deck.getCardIDs()) {
            if (ID == cardID) {
                numberOf++;
            }
        }
        if (numberOf > Collection.getCollection().howManyCard.get(cardName)) {
            Message.notEnoughCardNumber();
            return;
        }
        deck.getCardIDs().add(cardID);
        Message.cardAddedToDeckSuccessfully();
    }

    public void removeCardFromDeck(String cardName, String deckName) {
        if (!Deck.getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        Deck deck = Deck.getAllDecks().get(deckName);
        int cardID = getIDFromName(cardName);
        if (!deck.getCardIDs().contains(cardID)) {
            Message.thereIsNoCardWithThisIDInThisDeck();
            return;
        }
        deck.getCardIDs().remove((Integer)cardID);
        Message.cardRemovedFromDeckSuccessfully();
    }

    public boolean validateDeck(String deckName, boolean showResultsOrNot) {
        if (!Deck.getAllDecks().containsKey(deckName)) {
            if (showResultsOrNot) Message.thereIsNoDeckWithThisName();
            return false;
        }
        Deck deck = Deck.getAllDecks().get(deckName);
        if (deck.getCardIDs().size() != 20 || deck.getHero() == null) {
            if (showResultsOrNot) Message.deckIsNotValid();
            return false;
        }
        if (showResultsOrNot) Message.deckIsValid();
        return true;
    }

    public void selectMainDeck(String deckName) {
        if (!Deck.getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        if (validateDeck(deckName, false)) {
            setMainDeck(Deck.getAllDecks().get(deckName));
            Message.deckSelectedAsMain();
        } else {
            Message.deckIsNotValid();
        }
    }

    private int getIDFromName(String cardName) {
        for (int ID : Shop.getShop().getCardIDs()) {
            if (Card.getAllCards().get(ID).getName().equals(cardName)) {
                return ID;
            }
        }
        return -1;
    }

    //***

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
}
