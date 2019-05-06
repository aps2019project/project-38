package controller.window;

import model.Account;
import view.Message;

import java.nio.channels.AcceptPendingException;

import static view.Request.getNextRequest;

public class MainMenu extends Window{
    public void main(){
        tag1:
        while (true){
            Message.showMainMenuHelp();
            String input = getNextRequest();
            if (!input.matches("\\d+")) {
                Message.invalidInput();
                continue;
            }
            int indexOfSelectedSubMenu = Integer.parseInt(input);
            switch (indexOfSelectedSubMenu){
                case 1:
                    Window.openWindow(new CollectionWindow());
                    continue;
                case 2:
                    Window.openWindow(new ShopWindow());
                    continue;
                case 3:
                    //todo for ALI
                    continue;
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
