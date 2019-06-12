package model;

import view.Message;

import java.io.*;
import java.util.*;
import java.util.Collections;


public class Account implements Comparable<Account>, java.io.Serializable {

    private static Account activeAccount = null;
    private static HashMap<String, Account> usernameToAccountObject = new HashMap<>();
    //***
    public int derrick = 15000; //todo you can remove getter and setter and make it public
    private ArrayList<MatchHistory> history = new ArrayList<>();
    private Collection collection = new Collection();
    private String username;
    private String password;


    public Account(String username, String password) {
        super();
        this.username = username;
        this.password = password;
        usernameToAccountObject.put(username, this);
    }

    //***
    public static String createAccount(String username, String password, String againPassword) {
        if (Account.getUsernameToAccountObject().containsKey(username)) {
            return "There's an account with this name";
        }
        if (!password.equals(againPassword)) {
            Message.noIdenticalPassword();
            return "Your passwords aren't same";
        }
        Account account = new Account(username, password);
        return "Account created successfully";
    }

    public static boolean login(String username, String password) {
        if (!Account.getUsernameToAccountObject().containsKey(username)) {
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
        for (String username : getUsernameToAccountObject().keySet()) {
            allAccounts.add(getUsernameToAccountObject().get(username));
        }
        Collections.sort(allAccounts);
        Collections.reverse(allAccounts);
        return allAccounts;
    }

    //***
    public static void saveAccounts() {
        try {
            File file = new File(System.getProperty("user.home") + "/Selistdar");
            file.mkdirs();
            FileOutputStream fos = new FileOutputStream(file.getPath() + "/acc");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(usernameToAccountObject);
            fos.close();
            oos.close();
        } catch (IOException e) {
            System.err.println("could save accounts");
            e.printStackTrace();
        }
    }

    public static void loadAccounts() {
        try {
            File file = new File(System.getProperty("user.home") + "/Selistdar");
            file.mkdirs();
            FileInputStream fis = new FileInputStream(file.getPath() + "/acc");
            ObjectInputStream ois = new ObjectInputStream(fis);
            usernameToAccountObject = (HashMap<String, Account>) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception e) {
            System.err.println("couldn't read accounts.");
        }
    }

    public void putGameInHistory(String opponentName, boolean didWin) {
        MatchHistory matchHistory = new MatchHistory(opponentName, didWin, new Date());
        history.add(matchHistory);
    }

    //***
    public static Account getActiveAccount() {
        return activeAccount;
    }

    public static HashMap<String, Account> getUsernameToAccountObject() {
        return usernameToAccountObject;
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
