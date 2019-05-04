package controller.window;

import view.Message;

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
                    new CollectionWindow().main();
                    continue;
                case 2:
                    new ShopWindow().main();
                    continue;
                case 3:
                    //todo for ALI
                    continue;
                case 0:
                    break tag1;
                default:
                    Message.invalidInput();
            }
        }
    }
}
