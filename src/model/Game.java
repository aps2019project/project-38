package model;


import model.actions.gameaction.EndTurn;
import model.actions.triggeraction.Killer;
import model.cards.Card;
import model.cards.spells.Spell;
import model.cards.warriors.Warrior;
import model.effects.Attacked;
import model.effects.Effect;
import model.effects.Flying;
import model.effects.Moved;
import model.gamestate.*;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import model.triggers.Trigger;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



public class Game {
    public int turn;
    private Player[] players = new  Player[2];
    private Board board = new Board(this);
    public Timer timer = new Timer(Constant.GameConstants.turnTime, ignored -> endTurn());

    {
        turn = 0;
        getActivePlayer().mana = Constant.GameConstants.getTurnMana(turn);
        getActivePlayer().ableToReplaceCard = true;
        timer.start();
    }

    public Game(Account accountOne, Deck deckOne, Account accountTwo, Deck deckTwo) {
        int randomIndex = (new Random(2)).nextInt();
        this.players[randomIndex] = new HumanPlayer(accountOne, deckOne);
        this.players[(randomIndex + 1) % 2] = new HumanPlayer(accountTwo, deckTwo);
    }

    public Game(Account account, Deck humanDeck, Deck aIDeck) {
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

    public Board getBoard() {
        return board;
    }

    public void iterateAllTriggers (GameState gameState) {
        board.iterateBoardTriggers(gameState);
        iteratePlayerTriggers(players[0], gameState);
        iteratePlayerTriggers(players[1], gameState);
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

    private void killPlayerDiedWariors(Player player) {
        for (int i = 0; i < player.getWarriors().size(); i++) {
            if (player.getWarriors().get(i).getHp() <= 0) {
                Killer.kill(player.getWarriors().get(i));
            }
        }
    }

    public void move(Cell originCell, Cell targetCell) {
        if (getActivePlayer().getWarriors().contains(originCell.getWarrior()) &&
                targetCell.getWarrior() == null) {
            Warrior warrior = originCell.getWarrior();
            int manhattanDistance = board.getManhattanDistance(originCell, targetCell);
            if (checkWarriorEffectsForMove(warrior, manhattanDistance)) {
                originCell.setWarrior(null);
                targetCell.setWarrior(warrior);
                warrior.setCell(targetCell);
                warrior.getEffects().add(new Moved());
                MoveState moveState = new MoveState(warrior, originCell, targetCell);
                iterateAllTriggers(moveState);
            }
        }
    }

    private boolean checkWarriorEffectsForMove (Warrior warrior, int manhattanDistance) {
        boolean result = warrior.getEffects().stream().noneMatch
                (effect -> effect instanceof Attacked || effect instanceof Moved);
        if (result) {
            if (manhattanDistance <= Constant.WarriorConstants.maxMove) {
                return true;
            }
            else {
                return warrior.getEffects().stream().anyMatch(a -> a instanceof Flying);
            }
        }
        return false;
    }

    public void attack (Cell attackerCell, Cell defenderCell) {

    }

    public void replaceCard (int handMapKey) {
        try {
            if (getActivePlayer().ableToReplaceCard) {
                Card card = getActivePlayer().getHand().get(handMapKey);
                Random random = new Random();
                Card newCard;
                while (true) {
                    int randomIndex = random.nextInt(getActivePlayer().getMainDeck().getCardIDs().size());
                    int cardID = getActivePlayer().getMainDeck().getCardIDs().get(randomIndex);
                    if (cardID != card.getID()) {
                        newCard = Card.getAllCards().get(cardID);
                        break;
                    }
                }
                getActivePlayer().getHand().put(handMapKey, newCard);
                getActivePlayer().ableToReplaceCard = false;
            ReplaceCardState replaceCardState = new ReplaceCardState();
            iterateAllTriggers(replaceCardState);
            }
        }
        catch (Exception ignored) {}
    }

    public void useCard (int handMapKey, Cell cell) {
        try {
            Card card = getActivePlayer().getHand().get(handMapKey);
            if (getActivePlayer().mana >= card.getRequiredMana() /*&&
                    card.checkTarget(cell, this)*/) {
                getActivePlayer().mana -= card.getRequiredMana();
                getActivePlayer().getHand().put(handMapKey, null);
                card.apply(cell);
                GameState gameState;
                if (card instanceof Spell) {
                    gameState = new UseSpellState(cell, (Spell)card);//todo implement spell target and change this line
                }else {
                    gameState = new PutMinionState((Warrior) card);
                }
                iterateAllTriggers(gameState);
            }
        }
        catch (ClassCastException ignored) {
            System.err.println("((useCard method in Game, has a problem))");
            //todo
        }
        catch (Exception ignored) {}
    }

    public void endTurn () {
        EndTurn.doIt(this);
    }
}