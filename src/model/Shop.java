package model;

import model.cards.Card;
import view.Message;

import java.util.ArrayList;

public class Shop {
    private static Shop shop = new Shop();
    private ArrayList<Integer> cardIDs = new ArrayList<>();

    //***
    public void putCardsFromCardsSourceInShopSource(){
        //todo
    }

    public int searchInShopCards(String cardName) {
        for (int ID : shop.getCardIDs()) {
            Card card = Card.getAllCards().get(ID);
            if (card.getName().equals(cardName)) {
                return ID;
            }
        }
        Message.thereIsNoCardWithThisNameInShop();
        return -1;
    }

    public ArrayList<Integer> searchInCollectionCards(String cardName) {
        Account account = Account.getActiveAccount();
        Collection collection = account.getCollection();
        ArrayList<Integer> foundIDs = new ArrayList<>();
        for (int ID : collection.getCardIDs()) {
            Card card = Card.getAllCards().get(ID);
            if (card.getName().equals(cardName)) {
                foundIDs.add(ID);
            }
        }
        if (foundIDs.size() == 0) {
            Message.thereIsNoCardWithThisNameInCollection();
        }
        return foundIDs;
    }

    public void buy(String cardName) {
        Card card = null;
        for (int ID : shop.getCardIDs()) {
            if (Card.getAllCards().get(ID).getName().equals(cardName)) {
                card = Card.getAllCards().get(ID);
            }
        }
        Account account = Account.getActiveAccount();
        if (card == null) {
            Message.thereIsNoCardWithThisNameInShop();
            return;
        }
        if (account.getMoney() >= card.getPrice()) {
            if (card.isItem()) {
                int numberOfItems = 0;
                for (int ID : account.getCollection().getCardIDs()) {
                    Card card1 = Card.getAllCards().get(ID);
                    if (card1.isItem()) numberOfItems++;
                }
                if (numberOfItems >= 3) {
                    Message.have3Items();
                    return;
                }
            }
            account.setMoney(account.getMoney() - card.getPrice());
            shop.getCardIDs().remove(card.getID());
            account.getCollection().getCardIDs().add(card.getID());
            account.getCollection().getAllCards().put(card.getID(), Card.deepCopy(card));
            Message.buyWasSuccessful();
        } else {
            Message.haveNotEnoughMoney();
        }
    }

    public void sell(int cardID) {
        Account account = Account.getActiveAccount();
        Card card = Card.getAllCards().get(cardID);
        if (!account.getCollection().getCardIDs().contains(cardID)) {
            Message.haveNotThisCardInYourCollection();
            return;
        }
        account.setMoney(account.getMoney() + card.getPrice());
        shop.getCardIDs().add(card.getID());
        account.getCollection().getCardIDs().remove(card.getID());
        account.getCollection().getAllCards().remove(card.getID());
        Message.sellWasSuccessful();
    }

    //***

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public static Shop getShop() {
        return shop;
    }
}
