//package controller.window;
//
//import view.Message;
//import model.Account;
//import model.MatchHistory;
//
//import java.util.ArrayList;
//
//import static view.Request.getNextRequest;
//
//public class IntroWindow extends Window {
//
//    @Override
//    public void main() {
//        tag1:
//        while (true) {
//            Message.showAccountHelp();
//            String input = getNextRequest();
//            if (!input.matches("\\d+")) {
//                Message.invalidInput();
//                continue;
//            }
//            int indexOfSelectedSubMenu = Integer.parseInt(input);
//            switch (indexOfSelectedSubMenu) {
//                case 1:
////                    handleCreatingAccount();
//                    continue;
//                case 2:
////                    if(handleLoginAccount())break tag1;
//                    continue;
//                case 3:
////                    calculateLeaderBoard();
//                    continue;
//                case 0:
////                    Account.saveAccounts();
//                    System.exit(0);
//                default:
//                    Message.invalidInput();
//            }
//        }
//    }
//
////    private void handleCreatingAccount() {
////        Message.interUsername();
////        String userName = getNextRequest();
////        Message.interPassword();
////        String password = getNextRequest();
////        Message.interPasswordAgain();
////        String againPassword = getNextRequest();
////        Account.createAccount(userName, password, againPassword);
////    }
//
////    private boolean handleLoginAccount() {
////        Message.interUsername();
////        String userName = getNextRequest();
////        Message.interPassword();
////        String password = getNextRequest();
////        if (Account.login(userName, password)) { // :(
////            new MainMenu().openWindow();
////            return true;
////        }
////        return false;
////    }
//}
