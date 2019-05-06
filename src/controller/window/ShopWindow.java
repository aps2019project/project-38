package controller.window;

import model.Collection;
import model.Shop;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;
import model.effects.Effect;
import model.effects.Melee;
import model.effects.Ranged;
import view.Message;

import java.util.ArrayList;

import static view.Request.getNextRequest;

public class ShopWindow extends Window {

    public void main() {
        tag1:
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
                    handleShowInfoOfCards(Shop.getShop().getCardIDs(), 1);
                    continue;
                case 2:
                    handleShowInfoOfCards(Collection.getCollection().getCardIDs(), 2);
                    continue;
                case 3:
                    Message.InterCardName();
                    String cardName = getNextRequest();
                    Shop.getShop().searchInShopCards(cardName);
                    continue;
                case 4:
                    Message.InterCardName();
                    cardName = getNextRequest();
                    Shop.getShop().searchInCollectionCards(cardName);
                    continue;
                case 5:
                    Message.InterCardName();
                    cardName = getNextRequest();
                    Shop.getShop().buy(cardName);
                    continue;
                case 6:
                    Message.interCardName();
                    cardName = getNextRequest();
                    Shop.getShop().sell(cardName);
                    continue;
                case 0:
                    Window.closeWindow(this);
                    break tag1;
                default:
                    Message.invalidInput();
            }
        }
    }

    //kind==1 price for sell is shown //kind==2 price for buy is shown //kind==3 price isn't shown
    public static void handleShowInfoOfCards(ArrayList<Integer> repository, int kind) {
        if (repository.size() == 0) {
            Message.noCardInThisRepository();
            return;
        }
        ArrayList<Card> heroes = new ArrayList<>();
        ArrayList<Card> items = new ArrayList<>();
        ArrayList<Card> otherCards = new ArrayList<>();
        for (int cardID : repository) {
            Card card = Card.getAllCards().get(cardID);
            if (card instanceof Hero) {
                heroes.add(card);
            } else if (Spell.checkIsItem(card)) {
                items.add(card);
            } else {
                otherCards.add(card);
            }
        }
        showInfoOfHeroes(heroes, kind);
        showInfoOfItems(items, kind);
        showInfoOfOtherCards(otherCards, kind);
    }

    private static void showInfoOfHeroes(ArrayList<Card> heroes, int kind) {
        Message.showAWordAsTitle("_-Hero-_");
        for (int i = 0; i < heroes.size(); i++) {
            int template = 0;
            String kindOfAttackArea = "Hybrid"; // byDefault
            for (Effect effect : heroes.get(i).getEffects()) {
                if (effect instanceof Melee) {
                    template += 1;
                }
                if (effect instanceof Ranged) {
                    template += 2;
                }
            }
            if (template == 1) kindOfAttackArea = "Melee";
            if (template == 2) kindOfAttackArea = "Ranged";

            if (kind == 1) {
                Message.showInfoOfHeroPlusPrice((Hero) heroes.get(i), i + 1, kindOfAttackArea, "Sell");
            }
            if (kind == 2) {
                Message.showInfoOfHeroPlusPrice((Hero) heroes.get(i), i + 1, kindOfAttackArea, "Buy");
            }
            if (kind == 3) {
                Message.showInfoOfHeroMinusPrice((Hero) heroes.get(i), i + 1, kindOfAttackArea);
            }
        }
    }

    private static void showInfoOfItems(ArrayList<Card> items, int kind) {
        Message.showAWordAsTitle("_-Item-_");
        for (int i = 0; i < items.size(); i++) {
            if (kind == 1) {
                Message.showInfoOfItemPlusPrice((Spell) items.get(i), i + 1, "Sell");
            }
            if (kind == 2) {
                Message.showInfoOfItemPlusPrice((Spell) items.get(i), i + 1, "Buy");
            }
            if (kind == 3) {
                Message.showInfoOfItemMinusPrice((Spell) items.get(i), i + 1);
            }
        }
    }

    private static void showInfoOfOtherCards(ArrayList<Card> others, int kind) {
        Message.showAWordAsTitle("_-Cards-_");
        for (int i = 0; i < others.size(); i++) {
            String type;
            if (others.get(i) instanceof Spell) type = "Spell";
            else type = "Minion";
            if (kind == 1) {
                Message.showInfoOfCardPlusPrice(others.get(i), i + 1, type, "Sell");
            }
            if (kind == 2) {
                Message.showInfoOfCardPlusPrice(others.get(i), i + 1, type, "Buy");
            }
            if (kind == 3) {
                Message.showInfoOfCardMinusPrice(others.get(i), i + 1, type);
            }
        }
    }
}