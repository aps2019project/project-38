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
    protected String describtionOfSpecialPower;
    protected int ID;
    protected String name;
    protected int requiredMana;
    protected int price;

    public Card(int ID, String name, int price, int requiredMana, String descriptionOfSpecialPower) {
        this.describtionOfSpecialPower = descriptionOfSpecialPower;
        this.ID = ID;
        this.name = name;
        this.requiredMana = requiredMana;
        this.price = price;
    }

    public static void getNewCardFromUser() {
        //todo for phase2
    }

    public static void getCardsInfoFromFile() {
        //todo for phase2
    }

    public abstract void apply(Cell cell);

    public static Card deepCopy(Card card) {
        return null;
        //todo
    }

    //***

    public String getDescribtionOfSpecialPower() {
        return describtionOfSpecialPower;
    }

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
}
