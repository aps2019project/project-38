package model;

import model.cards.Card;
import view.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop {
    private static Shop shop = new Shop();
    private HashMap<String, Card> cardNameToCardObject = new HashMap<>();

    //***
    public int searchInShopCards(String cardName) {
        if (shop.getCardNameToCardObject().containsKey(cardName)) {
            Card card = getCardNameToCardObject().get(cardName);
            return card.ID;
        } else {
            Message.thereIsNoCardWithThisName();
            return -1;
        }
    }

    public ArrayList<Integer> searchInCollectionCards(String cardName) {
        Account account = Account.getActiveAccount();
        Collection collection = account.collection;
        ArrayList<Integer> foundIDs = new ArrayList<>();
        for (int i : collection.getCards()) {
            Card card = Card.getAllCard().get(i);
            if (card.name.equals(cardName)) {
                foundIDs.add(i);
            }
        }
        if (foundIDs.size() == 0) {
            Message.thereIsNoCardWithThisName();
        }
        return foundIDs;
    }

    public void buy(String cardName) {
        Card card = getCardNameToCardObject().get(cardName);
        Account account = Account.getActiveAccount();
        if (!getCardNameToCardObject().containsKey(cardName)) {
            Message.thereIsNoCardWithThisName();
            return;
        }
        if (account.collection.getCards().size() >= 20) {
            Message.hava20Cards();
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
                    Message.have3Items();
                    return;
                }
            }
            account.setMoney(account.getMoney() - card.price);
            Message.buyWasSuccessful();
        } else {
            Message.haveNotEnoughMoney();
        }
    }

    public void sell(int cardID) {
        Account account = Account.getActiveAccount();
        Card card = Card.getAllCard().get(cardID);
        if (!account.collection.getCards().contains(cardID)) {
            Message.haveNotThisCard();
            return;
        }
        Message.sellWasSuccessful();
        account.setMoney(account.getMoney() + card.price);
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
