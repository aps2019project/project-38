package server.net;

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

    public synchronized void sendPackage(Message m,Object... datas){
        sendMessage(m);
        for (Object data : datas) {
            sendObject(data);
        }
    }
}
