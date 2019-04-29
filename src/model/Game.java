package model;


import model.cards.Card;
import model.cards.warriors.Warrior;
import model.effects.Attacked;
import model.effects.Flying;
import model.effects.Moved;
import model.gamestate.GameState;
import model.gamestate.Move;
import model.gamestate.ReplaceCard;
import model.gamestate.TurnEnd;
import model.player.Player;
import model.triggers.Trigger;

import javax.swing.*;
import java.util.ArrayList;


public class Game {
    private int turn;
    private Player[] players = new  Player[2];
    private Board board = new Board(this);
    private Timer timer = new Timer(Constant.GameConstants.turnTime, ignoredVariable -> endTurn());

    {
        turn = 0;
        timer.start();
    }

    public Game() {
        //todo
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
                trigger.check(gameState,player);
            }
        }
    }

    public void move(Cell originCell, Cell targetCell) {
        if (getActivePlayer().getWarriors().contains(originCell.getWarrior()) &&
                targetCell.getWarrior() == null) {
            Warrior warrior = originCell.getWarrior();
            int manhatanDistance = board.getManhatanDistance(originCell, targetCell);
            if (checkWarriorEffectsForMove(warrior, manhatanDistance)) {
                originCell.setWarrior(null);
                targetCell.setWarrior(warrior);
                warrior.setCell(targetCell);
                Move move = new Move(warrior, originCell, targetCell);
                iterateAllTriggers(move);
            }
        }
    }

    private boolean checkWarriorEffectsForMove (Warrior warrior, int manhatanDistance) {
        boolean result = warrior.getEffects().stream().noneMatch
                (effect -> effect instanceof Attacked || effect instanceof Moved);
        if (result) {
            if (manhatanDistance <= Constant.WarriorConstants.maxMove) {
                return true;
            }
            else {
                return warrior.getEffects().stream().anyMatch(a -> a instanceof Flying);
            }
        }
        return false;
    }

    public void attack (Cell attackerCell, Cell defenderCell) {
        //todo
    }

    public void replaceCard (Card card) {
        if (getActivePlayer().getHand().contains(card)) {
        }
        ReplaceCard replaceCard = new ReplaceCard();
    }

    public void useCard (Card card) {
        //todo
    }

    public void endTurn () {
        TurnEnd turnEnd = new TurnEnd();
        iterateAllTriggers(turnEnd);
        turn ++;
        timer.restart();
    }
}