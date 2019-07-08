package client.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSession {
    static Socket socket;
    public static DataInputStream dis;
    public static DataOutputStream dos;

    public static void connect() {
        try {
            socket = new Socket("localhost", 8000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
