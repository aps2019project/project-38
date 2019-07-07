package server;

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

        }).start();
    }

    private void listen(){

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
