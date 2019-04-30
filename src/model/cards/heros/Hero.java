package model.cards.heros;

import model.cards.Card;
import model.cards.warriors.Warrior;

public class Hero extends Warrior {
    private Card card;

    public Hero(int ID, String name, int requiredMana, int price, boolean isItem, int HP, int AP, Card card) {
        super(ID, name, requiredMana, price, HP, AP, isItem);
        this.card = card;
    }
}
