package server.net;

import javafx.util.Pair;
import model.Account;
import model.Game;
import model.player.Player;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class MatchMaker {
    public static ArrayList<Game> onGoingGames = new ArrayList<>();

    public static ArrayBlockingQueue<Account> killingQueue = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<Account> carryFlagQueue = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<Account> holdFlagQueue = new ArrayBlockingQueue<>(100);

    //todo: a queue that waiting players are there and a thread that matches them with each other.

    public static void makeMatchMakingThreads(){
        new Thread(() -> {
            try {
                Account account1 = killingQueue.take();
                Account account2 = killingQueue.take();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Account account1 = carryFlagQueue.take();
                Account account2 = carryFlagQueue.take();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Account account1 = holdFlagQueue.take();
                Account account2 = holdFlagQueue.take();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static Pair<Player,Game> PGPGetter(String username){
        Game game = onGoingGames.stream()
                .filter(game1 -> game1.getPlayers()[0].username.equals(username)
                        ||game1.getPlayers()[1].username.equals(username))
                .findAny().orElse(null);

        if(game == null){
            return null;
        }

        Player player = game.getPlayers()[0].username.equals(username)?game.getPlayers()[0]:game.getPlayers()[1];
        return new Pair<>(player,game);
    }
}
