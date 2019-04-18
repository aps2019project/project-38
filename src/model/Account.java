package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Account {
    public static Account activeAccount;
    public static ArrayList<String> accountNames = new ArrayList<>();
    public static HashMap<String, Account> accountNameToAccountObject = new HashMap<>();
    //***
    private int money;
    private ArrayList<MatchHistory> history = new ArrayList<>();
    Collection collection;
    String username;
    String password;

    //***
    public static void createAccount(Account account, String userName, String password) {

    }

    private Account deepCopy(Account account) {
        return null;
    }

    private void save() {

    }

    //************************

    public void setMoney(int money) {
        this.money = money;
    }

    public static Account getActiveAccount() {
        return activeAccount;
    }

    public static ArrayList<String> getAccountNames() {
        return accountNames;
    }

    public static HashMap<String, Account> getAccountNameToAccountObject() {
        return accountNameToAccountObject;
    }

    public int getMoney() {
        return money;
    }
}
