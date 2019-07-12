package model.effects;


import java.io.*;

public abstract class Effect implements Serializable {
    public int duration;
    Dispelablity dispelablity;

    public Effect(int duration, Dispelablity dispelablity) {
        this.duration = duration;
        this.dispelablity=dispelablity;
    }

    public Effect deepCopy(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Effect) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.err.println("could not deep copy in effect:");
            e.printStackTrace();
        }
        return this;
    }

    public Dispelablity getDispelablity() {
        return dispelablity;
    }
}