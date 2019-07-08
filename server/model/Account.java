package model;

import com.google.gson.Gson;
import server.DBMethods;

import java.util.*;


public class Account implements Comparable<Account>, java.io.Serializable {

    private static HashMap<String, Account> usernameToAccount = new HashMap<>();
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
        usernameToAccount.put(username, this);
        avatarNumber = new Random().nextInt(15);
        collection.setMainDeck(Deck.getAllDecks().get("level1"));//todo for test game preview.
    }

    //***
    public static String createAccount(String username, String password, String againPassword) {
        if (Account.getUsernameToAccount().containsKey(username)) {
            return "There's an account with this name";
        }
        if (!password.equals(againPassword)) {
            return "Your passwords aren't same";
        }
        new Account(username, password);
        return "Account created successfully";
    }

    public static String login(String username, String password) {
        if (!Account.getUsernameToAccount().containsKey(username)) {
            return "There is no account with this name";
        }
        Account account = usernameToAccount.get(username);
        if (!account.password.equals(password)) {
            return "Your password is incorrect";
        }
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
        for (String username : getUsernameToAccount().keySet()) {
            allAccounts.add(getUsernameToAccount().get(username));
        }
        Collections.sort(allAccounts);
        Collections.reverse(allAccounts);
        return allAccounts;
    }

    //***

    public static void saveAccounts() {
        Gson gson = new Gson();
        String accounts = gson.toJson(usernameToAccount);
        System.out.println(accounts);
        DBMethods.put("accounts", "accounts", accounts);
    }

    public static void loadAccounts() {
        Gson gson = new Gson();
        String accounts = DBMethods.get("accounts", "accounts");
        System.out.println(accounts);
        Account.usernameToAccount = (HashMap<String, Account>) gson.fromJson(accounts, HashMap.class);
    }

    public void putGameInHistory(String opponentName, boolean didWin) {
        MatchHistory matchHistory = new MatchHistory(opponentName, didWin, new Date());
        history.add(matchHistory);
    }

    //***
    public static HashMap<String, Account> getUsernameToAccount() {
        return usernameToAccount;
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
