package model;

import model.cards.Card;
import model.cards.heros.Hero;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private static ArrayList<Deck> defaultDeck = new ArrayList<>();
    private static HashMap<String, Deck> allDecks = new HashMap<>();
    private String name;
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private Hero hero;
    private Card item;
    //***
    //***

    public void setItem(Card item) {
        this.item = item;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public static HashMap<String, Deck> getAllDecks() {
        return allDecks;
    }

    public static ArrayList<Deck> getDefaultDeck() {
        return defaultDeck;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public Hero getHero() {
        return hero;
    }

    public Card getItem() {
        return item;
    }
}
