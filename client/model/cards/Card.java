package model.cards;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Card implements Serializable {
    public Description description = new Description();
    public int ID;
    public String name;
    public int requiredMana;
    public int price;

    public static Card getCardByItsName(String cardName) {
        return getAllCards().get(getIDByName(cardName));
    }

    public static HashMap<Integer, Card> getAllCards() {
        //todo server
    }

    public static boolean checkIsItem(Card card) {
        //todo server
    }

    public static Integer getIDByName(String key) {
        //todo server
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}
