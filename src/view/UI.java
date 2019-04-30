package view;

import model.Account;
import model.Collection;
import model.MatchHistory;
import model.Shop;

import java.util.ArrayList;

public class UI {
    public static UI ui = new UI();

    //account methods:
    public void accountMenu() {
        showAccountHelp();
        while (true) {
            String input = getNextRequest();
            if (!input.matches("\\d+")) {
                Message.invalidInput();
                continue;
            }
            int indexOfSelectedSubMenu = Integer.parseInt(input);
            switch (indexOfSelectedSubMenu) {
                case 1:
                    String accountName = getNextRequest();
                    String password = getNextRequest();
                    Account.createAccount(accountName, password);
                    continue;
                case 2:
                    accountName = getNextRequest();
                    password = getNextRequest();
                    Account.login(accountName, password);
                    continue;
                case 3:
                    showLeaderBoard();
                    continue;
                case 0:
                    Account.getActiveAccount().save();
            }
        }
    }

    private void showAccountHelp() {
        System.out.println("Account Menu:");
        System.out.println("1- Create Account");
        System.out.println("2- Login");
        System.out.println("3- Show LeaderBoard");
        System.out.println("0- Exit");
    }

    private void showLeaderBoard() {
        ArrayList<Account> allAccounts = Account.sortAccounts();
        int i=0;
        for(Account account:allAccounts){
            int numberOfWins = 0;
            for (MatchHistory matchHistory:account.getHistory()){
                if(matchHistory.getDidWin())numberOfWins++;
            }
            System.out.printf("%d- UserName : %s - Wins : %d\n",i,account.getUsername(),numberOfWins);
            i++;
        }
    }

    //shop methods
    public void showShopMenu() {
        while (true) {
            String input = getNextRequest();
            String[] inputParts = input.split("\\w+");
            if (input.equals("exit")) {
                break;
            }
            if (inputParts[0].equals("show") && inputParts[1].equals("collection")) {
                showInfoOfAllCardsOfCollection();
            }
            if (inputParts[0].equals("search")) {
                int ID = Shop.getShop().searchInShopCards(inputParts[1]);
                if (ID != -1) {
                    System.out.println(ID);
                }
            }
            if (inputParts[0].equals("search") && inputParts[1].equals("collection")) {
                ArrayList<Integer> foundIDs = Shop.getShop().searchInCollectionCards(inputParts[2]);
                if (foundIDs.size() > 0) {
                    for (int ID : foundIDs) {
                        System.out.print(ID + " ");
                    }
                    System.out.println();
                }
            }
            if (inputParts[0].equals("buy")) {
                Shop.getShop().buy(inputParts[1]);
            }
            if (inputParts[0].equals("sell")) {
                Shop.getShop().sell(Integer.parseInt(inputParts[1]));
            }
            if (input.equals("show")) {
                showInfoOfAllCardsOfShop();
            }
            if (input.equals("help")) {
                showShopHelp();
            }
        }
    }

    private void showShopHelp() {
        //todo
    }

    private void showInfoOfAllCardsOfCollection() {
        //todo
    }

    private void showInfoOfAllCardsOfShop() {
        //todo
    }

    //collection method
    public void showCollectionMenu() {
        while (true) {
            String input = getNextRequest();
            String[] inputParts = input.split("\\w+");
            if (input.equals("exit")) {
                break;
            }
            if (input.equals("show")) {
                __showInfoOfAllCardsOfCollection();
            }
            if (inputParts[0].equals("search")) {
                for (int ID : Collection.getCollection().searchInCollectionCards(inputParts[1])) {
                    System.out.print(ID + " ");
                }
                System.out.println();
            }
            if (input.equals("save")) {
                Collection.getCollection().save();
            }
            if (inputParts[0].equals("create") && inputParts[1].equals("deck")) {
                Collection.getCollection().createDeck(inputParts[3]);
            }
            if (inputParts[0].equals("delete") && inputParts[1].equals("deck")) {
                Collection.getCollection().deleteDeck(inputParts[3]);
            }
            if (inputParts[0].equals("add") && inputParts[2].equals("to") && inputParts[3].equals("deck")) {
                Collection.getCollection().addCardToDeck(Integer.parseInt(inputParts[1]), inputParts[4]);
            }
            if (inputParts[0].equals("remove") && inputParts[2].equals("from") && inputParts[3].equals("deck")) {
                Collection.getCollection().removeCardFromDeck(Integer.parseInt(inputParts[1]), inputParts[4]);
            }
            if (inputParts[0].equals("validate") && inputParts[1].equals("deck")) {
                Collection.getCollection().validateDeck(inputParts[2]);
            }
            if (inputParts[0].equals("select") && inputParts[1].equals("deck")) {
                Collection.getCollection().selectMainDeck(inputParts[2]);
            }
            if (input.equals("show all decks")) {
                showInfoOfAllDecks();
            }
            if (inputParts[0].equals("show") && inputParts[1].equals("deck")) {
                showInfoOfSpecificDeck(inputParts[2]);
            }
            if (input.equals("help")) {
                showCollectionHelp();
            }
        }
    }

    private void __showInfoOfAllCardsOfCollection() {
        //todo
    }

    private void showInfoOfAllDecks() {
        //todo
    }

    private void showInfoOfSpecificDeck(String deckName) {
        //todo
    }

    private void showCollectionHelp() {
        //todo
    }

    //***
    public String getNextRequest() {
        //todo
        return null;
    }

    public static UI getUi() {
        return ui;
    }
}
