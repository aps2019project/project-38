package model;

import model.gamemodes.CarryingFlag;
import model.gamemodes.CollectingFlag;
import model.gamemodes.GameMode;
import model.gamemodes.KillingEnemyHero;

import java.util.HashMap;
import java.util.Map;

public class Level {
    private static HashMap<String, Level> levels = new HashMap<>();
    private Deck deck;
    private GameMode gameMode;
    private int prize;
    private boolean available = true;

    private Level(Deck deck, GameMode gameMode, int prize) {
        this.deck = deck;
        this.gameMode = gameMode;
        this.prize = prize;
    }

    static {
        {
            Level level = new Level(Deck.getAllDecks().get("level1"), new KillingEnemyHero(), 500);
            levels.put("1", level);
        }
        {
            Level level = new Level(Deck.getAllDecks().get("level2"), new CarryingFlag(), 1000);
            levels.put("2", level);
        }
        {
            Level level = new Level(Deck.getAllDecks().get("level3"),
                    new CollectingFlag(Constant.GameConstants.collectingFlagModeFlags), 1500);
            levels.put("3", level);
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Game getLevelGame(Account account) {
        Game game = new Game(gameMode.deepCopy(), account, deck);
        game.prize = prize;
        return game;
    }

    public int getPrize() {
        return prize;
    }

    public static HashMap<String, Level> getAvailableLevels() {
        HashMap<String, Level> result = new HashMap<>();
        for (Map.Entry<String, Level> entry : levels.entrySet()) {
            if (entry.getValue().available) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}