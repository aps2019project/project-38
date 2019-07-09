package server.net;

import com.google.gson.Gson;
import javafx.util.Pair;
import model.Account;
import model.GlobalChat;
import model.MatchHistory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Decoder {
    private ServerSession ss;

    public Decoder(ServerSession ss) {
        this.ss = ss;
    }

    public void decode(Message m) throws IOException {
        switch (m) {
            case saveTheGame:
                Account.saveAccounts();
                break;
            case quitTheApp:
                ss.quit();
                break;
            case createAccount: {
                String username = ss.dis.readUTF();
                String password = ss.dis.readUTF();
                String againPassword = ss.dis.readUTF();
                String result = Account.createAccount(username, password, againPassword);
                ss.encoder.sendString(result);
                break;
            }
            case login: {
                String username = ss.dis.readUTF();
                String password = ss.dis.readUTF();
                String result = Account.login(username, password);
                ss.encoder.sendString(result);
                if (result.equals("You logged in successfully")) {
                    ss.username = username;
                    ss.authToken = randomString();
                    ss.encoder.sendString(ss.authToken);
                }
                break;
            }
            case logOut: {
                String username = ss.dis.readUTF();
                Account.getUsernameToAccount().get(username).isActiveNow = false;
                ss.authToken = "";
                ss.username = null;
                break;
            }
            case updateMessages: {
                ArrayList<Account> allAccounts = Account.getSortedAccounts();
                ArrayList<Pair<Pair<String, Boolean>, Integer>> ranking = new ArrayList<>();
                for (Account account : allAccounts) {
                    int numberOfWins = 0;
                    for (MatchHistory matchHistory : account.getHistory()) {
                        if (matchHistory.getDidWin()) numberOfWins++;
                    }
                    Pair<Pair<String, Boolean>, Integer> pair = new Pair<>(new Pair<>(ss.username, account.isActiveNow), numberOfWins);
                    ranking.add(pair);
                }
                Gson gson = new Gson();
                String result = gson.toJson(ranking);
                ss.dos.writeUTF(result);
                break;
            }
            case sendMessage: {
                String messageText = ss.dis.readUTF();
                Pair<String, String> message = new Pair<>(ss.username, messageText);
                synchronized (GlobalChat.globalChat.messages) {
                    GlobalChat.globalChat.messages.add(message);
                    for (ServerSession ss : ServerSession.serverSessions) {
                        if (ss.username == null) continue;
                        ss.encoder.sendMessage(Message.updateMessages);
                        Gson gson = new Gson();
                        String ranking = gson.toJson(GlobalChat.globalChat.messages);
                        ss.encoder.sendString(ranking);
                    }
                }
                break;
            }
            case startTheGame: {
                String username = ss.dis.readUTF();
                if (Account.getUsernameToAccount().get(username).getCollection().getMainDeck() != null) {
                    ss.encoder.sendMessage(Message.accountDeckIsValid);
                } else {
                    ss.encoder.sendMessage(Message.accountDeckIsNotValid);
                }
                break;
            }
        }
    }


    public String randomString() {
        Random random = new Random();
        String randomString = "";
        randomString = randomString + (char) (random.nextInt(100));
        randomString = randomString + (char) (random.nextInt(100));
        randomString = randomString + (char) (random.nextInt(100));
        return randomString;
    }
}
