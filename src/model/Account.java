package model;

import view.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Account {
    private static Account activeAccount;
    private static ArrayList<String> accountNames = new ArrayList<>();
    private static HashMap<String, Account> accountNameToAccountObject = new HashMap<>();
    //***
    private int money;
    private ArrayList<MatchHistory> history = new ArrayList<>();
    private Collection collection;
    private String username;
    private String password;

    //***
    public static void createAccount(String userName, String password) {
        if (accountNames.contains(userName)) {
            Message.thereIsAnAccountWithThisName();
            return;
        }
        Account account = new Account();
        account.username = userName;
        account.password = password;
        accountNames.add(userName);
        accountNameToAccountObject.put(userName, account);
    }

    public static void login(String userName, String password) {
        Account account = accountNameToAccountObject.get(userName);
        if (!account.password.equals(password)) {
            Message.invalidPassword();
            return;
        }
        Account.activeAccount = account;
    }

    private static void sortAccounts() {
        //todo
    }

    public static void save() {
        //todo?
    }

    //***
    public static Account getActiveAccount() {
        return activeAccount;
    }

    public static ArrayList<String> getAccountNames() {
        return accountNames;
    }

    public static HashMap<String, Account> getAccountNameToAccountObject() {
        return accountNameToAccountObject;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<MatchHistory> getHistory() {
        return history;
    }

    public Collection getCollection() {
        return collection;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
