package model;

import model.cards.Warrior;
import model.triggers.Trigger;

import java.io.Serializable;
import java.util.ArrayList;

public class Cell extends QualityHaver implements Serializable {
    private Board board;
    private Warrior warrior;
    private int row;
    private int column;

    public Cell (Board board, int row, int column) {
        this.row = row;
        this.column = column;
        this.board=board;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Board getBoard() {
        return board;
    }
}
