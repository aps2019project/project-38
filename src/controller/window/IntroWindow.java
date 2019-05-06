package controller.window;

import model.Account;
import model.Collection;
import model.MatchHistory;
import model.cards.CardFactory;
import view.Message;

import java.util.ArrayList;

import static view.Request.getNextRequest;

public class IntroWindow extends Window {

    @Override
    public void main() {
        tag1:
        while (true) {
            Message.showAccountHelp();
            String input = getNextRequest();
            if (!input.matches("\\d+")) {
                Message.invalidInput();
                continue;
            }
            int indexOfSelectedSubMenu = Integer.parseInt(input);
            switch (indexOfSelectedSubMenu) {
                case 1:
                    handleCreatingAccount();
                    continue;
                case 2:
                    if(handleLoginAccount())break tag1;
                    continue;
                case 3:
                    calculateLeaderBoard();
                    continue;
                case 0:
                    Account.saveAccounts();
                    System.exit(0);
                default:
                    Message.invalidInput();
            }
        }
    }

    private void handleCreatingAccount() {
        Message.interUsername();
        String userName = getNextRequest();
        Message.interPassword();
        String password = getNextRequest();
        Message.interPasswordAgain();
        String againPassword = getNextRequest();
        Account.createAccount(userName, password, againPassword);
    }

    private boolean handleLoginAccount() {
        Message.interUsername();
        String userName = getNextRequest();
        Message.interPassword();
        String password = getNextRequest();
        if (Account.login(userName, password)) { // :(
            Window.openWindow(new MainMenu());
            return true;
        }
        return false;
    }

    private void calculateLeaderBoard() {
        ArrayList<Account> allAccounts = Account.sortAccounts();
        if (allAccounts.size() == 0) {
            Message.leaderBoardIsEmpty();
            return;
        }
        int i = 1;
        for (Account account : allAccounts) {
            int numberOfWins = 0;
            for (MatchHistory matchHistory : account.getHistory()) {
                if (matchHistory.getDidWin()) numberOfWins++;
            }
            Message.showLeaderBoard(i, account.getUsername(), numberOfWins);
            i++;
        }
    }
}
