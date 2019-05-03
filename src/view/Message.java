package view;

import model.cards.Card;
import model.cards.heros.Hero;
import model.cards.spells.Spell;

public interface Message {
    // in menu
    static void invalidInput() {
        System.out.println("Invalid input");
    }

    // in shop
    static void showShopHelp() {
        System.out.println("Shop Menu:");
        System.out.println("1- Show Cards In Shop");
        System.out.println("2- Show Cards In Collection");
        System.out.println("3- Search In Shop");
        System.out.println("4- Search In Collection");
        System.out.println("5- Buy");
        System.out.println("6- Sell");
        System.out.println("0- Exit");
    }

    static void showInfoOfHeroInShop(Hero hero, int numberOfHeroes) {
        System.out.printf("%d ) Name : %s - AP : %d - HP : %d - SpecialPower : %s - BuyCost : %d",
                numberOfHeroes, hero.getName(), hero.getAp(), hero.getHp(), hero.getDescriptionOfSpecialPower(), hero.getPrice());
    }

    static void showInfoOfItemInShop(Spell spell, int numberOfItems) {
        System.out.printf("%d ) Name : %s - Description : %s - BuyCost : %d", numberOfItems, spell.getName(), spell.getDescriptionOfSpecialPower(), spell.getPrice());
    }

    static void showInfoOfCardInShop(Card card, int numberOfCards, String type) {
        System.out.printf("%d ) Type : %s - Name : %s - MP : %d - Description : %s - SellCost : %d",
                numberOfCards, type, card.getName(), card.getRequiredMana(), card.getDescriptionOfSpecialPower(), card.getPrice());
    }

    static void showInfoOfAllCardsOfCollection() {
        //todo
    }

    static void InterCardName() {
        System.out.println("Please Inter CardName");
    }

    static void printCardID(int cardID) {
        System.out.printf("%d\n", cardID);
    }

    static void thereIsNoCardWithThisNameInShop() {
        System.out.println("There is no card with this name in shop cards");
    }

    static void haveNotEnoughMoney() {
        System.out.println("You haven't enough money");
    }

    static void buyWasSuccessful() {
        System.out.println("You bought the cart successfully");
    }

    static void thereIsNoCardWithThisNameInCollection() {
        System.out.println("There is no card with this name in collection cards");
    }

    static void have3Items() {
        System.out.println("You have 3 items. You couldn't buy any other item");
    }

    static void haveNotThisCardInYourCollection() {
        System.out.println("You haven't this card in your collection. You can't sell it");
    }

    static void sellWasSuccessful() {
        System.out.println("You sell the card successfully");
    }

    // in account:
    static void showAccountHelp() {
        System.out.println("Account Menu:");
        System.out.println("1- Create Account");
        System.out.println("2- Login");
        System.out.println("3- Show LeaderBoard");
        System.out.println("0- Exit");
    }

    static void showLeaderBoard(int i, String username, int numberOfWins) {
        System.out.printf("%d- UserName : %s - Wins : %d\n", i, username, numberOfWins);
    }

    static void interUsername() {
        System.out.println("Please inter Username");
    }

    static void interPassword() {
        System.out.println("Please inter Password");
    }

    static void thereIsAnAccountWithThisName() {
        System.out.println("There is already an account with this name");
    }

    static void invalidPassword() {
        System.out.println("Your password is incorrect");
    }

    // in collection
    static void thereIsNoCardWithThisIDInCollection() {
        System.out.println("There is no card with this ID in collection cards");
    }

    static void thereIsADeckWhitThisName() {
        System.out.println("There is already a deck with this name");
    }

    static void thereIsNoDeckWithThisName() {
        System.out.println("There is no deck with this name");
    }

    static void thereIsACardWithThisIDInThisDeck() {
        System.out.println("There is already a card whit this ID in this deck");
    }

    static void have20CardsInThisDeck() {
        System.out.println("You have 20 cards in your deck. You couldn't put any other card");
    }

    static void thereIsAHeroInThisDeck() {
        System.out.println("there is already a hero in this deck. You can't add any other");
    }

    static void thereIsNoCardWithThisIDInThisDeck() {
        System.out.println("There is no card with this ID in this deck");
    }

    static void deckIsNotValid() {
        System.out.println("This deck is not valid");
    }
}