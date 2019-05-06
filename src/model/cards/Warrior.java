package model.cards;

import model.Cell;
import model.player.Player;

public class Warrior extends Card {
    private Cell cell;
    private int hp;
    private int ap;

    public Warrior(Integer ID, String name, Integer price, int requiredMana, int hp, int ap) {
        super(ID, name, price, requiredMana);
        this.hp = hp;
        this.ap = ap;
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
        cell.setWarrior(this);
        this.cell=cell;
    }

    @Override
    public Warrior deepCopy() {
        return null;
    }
}