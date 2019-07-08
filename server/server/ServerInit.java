package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInit {
    public static void main(String[] args) throws IOException {
        ServerSocket sc = new ServerSocket(8000); //todo : get port from config.txt
        while (true) {
            Socket socket = sc.accept();
            new ClientSession(socket);
        }
    }
}