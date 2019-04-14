package model;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Integer> cards = new ArrayList<>();
    private Warior hero;
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

    public Warior getHero() {
        return hero;
    }

    public Card getItem() {
        return item;
    }

    public static ArrayList<Deck> getDeafultDecks() {
        return deafultDecks;
    }
}
