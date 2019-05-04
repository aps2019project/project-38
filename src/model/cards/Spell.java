package model.cards;

import model.Cell;

public class Spell extends Card {
    private int coolDown;
    private boolean isItem;

    public Spell(int ID, String name, int requiredMana, int price, boolean isItem,int coolDown) {
        super(ID, name, price, requiredMana);
        this.isItem = isItem;
        this.coolDown=coolDown;
    }

    public static boolean checkIsItem(Card card) {
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            return spell.isItem;
        }
        return false;
    }

    public boolean isItem() {
        return isItem;
    }

    public int getCoolDown() {
        return coolDown;
    }

    @Override
    public void apply(Cell cell) {

    }

    @Override
    public Spell deepCopy() {
        //todo
        return null;
    }
}
