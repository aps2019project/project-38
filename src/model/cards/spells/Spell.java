package model.cards.spells;

import model.Cell;
import model.cards.Card;

public class Spell extends Card {
    public Spell(int ID, String name, int requiredMana, int price, boolean isItem) {
        super(ID, name, requiredMana, price, isItem);
    }

    @Override
    public void apply(Cell cell) {

    }
}
