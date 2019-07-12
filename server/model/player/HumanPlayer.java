package model.player;


import model.Account;
import model.Deck;
import model.Game;


public class HumanPlayer extends Player {
    private Account account;

    public HumanPlayer(Account account, Deck deck, Game game) {
        super(deck, game);
        this.account = account;
        avatarIndex = account.avatarNumber;
        username = account.getUsername();
    }

    public Account getAccount() {
        return account;
    }
}
