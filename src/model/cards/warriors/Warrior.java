package model.cards.warriors;

import model.Cell;
import model.Game;
import model.cards.Card;
import model.player.Player;

public class Warrior extends Card {
    private Player player;
    private Cell cell;
    private int HP;
    private int AP;

    public Warrior(int ID, String name, int requiredMana, int price, boolean isItem, int HP, int AP) {
        super(ID, name, requiredMana, price, isItem);
        this.HP = HP;
        this.AP = AP;
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

    public int getAP() {
        return AP;//todo should check all effects for the overall AP.
    }

    @Override
    public void apply(Cell cell) {

    }
}