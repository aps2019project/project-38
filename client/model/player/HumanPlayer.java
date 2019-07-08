package model.player;


import model.Deck;
import model.Game;
import view.images.LoadedImages;


public class HumanPlayer extends Player {
    private Account account;

    public HumanPlayer(Account account, Deck deck, Game game) {
        super(deck, game);
        this.account = account;
        avatar = LoadedImages.avatars[account.avatarNumber];
        username = account.getUsername();
    }

    public Account getAccount() {
        return account;
    }
}
