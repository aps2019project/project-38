package server;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ClientConnector {
    public static HashMap<String, ClientConnector> getUserListenerByName = new HashMap<>();
    private Socket socket;
    Scanner scanner;
    PrintStream printStream;
    boolean isOnline = false;
    String name;

    ClientConnector(Socket socket) {
        this.socket = socket;
        try {
            scanner = new Scanner(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            //todo : run a method to answer to client
        }).start();
    }

    public void closeClientListener(String username) {
        ClientConnector ul = getUserListenerByName.get(username);
        getUserListenerByName.remove(username);
        printStream.close();
        scanner.close();
        try {
            ul.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ul = null;
    }

}
