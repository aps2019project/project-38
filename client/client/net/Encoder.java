package client.net;

import com.google.gson.Gson;
import model.Account;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Encoder {
    public static synchronized void sendMessage(Message m) {
        try {
            if (Account.activeAccount.authToken != null) {
                ClientSession.dos.writeUTF(Account.activeAccount.authToken);
            }
            ClientSession.dos.writeInt(m.ordinal());
            ClientSession.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendString(String s) {
        try {
            if (Account.activeAccount.authToken != null) {
                ClientSession.dos.writeUTF(Account.activeAccount.authToken);
            }
            ClientSession.dos.writeUTF(s);
            ClientSession.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendObject(Object o) {
        try {
            if (Account.activeAccount.authToken != null) {
                ClientSession.dos.writeUTF(Account.activeAccount.authToken);
            }
            ObjectOutputStream oos = new ObjectOutputStream(ClientSession.dos);
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendObjectJ(Object o) {
        Gson gson = new Gson();
        sendObject(gson.toJson(o));
    }

    public static synchronized void sendPackage(Message m, Object... datas) {
        sendMessage(m);
        for (Object data : datas) {
            sendObject(data);
        }
    }
}
