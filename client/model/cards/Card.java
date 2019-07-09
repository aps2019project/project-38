package model.cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    public Description description = new Description();
    public int ID;
    public String name;
    public int requiredMana;
    public int price;


    public static Card getCardByItsName(String cardName) {
        // todo HASHEM آیا واقعن میخواد؟؟
        return null;
    }
}
