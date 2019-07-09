package server.net;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

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

    String username;
    String authToken = "";

    public ServerSession(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            decoder = new Decoder(this);
            encoder = new Encoder(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                while (true) {
                    String gotAuthToken = dis.readUTF();
                    int messageIndex = dis.readInt();
                    if (!gotAuthToken.equals(authToken)) {
                        continue;
                    }
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


    public String randomString() {
        Random random = new Random();
        String randomString = "";
        randomString = randomString + (char) (random.nextInt(100));
        randomString = randomString + (char) (random.nextInt(100));
        randomString = randomString + (char) (random.nextInt(100));
        return randomString;
    }
}
