package model;

import model.cards.warriors.Warrior;
import model.effects.Effect;
import model.triggers.Trigger;

import java.util.ArrayList;

public class Cell {
    private Warrior warrior;
    ArrayList<Trigger> triggers = new ArrayList<>();
    ArrayList<Effect> effects = new ArrayList<>();
}
