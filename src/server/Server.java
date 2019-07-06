package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                ServerSocket sc = new ServerSocket(8000); //todo : get port from config.txt
                Socket socket = sc.accept();
                new ClientConnector(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}