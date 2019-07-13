package server.net;

import javafx.util.Pair;
import model.Account;
import model.Deck;
import model.Game;
import model.Level;
import model.gamemodes.CarryingFlag;
import model.gamemodes.CollectingFlag;
import model.gamemodes.GameMode;
import model.gamemodes.KillingEnemyHero;
import model.player.Player;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchMaker {
    public static ArrayList<Game> onGoingGames = new ArrayList<>();

    public static ArrayBlockingQueue<Account> killingQueue = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<Account> carryFlagQueue = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<Account> holdFlagQueue = new ArrayBlockingQueue<>(100);

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

    public static void createGameByGameParameters(Account account, ArrayList<String> gameParameters) {
        if (gameParameters.get(0).equals("Single Player")) {
            Game game;
            if (gameParameters.get(1).equals("Story")) {
                System.out.println(gameParameters.get(2));
                Matcher matcher = Pattern.compile("Mode: .+\nHero: .+ Prize: (?<prize>\\d+)")
                        .matcher(gameParameters.get(2));
                matcher.matches();
                int level = Integer.parseInt(matcher.group("prize")) / 500;
                game = Level.getAvailableLevels().get(String.valueOf(level)).getLevelGame(account);
            } else {
                Matcher matcher = Pattern.compile("Deck: (?<deckName>.+) Hero: .+")
                        .matcher(gameParameters.get(2));
                matcher.matches();
                Deck deck = account.getCollection().getAllDecks().get(matcher.group("deckName"));
                game = new Game(getMoodForStartingGame(2, gameParameters), account, deck);
            }
            //todo notify client by StartGame Message
        } else {
            GameMode gameMode = getMoodForStartingGame(1, gameParameters);//todo moini
        }

//        ArenaController.ac.init(game);//todo moini and rest of it
//        game.initialiseGameFields();
        //todo notify client by StartGame Message
    }

    private static GameMode getMoodForStartingGame(int index, ArrayList<String> gameParameters) {
        if (gameParameters.get(index).equals("Killing Enemy Hero")) {
            return new KillingEnemyHero();
        } else if (gameParameters.get(index).equals("Carrying Flag")) {
            return new CarryingFlag();
        } else {
            return new CollectingFlag(Integer.parseInt(gameParameters.get(index)));
        }
    }
}
