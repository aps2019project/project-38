package model.player;

import model.Constant;
import model.Deck;
import model.Game;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Warrior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public abstract class Player {
    public int mana;
    protected Deck mainDeck;
    protected HashMap<Integer, Card> hand = new HashMap<>();
    protected ArrayList<Warrior> warriors = new ArrayList<>();
    protected ArrayList<Card> usedCards = new ArrayList<>();
    public boolean ableToReplaceCard;
    private Card nextCard;

    public Player(Deck deck) {
        this.mainDeck = deck;
        for (int i = 0; i < Constant.GameConstants.handSize; i++) {
            hand.put(i, null);
        }
        initializeNextCard();
    }

    public void initializeNextCard() {
        int randomIndex = (new Random(System.currentTimeMillis()).nextInt(this.getMainDeck().getCardIDs().size()));
        nextCard = Card.getAllCards().get(this.getMainDeck().getCardIDs().get(randomIndex)).deepCopy();
    }

    public ArrayList<Card> getUsedCards() {
        return usedCards;
    }

    public ArrayList<Warrior> getWarriors() {
        return warriors;
    }

    public Hero getPlayerHero(){
        return (Hero)warriors.stream().filter(warrior -> warrior instanceof Hero).findFirst().get();
    }

    public HashMap<Integer, Card> getHand() {
        return hand;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public Card getNextCard() {
        return nextCard;
    }

    public Game getGame(){
        return warriors.get(0).getCell().getBoard().getGame();
    }
}