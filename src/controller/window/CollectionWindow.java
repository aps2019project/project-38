package controller.window;

import model.Collection;
import model.Deck;
import model.Shop;
import view.Message;

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
                    ShopWindow.handleShowInfoOfCards(Collection.getCollection().getCardIDs(), 2);
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
                    Message.interCardID();
                    int cardID = Integer.parseInt(getNextRequest());
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().addCardToDeck(cardID, deckName);
                    continue;
                case 6:
                    Message.interCardID();
                    cardID = Integer.parseInt(getNextRequest());
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().removeCardFromDeck(cardID, deckName);
                    continue;
                case 7:
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().validateDeck(deckName,true);
                    continue;
                case 8:
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Collection.getCollection().selectMainDeck(deckName);
                    continue;
                case 9:
                    Message.interDeckName();
                    deckName = getNextRequest();
                    Deck deck = Deck.getAllDecks().get(deckName);
                    showInfoOfASpecificDeck(deck);
                    continue;
                case 10:
                    showInfoOfAllDecks();
                    continue;
                case 11:
//                    Collection.getCollection().save();
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
        ShopWindow.handleShowInfoOfCards(deck.getCardIDs(), 3);
    }

    private void showInfoOfAllDecks() {
        Deck deck = Collection.getCollection().getMainDeck();
        int i = 1;
        if(deck!=null) {
            Message.showDeckName(i,deck.getName());
            showInfoOfASpecificDeck(deck);
            i=2;
        }
        for (String deckName : Collection.getCollection().getDecks()) {
            Deck deck1 = Deck.getAllDecks().get(deckName);
            if (deck1.equals(deck)) continue;
            Message.showDeckName(i,deck1.getName());
            showInfoOfASpecificDeck(deck1);
            i++;
        }
        if(i==1){
            Message.noDeckExist();
        }
    }
}
