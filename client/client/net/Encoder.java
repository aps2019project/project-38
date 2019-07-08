package client.net;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Encoder {
    public static synchronized void sendMessage(Message m) {
        try {
            ClientSession.dos.writeInt(m.ordinal());
            ClientSession.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendString(String s) {
        try {
            ClientSession.dos.writeUTF(s);
            ClientSession.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendObject(Object o) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ClientSession.dos);
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
