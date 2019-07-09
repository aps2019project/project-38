package client.net;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSession {
    static Socket socket;
    public static DataInputStream dis;
    public static DataOutputStream dos;
    static boolean useListener = false;//set true at the start of game and set false at the end of the game

    public static void connect() {
        try {
            socket = new Socket("localhost", 8000);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listen() {
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
