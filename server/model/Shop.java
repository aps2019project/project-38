package model;

import model.cards.Card;

import java.util.ArrayList;

public class Shop {
    private static Shop shop = new Shop();
    private ArrayList<Integer> cardIDs = new ArrayList<>();

    private Shop() {
    }

    public void sell(Account destinationAccount, Account targetAccount, Card card, int price) {
        //todo ali (targetAccount can be null)
    }

    public void buy(Account account, Card card, int price) {
        //todo ali
    }

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public static Shop getShop() {
        return shop;
    }
}
