package model.cards.warriors;

import model.Cell;
import model.cards.Card;
import model.player.Player;

public abstract class Warrior extends Card {
    Cell cell;

    int HP;
    int AP;

    private int moveCount;

    public int getMoveCount(){
        return 0;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }


}
