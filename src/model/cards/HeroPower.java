package model.cards;

import model.Cell;

//used decorator pattern instead of subClassing because i wanted to be able to have a warrior card as powerCard too.
public class HeroPower extends Spell {
    private int coolDown;
    public int coolDownRemaining;

    public HeroPower(Integer ID, String name, int requiredMana, Integer price, boolean isItem, int coolDown) {
        super(ID, name, requiredMana, price, isItem);
        this.coolDown = coolDown;
    }

    @Override
    public void apply(Cell cell) {
        super.apply(cell);
        coolDownRemaining=coolDown;
    }
}
