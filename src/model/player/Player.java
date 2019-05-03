package model.player;

import model.Constant;
import model.Deck;
import model.cards.Card;
import model.cards.heros.Hero;
import model.cards.warriors.Warrior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public abstract class Player {
    public int mana;
    private Deck mainDeck;
    private HashMap<Integer, Card> hand;
    private ArrayList<Warrior> warriors = new ArrayList<>();
    private ArrayList<Integer> usedCards = new ArrayList<>();
    public boolean ableToReplaceCard;

    public Player(Deck deck) {
        this.mainDeck = deck;
        warriors.add(deck.getHero().deepCopy());
        Random random = new Random();
        for (int i = 0; i < Constant.GameConstants.handSize; i++) {
            int randomIndex = random.nextInt(this.mainDeck.getCardIDs().size());
            hand.put(i, Card.getAllCards().get(mainDeck.getCardIDs().get(randomIndex)).deepCopy());
        }
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
}