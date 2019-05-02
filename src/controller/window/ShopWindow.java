package controller.window;

import com.sun.xml.internal.ws.policy.jaxws.SafePolicyReader;
import model.Shop;
import model.cards.Card;
import model.cards.heros.Hero;
import model.cards.spells.Spell;
import view.Message;

import java.util.ArrayList;

import static view.Request.getNextRequest;

public class ShopWindow extends Window {

    public void shopMenu() {
        while (true) {
            Message.showShopHelp();
            String input = getNextRequest();
            if (!input.matches("\\d+")) {
                Message.invalidInput();
                continue;
            }
            int indexOfSelectedSubMenu = Integer.parseInt(input);
            switch (indexOfSelectedSubMenu) {
                case 1:
                    handleShowInfoOfCardsOfShop();
                    continue;
                case 2:
                    handleShowInfoOfCardsOfCollection();
                    continue;
                case 3:
                    Message.InterCardName();
                    String cardName = getNextRequest();
                    int ID = Shop.getShop().searchInShopCards(cardName);
                    if (ID != -1) {
                        Message.printCardID(ID);
                    }
                    continue;
                case 4:
                    Message.InterCardName();
                    cardName = getNextRequest();
                    ArrayList<Integer> foundIDs = Shop.getShop().searchInCollectionCards(cardName);
                    if (foundIDs.size() > 0) {
                        for (int id : foundIDs) {
                            Message.printCardID(id);
                        }
                        System.out.println();
                    }
                    continue;
                case 5:
                    Message.InterCardName();
                    cardName = getNextRequest();
                    Shop.getShop().buy(cardName);
                    continue;
                case 6:
                    Message.InterCardName();
                    cardName = getNextRequest();
                    Shop.getShop().sell(Integer.parseInt(cardName));
            }
        }
    }

    private void handleShowInfoOfCardsOfShop() {
        Shop shop = Shop.getShop();
        int numberOfHeroes = 0;
        int numberOfItems = 0;
        int numberOfCards = 0;
        for (int cardID : shop.getCardIDs()) {
            Card card = Card.getAllCards().get(cardID);
            if (card instanceof Hero) {
                numberOfHeroes++;
                Message.showInfoOfHeroInShop((Hero) card, numberOfHeroes);
            } else if (Spell.checkIsItem(card)) {
                numberOfItems++;
                Message.showInfoOfItemInShop((Spell) card, numberOfItems);
            } else {
                numberOfCards++;
                String type;
                if (card instanceof Spell) type = "Spell";
                else type = "Minion";
                Message.showInfoOfCardInShop(card, numberOfCards, type);
            }
        }
    }
    private void handleShowInfoOfCardsOfCollection(){

    }
}