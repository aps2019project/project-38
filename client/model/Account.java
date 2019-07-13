package model;

import client.net.Digikala;

public class Account {
    public static Account activeAccount = new Account();

    public String username;

    private Account() {

    }

    public Collection getCollection() {
        return Digikala.getCollection();
    }

    public int getDerrick() {
        return Digikala.getDerrick();
    }

    public static Account getActiveAccount() {
        return activeAccount;
    }

    public static void handleAssignInfoToClient(String userName) {
        activeAccount.username = userName;
    }
}
