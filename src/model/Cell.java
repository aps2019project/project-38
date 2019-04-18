package model;

import model.cards.warriors.Warrior;
import model.triggers.Trigger;

import java.util.ArrayList;

public class Cell {
    Game game;
    private Warrior warrior;
    ArrayList<Trigger> triggers = new ArrayList<>();

    public Cell(Game game) {
        this.game = game;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }
}
