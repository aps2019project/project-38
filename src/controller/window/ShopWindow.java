package controller.window;

import model.Shop;
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
                    Message.showInfoOfAllCardsOfShop();
                    continue;
                case 2:
                    Message.showInfoOfAllCardsOfCollection();
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
}