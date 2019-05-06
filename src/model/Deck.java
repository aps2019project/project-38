package model;

import model.cards.Card;
import model.cards.CardFactory;
import model.cards.Hero;
import model.cards.Spell;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private static HashMap<String, Deck> allDecks = new HashMap<>();
    private static HashMap<String,String> lowerCaseNamesToOriginalName = new HashMap<>();
    public static ArrayList<Deck> levels = new ArrayList<>();
    private String name;
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private Hero hero;
    private Card item;
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
        int[] minionIndex = {1, 9, 11, 13, 17, 18, 21, 22, 26, 36, 38, 40};
        for (int i = 0; i < 12; i++) {
            deck1.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i]).getID());
        }
    }
    static {
        Deck deck2 = new Deck();
        deck2.setHero((Hero)CardFactory.getAllBuiltHeroes().get(5));
        deck2.setItem((Spell)CardFactory.getAllBuiltItems().get(18));
        //***
        int[] spellIndex = {2,3,5,8,9,13,19};
        for(int i=0;i<7;i++){
            deck2.getCardIDs().add(CardFactory.getAllBuiltSpells().get(spellIndex[i]).getID());
        }
        int[] minionIndex = {};
        for(int i=0;i<12;i++){
            deck2.getCardIDs().add(CardFactory.getAllBuiltMinions().get(minionIndex[i]).getID());
        }
    }

    public static HashMap<String, String> getLowerCaseNamesToOriginalName() {
        return lowerCaseNamesToOriginalName;
    }

    public void setItem(Card item) {
        this.item = item;
    }

    public Card getItem() {
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
