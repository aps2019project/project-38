package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Collection {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private HashMap<Integer, Integer> howManyCard = new HashMap<>();
    private Deck mainDeck = null;

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
            if (template.equals(deckName)) {
                Message.thereIsADeckWhitThisName();
                return;
            }
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deckName);
        Deck.getAllDecks().put(deckName, deck);
        Message.deckCreated();
    }

    public void deleteDeck(String deckName) {
        boolean isDeckNameValid = false;
        for (String template : getDecks()) {
            if (template.equals(deckName)) {
                isDeckNameValid = true;
            }
        }
        if (!isDeckNameValid) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        Deck.getAllDecks().remove(deckName);
        getDecks().remove(deckName);
        Message.deckDeleted();
    }

    public void addCardToDeck(int cardID, String deckName) {
        if (!Deck.getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        if (!getCardIDs().contains(cardID)) {
            Message.thereIsNoCardWithThisIDInCollection();
            return;
        }
        Deck deck = Deck.getAllDecks().get(deckName);
        Card card = Card.getAllCards().get(cardID);
        if (deck.getCardIDs().contains(cardID)) {
            Message.thereIsACardWithThisIDInThisDeck();
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
            deck.setItem(card);
        }
        int numberOf = 0;
        for (int ID : deck.getCardIDs()) {
            if (ID == cardID) {
                numberOf++;
            }
        }
        if (numberOf > Collection.getCollection().howManyCard.get(cardID)) {
            Message.notEnoughCardNumber();
            return;
        }
        deck.getCardIDs().add(cardID);
        Message.cardAddedToDeckSuccessfully();
    }

    public void removeCardFromDeck(int cardID, String deckName) {
        if (!Deck.getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        Deck deck = Deck.getAllDecks().get(deckName);
        if (!deck.getCardIDs().contains(cardID)) {
            Message.thereIsNoCardWithThisIDInThisDeck();
            return;
        }
        deck.getCardIDs().remove(cardID);
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
}
