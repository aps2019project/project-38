package controller.window;

import model.Account;
import model.MatchHistory;
import view.Message;

import java.util.ArrayList;
import static view.Request.getNextRequest;

public class IntroWindow extends Window{

    public void main() {
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
                    Message.interUsername();
                    String accountName = getNextRequest();
                    Message.invalidPassword();
                    String password = getNextRequest();
                    Account.createAccount(accountName, password);
                    continue;
                case 2:
                    Message.interUsername();
                    accountName = getNextRequest();
                    Message.interPassword();
                    password = getNextRequest();
                    Account.login(accountName, password);
                    continue;
                case 3:
                    calculateLeaderBoard();
                    continue;
                case 0:
                    Account.getActiveAccount().save();
            }
        }
    }

    private void calculateLeaderBoard() {
        ArrayList<Account> allAccounts = Account.sortAccounts();
        int i = 0;
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
