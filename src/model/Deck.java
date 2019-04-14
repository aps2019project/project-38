package model;

import model.cards.Card;
import model.cards.heros.Hero;
import model.cards.warriors.Warrior;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Integer> cards = new ArrayList<>();
    private Hero hero;
    private Card item;
    public static ArrayList<Deck> deafultDecks = new ArrayList<>();

    //***
    public void deepCopy(Deck deck) {

    }

    public void isValid() {

    }
    //***

    public ArrayList<Integer> getCards() {
        return cards;
    }

    public Hero getHero() {
        return hero;
    }

    public Card getItem() {
        return item;
    }

    public static ArrayList<Deck> getDeafultDecks() {
        return deafultDecks;
    }
}
