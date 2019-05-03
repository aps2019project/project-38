package controller.window;

import model.Collection;
import model.Deck;
import view.Message;

import static view.Request.getNextRequest;

public class CollectionWindow extends Window {

    public void showCollectionMenu() {
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
                    String cardName = getNextRequest();
                    for (int ID : Collection.getCollection().searchInCollectionCards(cardName)) {
                        System.out.print(ID + " ");
                    }
                    continue;
                case 3:
                    String deckName = getNextRequest();
                    Collection.getCollection().createDeck(deckName);
                    continue;
                case 4:
                    deckName = getNextRequest();
                    Collection.getCollection().deleteDeck(deckName);
                    continue;
                case 5:
                    int cardID = Integer.parseInt(getNextRequest());
                    deckName = getNextRequest();
                    Collection.getCollection().addCardToDeck(cardID, deckName);
                    continue;
                case 6:
                    cardID = Integer.parseInt(getNextRequest());
                    deckName = getNextRequest();
                    Collection.getCollection().removeCardFromDeck(cardID, deckName);
                    continue;
                case 7:
                    deckName = getNextRequest();
                    Collection.getCollection().validateDeck(deckName);
                    continue;
                case 8:
                    deckName = getNextRequest();
                    Collection.getCollection().selectMainDeck(deckName);
                    continue;
                case 9:
                    deckName = getNextRequest();
                    Deck deck = Deck.getAllDecks().get(deckName);
                    showInfoOfASpecificDeck(deck);
                    continue;
                case 10:
                    showInfoOfAllDecks();
                    continue;
                case 11:
                    Collection.getCollection().save();
            }
        }
    }

    private void showInfoOfASpecificDeck(Deck deck) {
        ShopWindow.handleShowInfoOfCards(deck.getCardIDs(), 3);
    }

    private void showInfoOfAllDecks() {
        Deck deck = Collection.getCollection().getMainDeck();
        Message.showAWordAsTitle("Deck 1");
        showInfoOfASpecificDeck(deck);
        int i = 2;
        for (Deck deck1 : Collection.getCollection().getDecks()) {
            if (deck1.equals(deck)) continue;
            Message.showAWordAsTitle("Deck " + i);
            showInfoOfASpecificDeck(deck1);
            i++;
        }
    }
}
