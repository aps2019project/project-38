package server.net;

import com.google.gson.Gson;
import javafx.util.Pair;
import model.Game;
import model.cards.Card;
import model.cards.Warrior;
import model.player.HumanPlayer;
import model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cameraman {
    Game game;
    public ArrayList<ServerSession> spectators = new ArrayList<>();
    public ArrayList<Pair<Message, ArrayList<Object>>> tape = new ArrayList<>();

    public Cameraman(Game game) {
        this.game = game;
    }

    public void sendToBothPlayers(Message m,Object... data){
        addToTape(m,data);

        for (Player player : game.getPlayers()) {
            if(player instanceof HumanPlayer){
                ServerSession.getSession(player.username).encoder.sendPackage(m,data);
            }
        }
        sendToSpectators(m,data);
    }

    public void sendToActivePlayer(Message m,Object... data){
        addToTape(m,data);

        Player player = game.getActivePlayer();
        if(player instanceof HumanPlayer){
            ServerSession.getSession(player.username).encoder.sendPackage(m,data);
        }
        sendToSpectators(m,data);
    }

    private void sendToSpectators(Message m,Object... data){
        for (ServerSession spectator : spectators) {
            spectator.encoder.sendPackage(m,data);
        }
    }

    private void addToTape(Message m,Object... data){
        Pair<Message,ArrayList<Object>> pair = new Pair<>(m,new ArrayList<>(Arrays.asList(data)));
        tape.add(pair);
    }

    public void sendHandToActivePlayer(HashMap<Integer, Card> handMap){
        Gson gson = new Gson();
        ArrayList<Object> data = new ArrayList<>();
        Message m = Message.buildPlayerHand;

        data.add(game.getActivePlayerIndex()+1);
        data.add(handMap.size());
        for (Map.Entry<Integer, Card> entry : handMap.entrySet()) {
            if(entry.getValue() instanceof Warrior){
                data.addAll(Arrays.asList(entry.getKey(),Message.itsWarrior.ordinal(),gson.toJson(entry.getValue())));
            }else {
                data.addAll(Arrays.asList(entry.getKey(),Message.itsSpell.ordinal(),gson.toJson(entry.getValue())));
            }
        }

        sendToActivePlayer(m,data.toArray());
    }
}
