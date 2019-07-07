package model.cards;

public class Hero extends Warrior {
    HeroPower power;

    public Hero(Integer ID, String name, Integer price, int HP, int AP, int requiredMana) {
        super(ID, name, price, requiredMana, HP, AP);
    }

    public HeroPower getPower() {
        return power;
    }
}
