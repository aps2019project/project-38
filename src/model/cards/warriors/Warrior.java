package model.cards.warriors;

import model.Cell;
import model.cards.Card;
import model.player.Player;

public abstract class Warrior extends Card {
    Player player;
    Cell cell;
    int HP;
    int AP;

    private int moveCount;

    public int getMoveCount(){
       return 0; // todo
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getHP() {
        return HP;//todo should check all effects for the overall HP.
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getAP() {
        return AP;//todo should check all effects for the overall AP.
    }

    public void setAP(int AP) {
        this.AP = AP;
    }


}
