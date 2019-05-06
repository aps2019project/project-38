package model.cards;

import model.Cell;
//used decorator pattern instead of subClassing because i wanted to be able to have a warrior card as powerCard too.
public class HeroPower {
    private Card card;
    private int coolDown;
    public int coolDownRemaining;

    public HeroPower(Card card, int coolDown) {
        this.card = card;
        this.coolDown = coolDown;
    }

    public void use(Cell cell){
        coolDownRemaining=coolDown;
        card.apply(cell);
    }
}
