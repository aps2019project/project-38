package model.cards.spells;

import model.Cell;
import model.cards.Card;

public class Spell extends Card {
    private boolean isItem;

    public Spell(int ID, String name, int requiredMana, int price, boolean isItem) {
        super(ID, name, price, requiredMana);
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
