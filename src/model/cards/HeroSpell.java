package model.cards;

public class HeroSpell extends Spell {
    int coolDown;

    public HeroSpell(int ID, String name, int requiredMana, int price, boolean isItem, int coolDown) {
        super(ID, name, price, requiredMana, isItem);
        this.coolDown = coolDown;
    }
}
