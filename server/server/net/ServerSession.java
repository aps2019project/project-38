package server.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSession {
    private static ArrayList<ServerSession> serverSessions = new ArrayList<>();

    public static ServerSession getSession(String username) {
        return serverSessions.stream().filter(serverSession -> serverSession.username.equals(username)).findFirst().orElse(null);
    }

    private Socket socket;
    Decoder decoder;
    Encoder encoder;
    public DataOutputStream dos;
    public DataInputStream dis;
    String username = "loading...";

    public ServerSession(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            decoder = new Decoder(this);
            encoder = new Encoder(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                while (true) {
                    int messageIndex = dis.readInt();
                    decoder.decode(Message.values()[messageIndex]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void logout() {
        serverSessions.remove(this);
        try {
            dos.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
