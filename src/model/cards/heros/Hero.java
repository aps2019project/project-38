package model.cards.heros;

import model.cards.Card;
import model.cards.warriors.Warrior;

public class Hero extends Warrior {
    private Card card;

    public Hero(int ID, String name, int requiredMana, int price, int HP, int AP, Card card) {
        super(ID, name, price, requiredMana, HP, AP);
        this.card = card;
    }
}
