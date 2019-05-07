package controller.window;

import model.Account;
import model.Collection;
import model.Deck;
import model.Shop;
import model.cards.Card;
import view.Message;

import java.util.ArrayList;

import static view.Request.getNextRequest;

public class CollectionWindow extends Window {

    @Override
    public void main() {
        tag1:
        while (true) {
            Message.showCollectionHelp();
            String input = getNextRequest();
            if (!input.matches("\\d+")) {
                Message.invalidInput();
                continue;
            }
            int indexOfSelectedSubMenu = Integer.parseInt(input);
            switch (indexOfSelectedSubMenu) {
                case 1:
                    ShopWindow.handleShowInfoOfCards(Collection.getCollection().getCardIDs(), 1);
                    continue;
                case 2:
                    Message.interCardName();
                    String cardName = getNextRequest();
                    Shop.getShop().searchInCollectionCards(cardName);
                    continue;
                case 3:
                    Message.interDeckName();
                    String deckName = getNextRequest();
                    Collection.getCollection().createDeck(deckName);
                    continue;
                case 4:
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().deleteDeck(deckName);
                    continue;
                case 5:
                    Message.interCardName();
                    cardName = getNextRequest();
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().addCardToDeck(cardName, deckName);
                    continue;
                case 6:
                    Message.interCardName();
                    cardName = getNextRequest();
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().removeCardFromDeck(cardName, deckName);
                    continue;
                case 7:
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().validateDeck(deckName, true);
                    continue;
                case 8:
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().selectMainDeck(deckName);
                    continue;
                case 9:
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
                    showInfoOfASpecificDeck(deck);
                    continue;
                case 10:
                    showInfoOfAllDecks();
                    continue;
                case 11:
//                    Collection.getCollection().saveAccounts();
                    continue;
                case 0:
                    Window.closeWindow(this);
                    break tag1;
                default:
                    Message.invalidInput();
            }
        }
    }

    private void showInfoOfASpecificDeck(Deck deck) {
        if (deck == null) {
            Message.thereIsNoDeckWithThisName();
            return;
        }
        ArrayList<Card> oneHero = new ArrayList<>();
        if (deck.getHero() != null) oneHero.add(deck.getHero());
        ShopWindow.showInfoOfHeroes(oneHero, 3);
        ArrayList<Card> oneItem = new ArrayList<>();
        if (deck.getItem() != null) oneItem.add(deck.getItem());
        ShopWindow.showInfoOfItems(oneItem, 3);
        ArrayList<Card> otherCards = new ArrayList<>();
        for (int ID : deck.getCardIDs()) {
            otherCards.add(Card.getAllCards().get(ID));
        }
        ShopWindow.showInfoOfOtherCards(otherCards, 3);
    }

    private void showInfoOfAllDecks() {
        Deck deck = Collection.getCollection().getMainDeck();
        int i = 1;
        if (deck != null) {
            Message.showDeckName(i, deck.getName());
            showInfoOfASpecificDeck(deck);
            i = 2;
        }
        for (String deckName : Collection.getCollection().getDecks()) {
            Deck deck1 = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
            if (deck1.equals(deck)) continue;
            Message.showDeckName(i, deck1.getName());
            showInfoOfASpecificDeck(deck1);
            i++;
        }
        if (i == 1) {
            Message.noDeckExist();
        }
    }
}
