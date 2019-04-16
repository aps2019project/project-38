package model;

import model.cards.Card;
import view.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop {
    private HashMap<String, Card> cardNameToCardObject = new HashMap<>();
    private static Shop shop = new Shop();

    //***
//    public static void showShopMenu() {
//        while (true) {
//
//        }
//    }
    public int searchInShopCards(String objectName) {
        if (shop.getCardNameToCardObject().containsKey(objectName)) {
            Card card = getCardNameToCardObject().get(objectName);
            return card.ID;
        } else {
            Message.thereIsNoCardWithThisName();
            return -1;
        }
    }

    public ArrayList<Integer> searchInCollectionCards(String objectName) {
        Account account = Account.getActiveAccount();
        Collection collection = account.collection;
        ArrayList<Integer> foundIDs = new ArrayList<>();
        for (int i : collection.getCards()) {
            Card card = Card.getAllCard().get(i);
            if (card.name.equals(objectName)) {
                foundIDs.add(i);
            }
        }
        if (foundIDs.size() == 0) {
            Message.thereIsNoCardWithThisName();
        }
        return foundIDs;
    }

    public void buy(String objectName) {
        Card card = getCardNameToCardObject().get(objectName);
        Account account = Account.getActiveAccount();
        if (!getCardNameToCardObject().containsKey(objectName)) {
            Message.thereIsNoCardWithThisName();
            return;
        }
        if (account.collection.getCards().size() >= 20) {
            Message.havaMoreThan20Cards();
            return;
        }

        if (account.getMoney() >= card.price) {
            if (card.isItem) {
                int numberOfItems = 0;
                for (int i : account.collection.getCards()) {
                    Card card1 = Card.getAllCard().get(i);
                    if (card1.isItem) numberOfItems++;
                }
                if (numberOfItems >= 3) {
                    Message.haveMoreThan3Items();
                }
            }
            account.setMoney(account.getMoney() - card.price);
            Message.buyWasSuccessful();
        } else {
            Message.haveNotEnoughMoney();
        }
    }

    public void sell(int ID) {

    }
//    public Card deepCopy(Card card){
//
//    }
    //***

    public HashMap<String, Card> getCardNameToCardObject() {
        return cardNameToCardObject;
    }

    public static Shop getShop() {
        return shop;
    }
}
