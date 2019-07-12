package model;

import javafx.util.Pair;

import java.util.ArrayList;

public class GlobalChat {
    public static GlobalChat globalChat = new GlobalChat();
    public ArrayList<Pair<String, String>> messages = new ArrayList<>();

    private GlobalChat() {

    }
}
