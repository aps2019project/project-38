package model.cards;

public class HeroSpell extends Spell {
    private int coolDown;

    public HeroSpell(int ID, String name, int requiredMana, int price, boolean isItem) {
        super(ID, name, price, requiredMana, isItem);
    }
}
