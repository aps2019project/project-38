package model.cards.spells;

import model.cards.Card;

public abstract class Spell extends Card {
    public Spell(int ID, String name, int requiredMana, int price, boolean isItem) {
        super(ID, name, requiredMana, price, isItem);
    }
}
