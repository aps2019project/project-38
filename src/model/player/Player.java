package model.player;

import model.Constant;
import model.Deck;
import model.cards.Card;
import model.cards.warriors.Warrior;

import java.util.ArrayList;
import java.util.Random;


public abstract class Player {
    private int mana = Constant.GameConstants.primaryMana;
    private ArrayList<Card> hand = new ArrayList<>();
    private Deck mainDeck;
    private ArrayList<Integer> usedCards = new ArrayList<>();
    private ArrayList<Warrior> warriors = new ArrayList<>();

    public Player(Deck deck) {
        this.mainDeck = deck;
        warriors.add(deck.getHero());
        Random random = new Random();
        for (int i = 0; i < Constant.GameConstants.handSize; i++) {
            int randomIndex = random.nextInt(this.mainDeck.getCardIDs().size());
            hand.add(Card.getAllCards().get(this.mainDeck.getCardIDs().get(randomIndex)));
        }
    }

    public ArrayList<Warrior> getWarriors() {
        return warriors;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }
}
