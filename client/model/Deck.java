package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Deck implements Serializable { //todo ALI فک کنم کلاینت دِک و کالکشن و شاپ نمیخواد
    private static HashMap<String, Deck> allDecks = new HashMap<>();
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private String name;
    public Spell item;
    public Hero hero;
    public ArrayList<Warrior> minions = new ArrayList<>();
    public ArrayList<Spell> spells = new ArrayList<>();

    //***

    public static void deckLevelBuilder() {
        {
            Deck deck1 = new Deck();
            deck1.name = "level1";
            deck1.setHero((Hero) CardFactory.getAllBuiltHeroes().get(1));
            deck1.setItem((Spell) CardFactory.getAllBuiltItems().get(13));
            //***
            int[] spellIndex = {1, 7, 10, 11, 12, 18, 20};
            for (int i = 0; i < 7; i++) {
                deck1.getCardIDs().add(CardFactory.getAllBuiltSpells().get(spellIndex[i] - 1).getID());
            }
            int[] minionIndex = {1, 9, 11, 11, 13, 17, 18, 21, 22, 26, 36, 38, 40};
            for (int i = 0; i < 13; i++) {
                deck1.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i] - 1).getID());
            }
            allDecks.put("level1", deck1);
        }
        {
            Deck deck2 = new Deck();
            deck2.name = "level2";

            deck2.setHero((Hero) CardFactory.getAllBuiltHeroes().get(5));
            deck2.setItem((Spell) CardFactory.getAllBuiltItems().get(18));
            //***
            int[] spellIndex = {2, 3, 5, 8, 9, 13, 19};
            for (int i = 0; i < 7; i++) {
                deck2.getCardIDs().add(CardFactory.getAllBuiltSpells().get(spellIndex[i] - 1).getID());
            }
            int[] minionIndex = {2, 3, 5, 8, 12, 15, 15, 19, 23, 27, 30, 33, 39};
            for (int i = 0; i < 13; i++) {
                deck2.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i] - 1).getID());
            }
            allDecks.put("level2", deck2);
        }
        {
            Deck deck3 = new Deck();
            deck3.name = "level3";

            deck3.setHero((Hero) CardFactory.getAllBuiltHeroes().get(7));
            deck3.setItem((Spell) CardFactory.getAllBuiltItems().get(12));
            //***
            int[] spellIndex = {6, 10, 12, 14, 15, 16, 17};
            for (int i = 0; i < 7; i++) {
                deck3.getCardIDs().add(CardFactory.getAllBuiltSpells().get(spellIndex[i] - 1).getID());
            }
            int[] minionIndex = {6, 7, 10, 14, 16, 16, 20, 24, 25, 28, 29, 31, 34};
            for (int i = 0; i < 13; i++) {
                deck3.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i] - 1).getID());
            }
            allDecks.put("level3", deck3);
        }
        {
            Deck allCombo = new Deck();
            allCombo.name = "allCombo";
            allCombo.setHero((Hero) CardFactory.getAllBuiltHeroes().get(8));
            allCombo.setItem((Spell) CardFactory.getAllBuiltItems().get(13));
            //***
            int[] minionIndex = {6, 12, 39, 40, 6, 12, 39, 40, 6, 12, 39, 40, 6, 12, 39, 40, 6, 12, 39, 40};
            for (int i = 0; i < 20; i++) {
                allCombo.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i] - 1).getID());
            }
            allDecks.put("allCombo", allCombo);
        }
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

    public static HashMap<String, Deck> getAllDecks() {
        return allDecks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(name, deck.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
