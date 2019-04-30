package model.cards;

import model.Cell;
import model.QualityHaver;
import model.effects.Effect;
import model.triggers.Trigger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Card extends QualityHaver implements Serializable {
    protected static HashMap<Integer, Card> allCards = new HashMap<>();
    protected int ID;
    protected String name;
    protected int requiredMana;
    protected int price;
    protected boolean isItem;

    public Card(int ID, String name, int requiredMana, int price, boolean isItem) {
        this.ID = ID;
        this.name = name;
        this.requiredMana = requiredMana;
        this.price = price;
        this.isItem = isItem;
    }

    public abstract void apply(Cell cell);

    public static Card deepCopy(Card card){
        return null;//please write returns so the build attempts don't fail.
        //todo
    }
    //***
    public static HashMap<Integer, Card> getAllCards() {
        return allCards;
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getRequiredMana() {
        return requiredMana;
    }

    public int getPrice() {
        return price;
    }

    public boolean isItem() {
        return isItem;
    }
}
