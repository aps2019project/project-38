package model;

import model.cards.Card;
import model.cards.Spell;
import view.Message;

import java.util.ArrayList;

public class Shop {
    private static Shop shop = new Shop();
    private ArrayList<Integer> cardIDs = new ArrayList<>();

    private Shop() {
    }

    //***

    public void searchInShopCards(String cardName) {
        for (int ID : shop.getCardIDs()) {
            Card card = Card.getAllCards().get(ID);
            if (card.getName().equals(cardName)) {
                Message.existACardWithThisIDInShop();
                return;
            }
        }
        Message.thereIsNoCardWithThisNameInShop();
    }

    public void searchInCollectionCards(String cardName) {
        Account account = Account.getActiveAccount();
        Collection collection = account.getCollection();
        int numberOfFoundIDs = 0;
        for (int ID : collection.getCardIDs()) {
            Card card = Card.getAllCards().get(ID);
            if (card.getName().equals(cardName)) {
                numberOfFoundIDs++;
            }
        }
        if (numberOfFoundIDs == 0) {
            Message.thereIsNoCardWithThisNameInCollection();
        } else {
            Message.haveXNumberOfCardIDInYourCollection(numberOfFoundIDs);
        }
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
            if (Spell.checkIsItem(card)) {
                int numberOfItems = 0;
                for (int ID : account.getCollection().getCardIDs()) {
                    Card card1 = Card.getAllCards().get(ID);
                    if (Spell.checkIsItem(card1)) numberOfItems++;
                }
                if (numberOfItems >= 3) {
                    Message.have3Items();
                    return;
                }
            }
            account.setMoney(account.getMoney() - card.getPrice());
            shop.getCardIDs().remove(card.getID());
            account.getCollection().getCardIDs().add(card.getID());
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
        for (Deck deck : account.getCollection().getDecks()) {
            if (deck.getCardIDs().contains(card.getID())) {
                deck.getCardIDs().remove(card.getID());
            }
        }
//        account.getCollection().getAllCards().remove(card.getID());
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
