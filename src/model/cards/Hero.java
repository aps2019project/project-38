package model.cards;

import sun.security.krb5.internal.crypto.Des;

public class Hero extends Warrior {
    private Card card;

    public Hero(int ID, String name, int price, int HP, int AP, int requiredMana) {
        super(ID, name, price, requiredMana, HP, AP);
    }
}
