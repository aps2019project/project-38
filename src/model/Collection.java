package model;

import model.cards.Card;
import model.cards.CardFactory;
import model.cards.Hero;
import model.cards.Spell;
import view.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Collection implements Serializable {
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private HashMap<String, Deck> allDecks = new HashMap<>();
    private HashMap<String, Integer> howManyCard = new HashMap<>();
    private Deck mainDeck = null;

    {// set a default deck
        Deck deck = new Deck();
        deck.setName("DefaultDeck");

        deck.setHero((Hero) CardFactory.getAllBuiltHeroes().get(5));
        deck.setItem((Spell) CardFactory.getAllBuiltItems().get(13));
        int[] spellIndex = {13, 8, 13, 8, 15, 12, 4};
        for (int i = 0; i < 7; i++) {
            deck.getCardIDs().add(CardFactory.getAllBuiltSpells().get(spellIndex[i] - 1).getID());
        }
        int[] minionIndex = {27, 27, 18, 18, 17, 17, 17, 29, 29, 29, 10, 10, 10};
        for (int i = 0; i < 13; i++) {
            deck.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i] - 1).getID());
        }
        mainDeck = deck;

        this.getDecks().add("DefaultDeck");
        this.getAllDecks().put("DefaultDeck", deck);
        this.getCardIDs().addAll(deck.getCardIDs());
        this.getCardIDs().add(deck.getHero().getID());
        this.getCardIDs().add(deck.getItem().getID());
    }

    {//todo danger for test
        this.decks.add("level3");
        this.allDecks.put("level3", Deck.getAllDecks().get("level3"));
        this.getCardIDs().addAll(Deck.getAllDecks().get("level3").getCardIDs());
        this.getCardIDs().add(Deck.getAllDecks().get("level3").getHero().getID());
        this.getCardIDs().add(Deck.getAllDecks().get("level3").getItem().getID());
        this.decks.add("level2");
        this.allDecks.put("level2", Deck.getAllDecks().get("level2"));
        this.getCardIDs().addAll(Deck.getAllDecks().get("level2").getCardIDs());
        this.getCardIDs().add(Deck.getAllDecks().get("level2").getHero().getID());
        this.getCardIDs().add(Deck.getAllDecks().get("level2").getItem().getID());
        this.decks.add("allCombo");
        this.allDecks.put("allCombo", Deck.getAllDecks().get("allCombo"));
        this.getCardIDs().addAll(Deck.getAllDecks().get("allCombo").getCardIDs());
        this.getCardIDs().add(Deck.getAllDecks().get("allCombo").getHero().getID());
        this.getCardIDs().add(Deck.getAllDecks().get("allCombo").getItem().getID());
    }

    //***

    public void createDeck(String deckName) {
        for (String template : getDecks()) {
            if (template.toLowerCase().equals(deckName.toLowerCase())) {
                Message.thereIsAlreadyADeckWhitThisName();
                return;
            }
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deckName);
        getAllDecks().put(deckName, deck);
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
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        if (this.getMainDeck().equals(deck)) {
            this.setMainDeck(null);
        }
        getDecks().remove(deckName);
        getAllDecks().remove(deckName);
        Message.deckDeleted();
    }

    public void addCardToDeck(String cardName, String deckName) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        int cardID = getIDByName(cardName);
        if (!this.getCardIDs().contains(cardID)) {
            Message.thereIsNoCardWithThisNameInCollection();
            return;
        }
        Card card = Card.getAllCards().get(cardID);
        if (deck.getCardIDs().contains(cardID)) {
            Message.thereIsACardWithThisNameInThisDeck();
            return;
        }
        if (card instanceof Hero) {
            if (deck.getHero() != null) {
                Message.thereIsAHeroInThisDeck();
                return;
            } else {
                deck.setHero((Hero) card);
                Message.cardAddedToDeckSuccessfully();
                return;
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem() != null) {
                Message.thereIsAnItemInThisDeck();
                return;
            } else {
                deck.setItem((Spell) card);
                Message.cardAddedToDeckSuccessfully();
                return;
            }
        }
        if (deck.getCardIDs().size() == 20) {
            Message.have20CardsInThisDeck();
            return;
        }
        int numberOfCard = 0;
        for (int ID : deck.getCardIDs()) {
            if (ID == cardID) {
                numberOfCard++;
            }
        }
        if (numberOfCard > Collection.getCollection().howManyCard.get(cardName)) {
            Message.notEnoughCardNumber();
            return;
        }
        deck.getCardIDs().add(cardID);
        Message.cardAddedToDeckSuccessfully();
    }

    public void removeCardFromDeck(String cardName, String deckName) {
        int cardID = getIDByName(cardName);
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        if (!Card.getAllCards().containsKey(cardID)) {
            Message.thereIsNoCardWithThisNameInThisDeck();
            return;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        Card card = Card.getAllCards().get(cardID);

        if (card instanceof Hero) {
            if (deck.getHero().equals((Hero) card)) {
                deck.setHero(null);
                Message.cardRemovedFromDeckSuccessfully();
                return;
            } else {
                Message.thereIsNoCardWithThisNameInThisDeck();
                return;
            }
        }
        if (card instanceof Spell) {
            if (deck.getItem().equals((Spell) card)) {
                deck.setItem(null);
                Message.cardRemovedFromDeckSuccessfully();
                return;
            } else {
                Message.thereIsNoCardWithThisNameInThisDeck();
                return;
            }
        }
        if (deck.getCardIDs().contains(cardID)) {
            deck.getCardIDs().remove((Integer) cardID);
            Message.cardRemovedFromDeckSuccessfully();
        } else {
            Message.thereIsNoCardWithThisNameInThisDeck();
        }
    }

    public boolean validateDeck(String deckName, boolean showResultsOrNot) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            if (showResultsOrNot) Message.thereIsNoDeckWithThisName();
            return false;
        }
        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        if (deck.getCardIDs().size() != 20 || deck.getHero() == null) {
            if (showResultsOrNot) Message.deckIsNotValid();
            return false;
        }
        if (showResultsOrNot) Message.deckIsValid();
        return true;
    }

    public void selectMainDeck(String deckName) {
        if (!Account.getActiveAccount().getCollection().getAllDecks().containsKey(deckName)) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        if (validateDeck(deckName, false)) {
            setMainDeck(Account.getActiveAccount().getCollection().getAllDecks().get(deckName));
            Message.deckSelectedAsMain();
        } else {
            Message.deckIsNotValid();
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
