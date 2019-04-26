package view;

public interface Message {
    // in shop
    static void thereIsNoCardWithThisNameInShop() {
        System.out.println("There is no card with this name in shop cards");
    }
    static void haveNotEnoughMoney() {
        System.out.println("You haven't enough money");
    }
    static void buyWasSuccessful(){
        System.out.println("You bought the cart successfully");
    }
    static void thereIsNoCardWithThisNameInCollection() {
        System.out.println("There is no card with this name in collection cards");
    }
    static void have3Items(){
        System.out.println("You have 3 items. You couldn't buy any other item");
    }
    static void haveNotThisCardInYourCollection(){
        System.out.println("You haven't this card in your collection. You can't sell it");
    }
    static void sellWasSuccessful(){
        System.out.println("You sell the card successfully");
    }
    // in account
    static void thereIsAnAccountWithThisName(){
        System.out.println("There is already an account with this name");
    }
    static void invalidPassword(){
        System.out.println("Your password is incorrect");
    }
    // in collection
    static void thereIsNoCardWithThisIDInCollection() {
        System.out.println("There is no card with this ID in collection cards");
    }
    static void thereIsADeckWhitThisName(){
        System.out.println("There is already a deck with this name");
    }
    static void thereIsNoDeckWithThisName(){
        System.out.println("There is no deck with this name");
    }
    static void thereIsACardWithThisIDInThisDeck(){
        System.out.println("There is already a card whit this ID in this deck");
    }
    static void have20CardsInThisDeck(){
        System.out.println("You have 20 cards in your deck. You couldn't put any other card");
    }
    static void thereIsAHeroInThisDeck(){
        System.out.println("there is already a hero in this deck. You can't add any other");
    }
    static void thereIsNoCardWithThisIDInThisDeck(){
        System.out.println("There is no card with this ID in this deck");
    }
    static void deckIsNotValid(){
        System.out.println("This deck is not valid");
    }
}

