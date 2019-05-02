package model.cards.herospells;

import model.cards.spells.Spell;

public class HeroSpell extends Spell {
    private int coolDown;

    public HeroSpell(int ID, String name, int requiredMana, int price, boolean isItem, String descriptionOfSpecialPower) {
        super(ID, name, requiredMana, price, isItem, descriptionOfSpecialPower);
    }
}
