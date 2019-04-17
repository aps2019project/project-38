package model.player;

import model.Deck;
import model.cards.Card;
import model.cards.warriors.Warrior;

import java.util.ArrayList;


public abstract class Player {
    private int mana;
    private int endTime;
    private ArrayList<Card> hand = new ArrayList<>();
    private Deck mainDeck;
    private ArrayList<Integer> graveYard = new ArrayList<>();
    private ArrayList<Warrior> warriors = new ArrayList<>();

    public ArrayList<Warrior> getWarriors() {
        return warriors;
    }
}
