package model.cards;

import client.net.Digikala;

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
        return Digikala.getAllCards();
    }

    public static boolean checkIsItem(Card card) {
        //todo server
        return false;
    }

    public static Integer getIDByName(String key) {
        return Digikala.getIDByName(key);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}
