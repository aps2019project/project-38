package server;

import model.Account;
import server.net.ServerSession;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInit {
    public static void main(String[] args) throws IOException {
        Account.loadAccounts();

        ServerSocket sc = new ServerSocket(8000); //todo : get port from config.txt
        while (true) {
            Socket socket = sc.accept();
            new ServerSession(socket);
        }
    }
}