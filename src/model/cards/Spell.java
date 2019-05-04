package model.cards;

import model.Cell;

public class Spell extends Card {
    private boolean isItem;
    public String target;

    public Spell(int ID, String name, int price, int requiredMana, String target , boolean isItem) {
        super(ID, name, price, requiredMana);
        this.target = target;
        this.isItem = isItem;
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

    @Override
    public void apply(Cell cell) {

    }

    @Override
    public Spell deepCopy() {
        //todo
        return null;
    }
}
