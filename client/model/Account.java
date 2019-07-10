package model;

import java.util.HashMap;
import java.util.Map;

public class Account {
    public static Account activeAccount = new Account();

    public String authToken = null;
    public String username;

    private Account() {

    }

    public Collection getCollection() {
        //todo server
    }

    public int getDerrick() {
        //todo server
    }

    public void setDerrick(int newDerrick) {
        //todo server
    }

    public static Account getActiveAccount() {
        return activeAccount;
    }
}
