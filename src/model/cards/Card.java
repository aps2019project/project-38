package model.cards;

import model.Cell;
import model.effects.Effect;
import model.triggers.Trigger;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Card {
    private static HashMap<Integer, Card> allCards = new HashMap<>();
    public int ID;
    public String name;
    public int requiredMana;
    public int price;
    public boolean isItem;

    public ArrayList<Trigger> triggers = new ArrayList<>();
    public ArrayList<Effect> effects = new ArrayList<>();

    public abstract void apply(Cell cell);
    //***
    public static HashMap<Integer, Card> getAllCards() {
        return allCards;
    }
}
