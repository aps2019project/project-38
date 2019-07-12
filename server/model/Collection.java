package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import server.net.Message;
import server.net.ServerSession;

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
        ServerSession serverSession = getServerSession();
        if (decks.contains(deckName)) {
            serverSession.encoder.sendPackage(Message.CreateDeck, false, "There is already a deck with this name");
            return;
        }
        Deck deck = new Deck();
        deck.setName(deckName);
        getDecks().add(deckName);
        getAllDecks().put(deckName, deck);
        Deck.getAllDecks().put(deckName, deck);
        serverSession.encoder.sendPackage(Message.CreateDeck, true, "Deck created");
    }

    public void deleteDeck(String deckName) {
        ServerSession serverSession = getServerSession();
        Deck deck = getAccount().getCollection().getAllDecks().get(deckName);
        if (this.getMainDeck() != null && this.getMainDeck().equals(deck)) {
            this.setMainDeck(null);
        }
        getDecks().remove(deckName);
        getAllDecks().remove(deckName);
        Deck.getAllDecks().remove(deckName);
        serverSession.encoder.sendPackage(Message.DeleteDeck, true, "Deck deleted");
    }

    public void addCardToDeck(String cardName, String deckName) {
        ServerSession serverSession = getServerSession();
        Deck deck = getAccount().getCollection().getAllDecks().get(deckName);
        int cardID = Card.getIDByName(cardName);
        Card card = Card.getAllCards().get(cardID);
        if (card instanceof Hero) {
            if (deck.getHero() != null) {
                serverSession.encoder.sendPackage(Message.AddCardToDeck,
                        false, "There is already a hero in this deck. You can't add any other");
                return;
            } else {
                deck.setHero((Hero) card);
                deck.getCardIDs().add(cardID);
                serverSession.encoder.sendPackage(Message.AddCardToDeck, true, "Card added to deck successfully");
                return;
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem() != null) {
                serverSession.encoder.sendPackage(Message.AddCardToDeck, false, "There is an item in this deck");
                return;
            } else {
                deck.setItem((Spell) card);
                deck.getCardIDs().add(cardID);
                serverSession.encoder.sendPackage(Message.AddCardToDeck, true, "Card added to deck successfully");
                return;
            }
        }
        if (deck.getCardIDs().size() == 20) {
            serverSession.encoder.sendPackage(Message.AddCardToDeck,
                    false, "You have 20 cards in your deck. You couldn't put any other card");
            return;
        }
        deck.getCardIDs().add(cardID);
        if (card instanceof Warrior) {
            deck.minions.add(card);
        } else {
            deck.spells.add(card);
        }
        serverSession.encoder.sendPackage(Message.AddCardToDeck, true, "Card added to deck successfully");
    }

    public void removeCardFromDeck(String cardName, String deckName) {
        ServerSession serverSession = getServerSession();
        int cardID = Card.getIDByName(cardName);
        Deck deck = getAccount().getCollection().getAllDecks().get(deckName);
        Card card = Card.getAllCards().get(cardID);
        if (card instanceof Hero) {
            if (deck.getHero().equals((Hero) card)) {
                deck.setHero(null);
                serverSession.encoder.sendPackage(Message.RemoveCardFromDeck,
                        true, "Card removed from deck successfully");
                return;
            } else {
                serverSession.encoder.sendPackage(Message.RemoveCardFromDeck,
                        false, "There is no card with this name in this deck");
                return;
            }
        }
        if (Spell.checkIsItem(card)) {
            if (deck.getItem().equals((Spell) card)) {
                deck.setItem(null);
                serverSession.encoder.sendPackage(Message.RemoveCardFromDeck,
                        true, "Card removed from deck successfully");
                return;
            } else {
                serverSession.encoder.sendPackage(Message.RemoveCardFromDeck,
                        false, "There is no card with this name in this deck");
                return;
            }
        }
        if (deck.getCardIDs().contains(cardID)) {
            deck.getCardIDs().remove((Integer) cardID);
            if (card instanceof Warrior) {
                deck.minions.remove(card);
            } else {
                deck.spells.remove(card);
            }
            serverSession.encoder.sendPackage(Message.RemoveCardFromDeck,
                    true, "Card removed from deck successfully");
        } else {
            serverSession.encoder.sendPackage(Message.RemoveCardFromDeck,
                    false, "There is no card with this name in this deck");
        }
    }

    private boolean validateDeck(String deckName) {
        Deck deck = getAccount().getCollection().getAllDecks().get(deckName);
        if (deck.getCardIDs().size() != 20 || deck.getHero() == null) {
            return false;
        }
        return true;
    }

    public void selectMainDeck(String deckName) {
        ServerSession serverSession = getServerSession();
        if (validateDeck(deckName)) {
            setMainDeck(getAccount().getCollection().getAllDecks().get(deckName));
            serverSession.encoder.sendPackage(Message.SelectMainDeck,
                    true, "This deck selected as main successfully");
        } else {
            serverSession.encoder.sendPackage(Message.SelectMainDeck,
                    false, "This deck is not valid");
        }
    }

    public void renameDeck(String deckName, String newName) {
        ServerSession serverSession = getServerSession();
        if (getAccount().getCollection().getAllDecks().containsKey(newName)) {
            serverSession.encoder.sendPackage(Message.RenameDeck,
                    false, "You have an another deck with this name");
            return;
        }
        Deck deck = getAccount().getCollection().getAllDecks().get(deckName);
        deck.setName(newName);
        allDecks.remove(deckName);
        decks.remove(deckName);
        allDecks.put(newName, deck);
        decks.add(newName);
        serverSession.encoder.sendPackage(Message.RenameDeck, true, "Deck renamed successfully");
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

    private Account getAccount() {
        return Account.getUsernameToAccount().entrySet().stream()
                .filter(entry -> entry.getValue().getCollection().equals(this)).findAny().get().getValue();
    }

    private ServerSession getServerSession() {
        return ServerSession.getSession(getAccount().getUsername());
    }
}
