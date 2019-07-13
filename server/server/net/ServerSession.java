package server.net;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSession {
    public static ArrayList<ServerSession> serverSessions = new ArrayList<>();
    private Socket socket;
    public Decoder decoder;
    public Encoder encoder;
    public DataOutputStream dos;
    public DataInputStream dis;
    String username; // if some serverSession's username is null, it means that this serverSession is not online

    public static ServerSession getSession(String username) {
        return serverSessions.stream().filter(serverSession -> serverSession.username.equals(username)).findFirst().orElse(null);
    }

    public ServerSession(Socket socket) {
        this.socket = socket;
        try {
            serverSessions.add(this);
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
                    int messageIndex = dis.readInt();
                    System.out.println("Payam oomad: " + Message.values()[messageIndex]);
                    decoder.decode(Message.values()[messageIndex]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void quit() {
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
