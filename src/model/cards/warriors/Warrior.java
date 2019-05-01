package model.cards.warriors;

import model.Cell;
import model.cards.Card;
import model.player.Player;

public class Warrior extends Card {
    private Cell cell;
    private int hp;
    private int ap;

    public Warrior(int ID, String name, int requiredMana, int price, int hp, int ap, boolean isItem) {
        super(ID, name, requiredMana, price, isItem);
        this.hp = hp;
        this.ap = ap;
    }

    public Warrior deepCopy() {
        return null;
        //todo
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getHp() {
        return hp;//todo should check all effects for the overall hp.
    }

    public int getAp() {
        return ap;//todo should check all effects for the overall ap.
    }

    @Override
    public void apply(Cell cell) {

    }
}