package server.net;

import javafx.util.Pair;
import model.Game;
import model.player.Player;

import java.util.ArrayList;

public class MatchMaker{
    public static ArrayList<Game> onGoingGames = new ArrayList<>();
    //todo: a queue that waiting players are there and a thread that matches them with each other.

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
