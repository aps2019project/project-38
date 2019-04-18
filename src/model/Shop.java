package model;

import model.cards.Card;
import view.Message;

import java.util.ArrayList;

public class Shop {
    private static Shop shop = null;
    private ArrayList<Integer> cardIDs = new ArrayList<>();

    //***
    public int searchInShopCards(String cardName) {
        for (int ID : shop.getCardIDs()) {
            Card card = Card.getAllCards().get(ID);
            if (card.name.equals(cardName)) {
                return ID;
            }
        }
        Message.thereIsNoCardWithThisName();
        return -1;
    }

    public ArrayList<Integer> searchInCollectionCards(String cardName) {
        Account account = Account.getActiveAccount();
        Collection collection = account.getCollection();
        ArrayList<Integer> foundIDs = new ArrayList<>();
        for (int ID : collection.getCardIDs()) {
            Card card = Card.getAllCards().get(ID);
            if (card.name.equals(cardName)) {
                foundIDs.add(ID);
            }
        }
        if (foundIDs.size() == 0) {
            Message.thereIsNoCardWithThisName();
        }
        return foundIDs;
    }

    public void buy(String cardName) {
        Card card = null;
        for (int ID : shop.getCardIDs()) {
            if (Card.getAllCards().get(ID).name.equals(cardName)) {
                card = Card.getAllCards().get(ID);
            }
        }
        Account account = Account.getActiveAccount();
        if (card == null) {
            Message.thereIsNoCardWithThisName();
            return;
        }
        if (account.getCollection().getCardIDs().size() >= 20) {
            Message.have20Cards();
            return;
        }
        if (account.getMoney() >= card.price) {
            if (card.isItem) {
                int numberOfItems = 0;
                for (int ID : account.getCollection().getCardIDs()) {
                    Card card1 = Card.getAllCards().get(ID);
                    if (card1.isItem) numberOfItems++;
                }
                if (numberOfItems >= 3) {
                    Message.have3Items();
                    return;
                }
            }
            account.setMoney(account.getMoney() - card.price);
            shop.getCardIDs().remove(card.ID);
            account.getCollection().getCardIDs().add(card.ID);
            account.getCollection().getAllCards().put(card.ID, deepCopy(card));
            Message.buyWasSuccessful();
        } else {
            Message.haveNotEnoughMoney();
        }
    }

    public void sell(int cardID) {
        Account account = Account.getActiveAccount();
        Card card = Card.getAllCards().get(cardID);
        if (!account.getCollection().getCardIDs().contains(cardID)) {
            Message.haveNotThisCard();
            return;
        }
        account.setMoney(account.getMoney() + card.price);
        shop.getCardIDs().add(card.ID);
        account.getCollection().getCardIDs().remove(card.ID);
        account.getCollection().getAllCards().remove(card.ID);
        Message.sellWasSuccessful();
    }

    private Card deepCopy(Card card) {
        //todo
        return null;
    }
    //***

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public static Shop getShop() {
        return shop;
    }
}
