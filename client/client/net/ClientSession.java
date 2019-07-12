package client.net;

import java.io.*;
import java.net.Socket;

public class ClientSession {
    static Socket socket;
    public static DataInputStream dis;
    public static DataOutputStream dos;
    static boolean useListener = false; //set true at the start of game and set false at the end of the game

    public static void connect() {
        try {
            socket = new Socket("localhost", 8000);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            new Thread(() -> {
                while (true) {
                    try {
                        int messageIndex = dis.readInt();
                        System.out.println(dis+";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
                        System.out.println(Decoder.readObject());
//                        Decoder.decode(Message.values()[messageIndex]); for debug
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listen() { // MOEINI AND HASHEM
        while (useListener) {
            try {
                int messageIndex = dis.readInt();
                Decoder.decode(Message.values()[messageIndex]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
