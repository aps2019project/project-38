package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private static HashMap<String, Deck> allDecks = new HashMap<>();
    private static HashMap<String,String> lowerCaseNamesToOriginalName = new HashMap<>();
    private String name;
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private Hero hero;
    private Spell item;
    //***

    public static HashMap<String, String> getLowerCaseNamesToOriginalName() {
        return lowerCaseNamesToOriginalName;
    }

    public void setItem(Spell item) {
        this.item = item;
    }

    public Spell getItem() {
        return item;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public static HashMap<String, Deck> getAllDecks() {
        return allDecks;
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
}
