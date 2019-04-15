package model.cards;

import model.Cell;
import model.effects.Effect;
import model.triggers.Trigger;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Card {
    public static HashMap<Integer, Card> allCard = new HashMap<>();
    public int ID;
    public String name;
    public int requiredMana;

    public ArrayList<Trigger> triggers = new ArrayList<>();
    public ArrayList<Effect> effects = new ArrayList<>();

    public abstract void apply(Cell cell);
}
