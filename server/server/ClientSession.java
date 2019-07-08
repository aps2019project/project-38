package server;

import com.google.gson.Gson;
import javafx.scene.control.Label;
import javafx.util.Pair;
import model.Account;
import model.MatchHistory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientSession {
    private static ArrayList<ClientSession> sessions = new ArrayList<>();

    public static ClientSession getSession(String username) {
        return sessions.stream().filter(clientSession -> clientSession.username.equals(username)).findFirst().orElse(null);
    }

    private Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    String username = "loading...";

    ClientSession(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                listen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void listen() throws IOException {
        System.out.println(":))");
        while (true) {
            String message = dis.readUTF();
            if (Messages.valueOf(message).equals(Messages.saveTheGame)) {
                Account.saveAccounts();
            }
            if (Messages.valueOf(message).equals(Messages.quitTheGame)) {
                logout();
            }
            if (Messages.valueOf(message).equals(Messages.createAccount)) {
                String username = dis.readUTF();
                String password = dis.readUTF();
                String againPassword = dis.readUTF();
                String result = Account.createAccount(username, password, againPassword);
                dos.writeUTF(result);
            }

            if (Messages.valueOf(message).equals(Messages.login)) {
                String username = dis.readUTF();
                String password = dis.readUTF();
                String result = Account.login(username, password);
            }

            if (Messages.valueOf(message).equals(Messages.showLeaderBoard)) {
                ArrayList<Account> allAccounts = Account.getSortedAccounts();
                ArrayList<Pair<String, Integer>> ranking = new ArrayList<>();
                for (Account account : allAccounts) {
                    int numberOfWins = 0;
                    for (MatchHistory matchHistory : account.getHistory()) {
                        if (matchHistory.getDidWin()) numberOfWins++;
                    }
                    Pair<String, Integer> pair = new Pair<>(username, numberOfWins);
                    ranking.add(pair);
                }
                Gson gson = new Gson();
                String result = gson.toJson(ranking);
                dos.writeUTF(result);
            }
        }
    }

    public void logout() {
        sessions.remove(this);
        try {
            dos.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
