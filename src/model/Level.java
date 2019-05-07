package model;

import model.gamemoods.CarryingFlag;
import model.gamemoods.CollectingFlag;
import model.gamemoods.GameMood;
import model.gamemoods.KillingEnemyHero;

import java.util.HashMap;
import java.util.Map;

public class Level {
    private static HashMap<String, Level> levels = new HashMap<>();
    private Deck deck;
    private GameMood gameMood;
    private int prise;
    private boolean available = true; //todo badana

    public Level(Deck deck, GameMood gameMood, int prise) {
        this.deck = deck;
        this.gameMood = gameMood;
        this.prise = prise;
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
                    new CollectingFlag(Constant.GameConstants.collectingFlagMoodFlags), 1500);
            levels.put("3", level);
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public GameMood getGameMood() {
        return gameMood;
    }

    public Game getLevelGame(Account account) {
        Game game = new Game(gameMood, account, deck);
        game.prise = prise;
        return game;
    }

    public int getPrise() {
        return prise;
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
