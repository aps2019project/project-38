package model;

public class Account {
    public static Account activeAccount = new Account();

    public String authToken = null;
    public String username;

    private Account() {

    }
}