package model.player;


import model.Account;
import model.Deck;

public class HumanPlayer extends Player {
    private Account account;
    public HumanPlayer(Account account, Deck deck) {
        super(deck);
        this.account = account;
    }
}
