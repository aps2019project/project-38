package model.cards;

import model.Cell;

public class Warrior extends Card {
    transient private Cell cell;
    public int hp;
    public int ap;

    public String getWarriorType() {
        //todo server -> melee ranged hybrid
    }
}