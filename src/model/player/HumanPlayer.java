package model.player;


import model.Account;
import model.Deck;
import view.images.LoadedImages;


public class HumanPlayer extends Player {
    private Account account;

    public HumanPlayer(Account account, Deck deck) {
        super(deck);
        this.account = account;
        avatar = LoadedImages.avatars[account.avatarNumber];
        username = account.getUsername();
    }

    public Account getAccount() {
        return account;
    }
}
