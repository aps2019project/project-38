package view;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Spell;

public interface Message {
    // general messages
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

    static void showInfoOfHeroPlusPrice(Hero hero, int numberOfHeroes, String kindOfAttackArea, String buyOrSell) {
        System.out.printf("%d ) Name : %s - AP : %d - HP : %d - class : %s - SpecialPower : %s - %sCost : %d\n",
                numberOfHeroes, hero.getName(), hero.getAp(), hero.getHp(), kindOfAttackArea, hero.getDescriptionOfSpecialPower(), buyOrSell, hero.getPrice());
    }

    static void showInfoOfHeroMinesPrice(Hero hero, int numberOfHeroes, String kindOfAttackArea) {
        System.out.printf("%d ) Name : %s - AP : %d - HP : %d - class : %s - SpecialPower : %s\n",
                numberOfHeroes, hero.getName(), hero.getAp(), hero.getHp(), kindOfAttackArea, hero.getDescriptionOfSpecialPower());
    }

    static void showInfoOfItemPlusPrice(Spell spell, int numberOfItems, String buyOrSell) {
        System.out.printf("%d ) Name : %s - Description : %s - %sCost : %d\n",
                numberOfItems, spell.getName(), spell.getDescriptionOfSpecialPower(), buyOrSell, spell.getPrice());
    }

    static void showInfoOfItemMinesPrice(Spell spell, int numberOfItems) {
        System.out.printf("%d ) Name : %s - Description : %s\n",
                numberOfItems, spell.getName(), spell.getDescriptionOfSpecialPower());
    }

    static void showInfoOfCardPlusPrice(Card card, int numberOfCards, String typeOfCard, String buyOrSell) {
        System.out.printf("%d ) Type : %s - Name : %s - MP : %d - Description : %s - %sCost : %d\n",
                numberOfCards, typeOfCard, card.getName(), card.getRequiredMana(), card.getDescriptionOfSpecialPower(), buyOrSell, card.getPrice());
    }

    static void showInfoOfCardMinesPrice(Card card, int numberOfCards, String typeOfCard) {
        System.out.printf("%d ) Type : %s - Name : %s - MP : %d - Description : %s\n",
                numberOfCards, typeOfCard, card.getName(), card.getRequiredMana(), card.getDescriptionOfSpecialPower());
    }

    static void showAWordAsTitle(String aWord) {
        System.out.println("        " + aWord + " :");
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
    static void showCollectionHelp() {
        System.out.println("Collection Menu:");
        System.out.println("1- Show Info Of Cards Of Collection:");
        System.out.println("2- Search In Collection");
        System.out.println("3- Create Deck");
        System.out.println("4- Delete Deck");
        System.out.println("5- Add Card To Deck");
        System.out.println("6- Remove Card From Deck");
        System.out.println("7- Check Validation Of A Deck");
        System.out.println("8- Select A Deck As Main");
        System.out.println("9- Show Info Of A Specific Deck");
        System.out.println("10- Show Info Of All Decks");
        System.out.println("11- Save :||");
        System.out.println("0- Exit");
    }

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