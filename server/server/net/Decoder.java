package server.net;

import com.google.gson.Gson;
import javafx.util.Pair;
import model.Account;
import model.MatchHistory;

import java.io.IOException;
import java.util.ArrayList;

public class Decoder {
    ServerSession ss;

    public Decoder(ServerSession ss) {
        this.ss = ss;
    }

    public void decode(Message m) throws IOException {
        switch (m) {
            case saveTheGame:
                Account.saveAccounts();
                break;
            case quitTheGame:
                ss.logout();
                break;
            case createAccount: {
                String username = ss.dis.readUTF();
                String password = ss.dis.readUTF();
                String againPassword = ss.dis.readUTF();
                String result = Account.createAccount(username, password, againPassword);
                ss.dos.writeUTF(result);
                break;
            }
            case login: {
                String username = ss.dis.readUTF();
                String password = ss.dis.readUTF();
                String result = Account.login(username, password);
                ss.dos.writeUTF(result);
                break;
            }
            case showLeaderBoard: {
                ArrayList<Account> allAccounts = Account.getSortedAccounts();
                ArrayList<Pair<String, Integer>> ranking = new ArrayList<>();
                for (Account account : allAccounts) {
                    int numberOfWins = 0;
                    for (MatchHistory matchHistory : account.getHistory()) {
                        if (matchHistory.getDidWin()) numberOfWins++;
                    }
                    Pair<String, Integer> pair = new Pair<>(ss.username, numberOfWins);
                    ranking.add(pair);
                }
                Gson gson = new Gson();
                String result = gson.toJson(ranking);
                ss.dos.writeUTF(result);
                break;
            }
            case startTheGame: {
                String username = ss.dis.readUTF();
                if (Account.getUsernameToAccount().get(username).getCollection().getMainDeck() != null) {
                    ss.encoder.sendMessage(Message.accountDeckIsValid);
                } else {
                    ss.encoder.sendMessage(Message.accountDeckIsNotValid);
                }
            }
        }
    }
}
