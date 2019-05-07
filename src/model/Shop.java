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
            if (card.getName().toLowerCase().equals(cardName.toLowerCase())) {

                Message.existACardWithThisNameInShop();
                Message.printSomeThing("It's ID is "+ID);
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
            if (card.getName().toLowerCase().equals(cardName.toLowerCase())) {
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
        Card card = getCardByItsName(Card.lowerNameToOriginalName.get(cardName.toLowerCase()));
        Account account = Account.getActiveAccount();

        if (card == null) {
            Message.thereIsNoCardWithThisNameInShop();
            return;
        }

        if (account.getDerrick() >= card.getPrice()) {
            if (Spell.checkIsItem(card)) {
                int numberOfItems = 0;
                for (int ID : account.getCollection().getCardIDs()) {
                    Card card1 = Card.getAllCards().get(ID);
                    if (Spell.checkIsItem(card1)) numberOfItems++;
                }
                if (numberOfItems >= 3) {
                    Message.haveAlready3Items();
                    return;
                }
            }
            account.setDerrick(account.getDerrick() - card.getPrice());
//            shop.getCardIDs().remove(card.getID());
            account.getCollection().getCardIDs().add(card.getID());

            if (Collection.getCollection().getHowManyCard().containsKey(cardName)) {
                int keyValue = Collection.getCollection().getHowManyCard().get(cardName);
                Collection.getCollection().getHowManyCard().put(card.getName(), keyValue + 1);
            } else {
                Collection.getCollection().getHowManyCard().put(card.getName(), 1);
            }

            Message.buyWasSuccessful();
        } else {
            Message.haveNotEnoughMoney();
        }
    }

    public void sell(String cardName) {
        Card card = getCardByItsName(Card.lowerNameToOriginalName.get(cardName.toLowerCase()));
        Account account = Account.getActiveAccount();

        if (card == null) {
            Message.thereIsNoCardWithThisNameInCollection();
            return;
        }

        if (!account.getCollection().getCardIDs().contains(card.getID())) {
            Message.haveNotThisCardInYourCollection();
            return;
        }
        account.setDerrick(account.getDerrick() + card.getPrice());
        shop.getCardIDs().add(card.getID());
        account.getCollection().getCardIDs().remove((Integer) card.getID());
        for (String deckName : account.getCollection().getDecks()) {
            Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
            if (deck.getCardIDs().contains(card.getID())) {
                deck.getCardIDs().remove(card.getID());
            }
        }
        account.getCollection().getCardIDs().remove((Integer) card.getID());

        int keyValue = Collection.getCollection().getHowManyCard().get(card.getName());
        Collection.getCollection().getHowManyCard().put(card.getName(), keyValue - 1);

        Message.sellWasSuccessful();
    }

    private Card getCardByItsName(String cardName) {
        Card card = null;
        for (int ID : shop.getCardIDs()) {
            if (Card.getAllCards().get(ID).getName().equals(cardName)) {
                card = Card.getAllCards().get(ID);
            }
        }
        return card;
    }

    //***

    public ArrayList<Integer> getCardIDs() {
        return cardIDs;
    }

    public static Shop getShop() {
        return shop;
    }
}
