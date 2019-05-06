package controller.window;

import model.Account;
import model.Collection;
import model.Shop;
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
                    Window.openWindow(new CollectionWindow());
                    break tag1;
                case 2:
                    Window.openWindow(new ShopWindow());
                    break tag1;
                case 3:
                    //todo for ALI
                    break tag1;
                case 0:
                    Window.closeWindow(this);
                    Account.getActiveAccount().save();
                    break tag1;
                default:
                    Message.invalidInput();
            }
        }
    }
}
