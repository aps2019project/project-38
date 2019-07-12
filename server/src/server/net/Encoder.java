package server.net;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Encoder {
    ServerSession ss;

    public Encoder(ServerSession ss) {
        this.ss = ss;
    }

    public synchronized void sendMessage(Message m) {
        try {
            ss.dos.writeInt(m.ordinal());
            ss.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendString(String s) {
        try {
            ss.dos.writeUTF(s);
            ss.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendObject(Object o) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ss.dos);
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendObjectJ(Object o){
        Gson gson = new Gson();
        sendString(gson.toJson(o));
    }

    public synchronized void sendPackage(Message m,Object... datas){
        sendMessage(m);
        for (Object data : datas) {
            sendObject(data);
        }
    }

    public synchronized void sendPackageJ(Message m,Object... datas){
        sendMessage(m);
        for (Object data : datas) {
            sendObjectJ(data);
        }
    }

    public static void sendPackageToAll(Message m, Object... data) {
        for (ServerSession serverSession : ServerSession.serverSessions) {
            if (serverSession.username != null) {
                serverSession.encoder.sendPackage(m, data);
            }
        }
    }
}
