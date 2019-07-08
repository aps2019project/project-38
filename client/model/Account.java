package model;//todo HASHEM, PLEASE do NOT push a code that does not compile.

import java.io.*;
import java.util.*;


public class Account implements Comparable<Account>, java.io.Serializable {

    private static Account activeAccount = null;
    private static HashMap<String, Account> usernameToAccountMap = new HashMap<>();
    //***
    public int derrick = 15000;
    private ArrayList<MatchHistory> history = new ArrayList<>();
    private Collection collection = new Collection();
    private String username;
    private String password;
    public int avatarNumber;

    public Account(String username, String password) {
        super();
        this.username = username;
        this.password = password;
        usernameToAccountMap.put(username, this);
        avatarNumber = new Random().nextInt(15);//todo shall be able to select
        collection.setMainDeck(Deck.getAllDecks().get("level1"));//todo for test game preview.
    }

    //***
    public static String createAccount(String username, String password, String againPassword) {
        if (Account.getUsernameToAccountMap().containsKey(username)) {
            return "There's an account with this name";
        }
        if (!password.equals(againPassword)) {
            return "Your passwords aren't same";
        }
        new Account(username, password);
        return "Account created successfully";
    }

    public static String login(String username, String password) {
        if (!Account.getUsernameToAccountMap().containsKey(username)) {
            return "There is no account with this name";
        }
        Account account = usernameToAccountMap.get(username);
        if (!account.password.equals(password)) {
            return "Your password is incorrect";
        }
        Account.activeAccount = account;
        return "You logged in successfully";
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

    public static ArrayList<Account> getSortedAccounts() {
        ArrayList<Account> allAccounts = new ArrayList<>();
        for (String username : getUsernameToAccountMap().keySet()) {
            allAccounts.add(getUsernameToAccountMap().get(username));
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
            oos.writeObject(usernameToAccountMap);
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
            usernameToAccountMap = (HashMap<String, Account>) ois.readObject();
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
    public static void setActiveAccount(Account activeAccount) {
        Account.activeAccount = activeAccount;
    }

    public static Account getActiveAccount() {
        return activeAccount;
    }

    public static HashMap<String, Account> getUsernameToAccountMap() {
        return usernameToAccountMap;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}