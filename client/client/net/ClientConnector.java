package client.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnector {
    public static Socket socket;
    public static Scanner scanner;
    public static PrintStream printer;

    public static void main() {
        try {
            socket = new Socket("localhost", 8000);
            scanner = new Scanner(socket.getInputStream());
            printer = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
