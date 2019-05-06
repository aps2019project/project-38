package model;

import model.cards.Card;
import model.cards.CardFactory;
import model.cards.Hero;
import model.cards.Spell;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private static HashMap<String, Deck> allDecks = new HashMap<>();
    private static HashMap<String, String> lowerCaseNamesToOriginalName = new HashMap<>();
    public static ArrayList<Deck> levels = new ArrayList<>();
    private String name;
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private Hero hero;
    private Spell item;
    //***

    static {
        Deck deck1 = new Deck();
        deck1.setHero((Hero) CardFactory.getAllBuiltHeroes().get(1));
        deck1.setItem((Spell) CardFactory.getAllBuiltItems().get(1));
        //***
        int[] spellIndex = {1, 7, 10, 11, 12, 18, 20};
        for (int i = 0; i < 7; i++) {
            deck1.getCardIDs().add(CardFactory.getAllBuiltSpells().get(spellIndex[i]).getID());
        }
        int[] minionIndex = {1, 9, 11, 11, 13, 17, 18, 21, 22, 26, 36, 38, 40};
        for (int i = 0; i < 13; i++) {
            deck1.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i]).getID());
        }
        levels.add(deck1);
    }

    static {
        Deck deck2 = new Deck();
        deck2.setHero((Hero) CardFactory.getAllBuiltHeroes().get(5));
        deck2.setItem((Spell) CardFactory.getAllBuiltItems().get(18));
        //***
        int[] spellIndex = {2, 3, 5, 8, 9, 13, 19};
        for (int i = 0; i < 7; i++) {
            deck2.getCardIDs().add(CardFactory.getAllBuiltSpells().get(spellIndex[i]).getID());
        }
        int[] minionIndex = {2, 3, 5, 8, 12, 15, 15, 19, 23, 27, 30, 33, 39};
        for (int i = 0; i < 13; i++) {
            deck2.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i]).getID());
        }
        levels.add(deck2);
    }

    static {
        Deck deck3 = new Deck();
        deck3.setHero((Hero) CardFactory.getAllBuiltHeroes().get(7));
        deck3.setItem((Spell) CardFactory.getAllBuiltItems().get(12));
        //***
        int[] spellIndex = {6, 10, 12, 14, 15, 16, 17};
        for (int i = 0; i < 7; i++) {
            deck3.getCardIDs().add(CardFactory.getAllBuiltSpells().get(spellIndex[i]).getID());
        }
        int[] minionIndex = {6, 7, 10, 14, 16, 16, 20, 24, 25, 28, 29, 31, 34};
        for (int i = 0; i < 13; i++) {
            deck3.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i]).getID());
        }
        levels.add(deck3);
    }

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
