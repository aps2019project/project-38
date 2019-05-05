package model;


import model.actions.*;
import model.actions.Killer;
import model.cards.Card;
import model.cards.Warrior;
import model.effects.Effect;
import model.gamestate.*;
import model.gamemoods.GameMood;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import model.triggers.Trigger;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public abstract class Game {
    GameMood gameMood;
    public int turn;
    Player[] players = new  Player[2];
    private Board board = new Board(this);
    public Timer timer = new Timer(Constant.GameConstants.turnTime, ignored -> endTurn());


    {
        turn = 0;
        getActivePlayer().mana = Constant.GameConstants.getTurnMana(turn);
        getActivePlayer().ableToReplaceCard = true;
        board.getCell(Constant.GameConstants.boardRow / 2 + 1, 0)
                .setWarrior(players[0].getWarriors().get(0));
        board.getCell(Constant.GameConstants.boardRow / 2 + 1, Constant.GameConstants.boardColumn - 1)
                .setWarrior(players[1].getWarriors().get(0));
        timer.start();
    }

    public Game(GameMood gameMood, Account accountOne, Deck deckOne, Account accountTwo, Deck deckTwo) {
        this.gameMood = gameMood;
        int randomIndex = (new Random(2)).nextInt();
        this.players[randomIndex] = new HumanPlayer(accountOne, deckOne);
        this.players[(randomIndex + 1) % 2] = new HumanPlayer(accountTwo, deckTwo);
    }

    public Game(GameMood gameMood, Account account, Deck humanDeck, Deck aIDeck) {
        this.gameMood = gameMood;
        int randomIndex = (new Random(2)).nextInt();
        players[randomIndex] = new HumanPlayer(account, humanDeck);
        players[(randomIndex + 1) % 2] = new AIPlayer(aIDeck);
    }

    public Player getActivePlayer() {
        return players[turn % 2];
    }

    public Player getWarriorsPlayer(Warrior warrior){
        if (players[0].getWarriors().contains(warrior)) {
            return players[0];
        }
        return players[1];
    }

    public Player getWarriorsEnemyPlayer(Warrior warrior) {
        return getWarriorsPlayer(warrior) == players[0] ? players[0] : players[1];
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void iterateAllTriggersCheck(GameState gameState) {
        board.iterateBoardTriggers(gameState);
        iteratePlayerTriggers(players[0], gameState);
        iteratePlayerTriggers(players[1], gameState);
        //apply buffer here if not in kill mod
    }

    private void iteratePlayerTriggers (Player player, GameState gameState) {
        for (Warrior warrior : player.getWarriors()) {
            for (Trigger trigger : warrior.getTriggers()) {
                trigger.check(gameState,warrior);
            }
        }
    }

    public void iterateAndExpireAllTriggers() {
        iterateAndExpirePlayerTriggers(players[0]);
        iterateAndExpirePlayerTriggers(players[1]);
        board.iterateAndExpireBoardTriggers();
    }

    private void iterateAndExpirePlayerTriggers(Player player) {
        for (Warrior warrior : player.getWarriors()) {
            for (Iterator<Trigger> iterator = warrior.getTriggers().iterator(); iterator.hasNext();) {
                Trigger trigger = iterator.next();
                if (trigger.duration > 0) {
                    trigger.duration --;
                    if (trigger.duration == 0) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void iterateAndExpireAllEffects() {
        iterateAndExpirePlayerEffects(players[0]);
        iterateAndExpirePlayerEffects(players[1]);
        board.iterateAndExpireBoardEffects();
    }

    private void iterateAndExpirePlayerEffects(Player player) {
        for (Warrior warrior : player.getWarriors()) {
            for (Iterator<Effect> iterator = warrior.getEffects().iterator(); iterator.hasNext();) {
                Effect effect = iterator.next();
                if (effect.duration > 0) {
                    effect.duration --;
                    if (effect.duration == 0) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void addNewCardToPlayerHand(Player player) {
        for (Map.Entry<Integer, Card> entry: player.getHand().entrySet()) {
            if (entry.getValue() == null) {
                int randomIndex = (new Random(player.getMainDeck().getCardIDs().size())).nextInt();
                entry.setValue(Card.getAllCards().get(player.getMainDeck().getCardIDs().get(randomIndex)).deepCopy());
                break;
            }
        }
    }

    private void killAllDiedWarriors() {
        //kill mod: ON
        killPlayerDiedWarriors(players[0]);
        killPlayerDiedWarriors(players[1]);
        //apply buffer here
        //kil mod: OFF
    }

    private void killPlayerDiedWarriors(Player player) {
        for (int i = 0; i < player.getWarriors().size(); i++) {
            if (player.getWarriors().get(i).getHp() <= 0) {
                Killer.kill(player.getWarriors().get(i));
            }
        }
    }

    public void move(Cell originCell, Cell targetCell) {
        if (getActivePlayer().getWarriors().contains(originCell.getWarrior()) &&
                targetCell.getWarrior() == null) {
            Move.doIt(originCell, targetCell);
            killAllDiedWarriors();
        }
    }

    public void comboAttack(Cell[] attackersCell, Cell defenderCell) {
        if (defenderCell.getWarrior() == null) return;
        for (Cell cell : attackersCell) {
            if (cell.getWarrior() == null) {
                return;
            }
        }
        for (int i = 0; i < attackersCell.length - 1; i++) {
            if (getWarriorsPlayer(attackersCell[i].getWarrior()) !=
                    getWarriorsPlayer(attackersCell[i + 1].getWarrior())) {
                return;
            }
        }
        if (defenderCell.getWarrior() == attackersCell[0].getWarrior()) {
            return;
        }
        ComboAttack.doIt(attackersCell, defenderCell);
        killAllDiedWarriors();

    }

    public void attack (Cell attackerCell, Cell defenderCell) {
        ArrayList<Warrior> activePlayerWarriors = getActivePlayer().getWarriors();
        if (activePlayerWarriors.contains(attackerCell.getWarrior()) &&
                !activePlayerWarriors.contains(defenderCell.getWarrior())) {
            Attack.doIt(attackerCell, defenderCell);
            killAllDiedWarriors();
        }
    }

    public void replaceCard (int handMapKey) {
        if (getActivePlayer().getHand().get(handMapKey) != null) {
            ReplaceCard.doIt(this, handMapKey);
            killAllDiedWarriors();
        }
    }

    public void useCard (int handMapKey, Cell cell) {
        if (getActivePlayer().getHand().get(handMapKey) != null) {
            UseCard.doIt(handMapKey, cell);
            killAllDiedWarriors();
        }
    }

    public void endTurn () {
        EndTurn.doIt(this);
        killAllDiedWarriors();
        startTurn();
    }

    private void startTurn() {
        StartTurn.doIt(this);
        killAllDiedWarriors();
    }
}