package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.Message;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;

    //***
    public static Collection getCollection() {
        return Account.getActiveAccount().getCollection();
    }

    public ArrayList<Integer> searchInCollectionCards(String cardName) {
        ArrayList<Integer> foundIDs = new ArrayList<>();
        for (int ID : getCardIDs()) {
            Card card = Card.getAllCards().get(ID);
            if (card.getName().equals(cardName)) {
                foundIDs.add(ID);
            }
        }
        if (foundIDs.size() == 0) {
            Message.thereIsNoCardWithThisNameInCollection();
        }
        return foundIDs;
    }

    public void createDeck(String deckName) {
        for (Deck deck : getDecks()) {
            if (deck.getName().equals(deckName)) {
                Message.thereIsADeckWhitThisName();
                return;
            }
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deck);
    }

    public void deleteDeck(String deckName) {
        Deck mustBeDeleted = null;
        for (Deck deck : getDecks()) {
            if (deck.getName().equals(deckName)) {
                mustBeDeleted = deck;
            }
        }
        if (mustBeDeleted == null) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        getDecks().remove(mustBeDeleted);
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
        deck.getCardIDs().add(cardID);
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
    }

    public boolean validateDeck(String deckName) {
        if (!Deck.getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return false;
        }
        Deck deck = Deck.getAllDecks().get(deckName);
        if (deck.getCardIDs().size() != 20 || deck.getHero() == null) {
            Message.deckIsNotValid();
            return false;
        }
        return true;
    }

    public void selectMainDeck(String deckName) {
        if (!Deck.getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        if (validateDeck(deckName)) {
            setMainDeck(Deck.getAllDecks().get(deckName));
        }
    }

    public void save() {
        //todo
    }
    //***

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
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
