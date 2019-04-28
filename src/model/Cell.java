package model;

import model.cards.warriors.Warrior;
import model.triggers.Trigger;

import java.util.ArrayList;

public class Cell {
    private Game game;
    private Warrior warrior;
    private ArrayList<Trigger> triggers = new ArrayList<>();
    private int row;
    private int column;

    public Cell (int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Cell(Game game) {
        this.game = game;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Game getGame() {
        return game;
    }
}
