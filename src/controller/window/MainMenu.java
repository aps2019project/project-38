package controller.window;

import model.Account;
import view.Message;


import static view.Request.getNextRequest;

public class MainMenu extends Window {
    public void main() {
        tag1:
        while (true) {
            Message.showMainMenuHelp();
            String input = getNextRequest();
            if (!input.matches("\\d+")) {
                Message.invalidInput();
                continue;
            }
            int indexOfSelectedSubMenu = Integer.parseInt(input);
            switch (indexOfSelectedSubMenu) {
                case 1:
                    new CollectionWindow().openWindow();
                    break tag1;
                case 2:
                    new ShopWindow().openWindow();
                    break tag1;
                case 3:
                    new GameWindow().openWindow();
                    break tag1;
                case 0:
                    this.closeWindow();
                    Account.saveAccounts();
                    break tag1;
                default:
                    Message.invalidInput();
            }
        }
    }
}