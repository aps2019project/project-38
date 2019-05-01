package model.cards.spells;

import model.Cell;
import model.cards.Card;

public class Spell extends Card {
    public boolean isItem = false;

    public Spell(int ID, String name, int requiredMana, int price, boolean isItem, String descriptionOfSpecialPower) {
        super(ID, name, price, requiredMana, descriptionOfSpecialPower);
        this.isItem = isItem;
    }

    public static boolean checkIsItem(Card card) {
        if (card instanceof Spell) {
            Spell spell = (Spell) card;
            return spell.isItem;
        }
        return false;
    }

    @Override
    public void apply(Cell cell) {

    }
}
