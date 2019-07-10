package model.cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    public Description description = new Description();
    public int ID;
    public String name;
    public int requiredMana;
    public int price;

    public String getName() {
        return name;
    }

    public static Card getCardByItsName(String name) {

    }

    public static int getIDByName(String cardName) {

    }
}
