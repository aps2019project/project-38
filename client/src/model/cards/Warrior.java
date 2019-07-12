package model.cards;

import client.net.Digikala;
import model.Cell;

public class Warrior extends Card {
    transient private Cell cell;
    public int hp;
    public int ap;

    public String getWarriorType() {
        return Digikala.getWarriorType(this.ID);
    }
}