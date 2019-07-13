package model;

import client.net.Box;
import client.net.Digikala;
import client.net.Encoder;
import client.net.Message;

import java.util.ArrayList;

public interface Level {
    Box<ArrayList<String>> levelsDescription = new Box<>();
    static ArrayList<String> getLevelsDescription() {
        Encoder.sendPackage(Message.LevelsDescription);
        Digikala.wait(levelsDescription);
        return levelsDescription.obj;
    }
}