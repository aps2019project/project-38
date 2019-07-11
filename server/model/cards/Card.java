package model.cards;

import model.Cell;
import model.QualityHaver;
import model.Shop;
import model.effects.Effect;
import model.exceptions.NotEnoughConditions;
import model.triggers.Trigger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class Card extends QualityHaver implements Serializable {
    transient public static HashMap<Integer, Card> allCards = new HashMap<>();
    public Description description = new Description();
    protected String name;
    protected int requiredMana;
    protected int price;
    private int ID;

    public Card(Integer ID, String name, Integer price, int requiredMana) {
        this.ID = ID;
        this.name = name;
        this.requiredMana = requiredMana;
        this.price = price;
    }

    public static int getIDByName(String cardName) {
        for (int ID : Shop.getShop().getCardIDs()) {
            if (Card.getAllCards().get(ID).getName().equals(cardName)) {
                return ID;
            }
        }
        return -1;
    }

    public abstract void apply(Cell cell) throws NotEnoughConditions;

    public abstract Card deepCopy();

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

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static Card getCardByItsName(String cardName) {
        return Card.getAllCards().values().stream()
                .filter(card -> card.getName().equals(cardName)).findFirst().orElse(null);
    }
}
