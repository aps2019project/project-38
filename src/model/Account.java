package model;

import view.Message;

import java.util.*;
import java.util.Collections;


public class Account implements Comparable<Account>, java.io.Serializable {

    private static Account activeAccount = new Account();
    private static ArrayList<String> usernames = new ArrayList<>();
    private static HashMap<String, Account> usernameToAccountObject = new HashMap<>();
    //***
    private int money;
    private ArrayList<MatchHistory> history = new ArrayList<>();
    private Collection collection = new Collection();
    private String username;
    private String password;

    //***
    public static void createAccount(String username, String password, String againPassword) {
        if (Account.getusernames().contains(username)) {
            Message.thereIsAnAccountWithThisName();
            return;
        }
        if (!password.equals(againPassword)) {
            Message.noIdenticalPassword();
            return;
        }
        Message.accountCreatedSuccessfully();
        Account account = new Account();
        account.username = username;
        account.password = password;
        usernames.add(username);
        usernameToAccountObject.put(username, account);
    }

    public static boolean login(String username, String password) {
        if (!Account.getusernames().contains(username)) {
            Message.thereIsNoAccountWithThisName();
            return false;
        }
        Account account = usernameToAccountObject.get(username);
        if (!account.password.equals(password)) {
            Message.incorrectPassword();
            return false;
        }
        Account.activeAccount = account;
        return true;
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
        for (String username : getusernames()) {
            allAccounts.add(getusernameToAccountObject().get(username));
        }
        Collections.sort(allAccounts);
        return allAccounts;
    }

    //***
    public void save() {

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

    public static ArrayList<String> getusernames() {
        return usernames;
    }

    public static HashMap<String, Account> getusernameToAccountObject() {
        return usernameToAccountObject;
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
