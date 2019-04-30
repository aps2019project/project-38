package model;

import view.Message;

import java.util.*;
import java.util.Collections;


public class Account implements Comparable<Account> {

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

    @Override
    public int compareTo(Account o) {
        int numberOfMyWins = 0;
        for (MatchHistory matchHistory : this.getHistory()) {
            if (matchHistory.getDidWin()) numberOfMyWins++;
        }
        int numberOFOWins = 0;
        for (MatchHistory matchHistory : o.getHistory()) {
            if (matchHistory.getDidWin()) numberOFOWins++;
        }
        return Integer.compare(numberOfMyWins, numberOFOWins);
    }

    public static ArrayList<Account> sortAccounts() {
        ArrayList<Account> allAccounts = new ArrayList<>();
        for (String accountName : getAccountNames()) {
            allAccounts.add(getAccountNameToAccountObject().get(accountName));
        }
        Collections.sort(allAccounts);
        return allAccounts;
    }

    //***
    public void save() {
        //todo
    }

    public void putGameInHistory(String opponentName, boolean didWin) {
        MatchHistory matchHistory = new MatchHistory();
        matchHistory.setDidWin(didWin);
        matchHistory.setOpponentName(opponentName);
        Date date = new Date();
        matchHistory.setDate(date);
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
