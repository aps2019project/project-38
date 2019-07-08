package server.net;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Encoder {
    ServerSession ss;

    public Encoder(ServerSession ss) {
        this.ss = ss;
    }

    public synchronized void sendCode(Message m){
        try {
            ss.dos.writeInt(m.ordinal());
            ss.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendData(Object o){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ss.dos);
            oos.writeObject(o);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
