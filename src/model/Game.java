package model;


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

import javax.swing.*;
import java.util.Random;



public class Game {
    private int turn;
    private Player[] players = new  Player[2];
    private Board board = new Board(this);
    private Timer timer = new Timer(Constant.GameConstants.turnTime, ignored -> endTurn());

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

    private void iterateAllTriggers (GameState gameState) {
        board.iterateBoardTriggers(gameState);
        iteratePlayerTriggers(players[0], gameState);
        iteratePlayerTriggers(players[1], gameState);
    }

    private void iteratePlayerTriggers (Player player, GameState gameState) {
        for (Warrior warrior : player.getWarriors()) {
            for (Trigger trigger : warrior.getTriggers()) {
                trigger.check(gameState);
            }
        }
    }

    private void iterateAllEffects() {
        iteratePlayerEffects(players[0]);
        iteratePlayerEffects(players[1]);
    }

    private void iteratePlayerEffects(Player player) {
        for (Warrior warrior : player.getWarriors()) {
            for (Effect effect : warrior.getEffects()) {
                if (effect.duration > 0) {
                    effect.duration --;
                    if (effect.duration == 0) {
                        warrior.getEffects().remove(effect);
                    }
                }
            }
        }
    }

    private void killPlayerDiedWariors(Player player) {
        player.getWarriors().removeIf(warrior -> warrior.getHP() <= 0);
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
                warrior.getEffects().add(new Moved());
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
        Warrior attacker = attackerCell.getWarrior();
        Warrior defender = defenderCell.getWarrior();
        if (getActivePlayer().getWarriors().contains(attacker) &&
                !getActivePlayer().getWarriors().contains(defender)) {
            //todo
        }
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
            ReplaceCard replaceCard = new ReplaceCard();
            iterateAllTriggers(replaceCard);
            }
        }
        catch (Exception ignored) {}
    }

    public void useCard (int handMapKey, Cell cell) {
        try {
            Card card = getActivePlayer().getHand().get(handMapKey);
            if (getActivePlayer().mana >= card.getRequiredMana() &&
                    card.checkTarget(cell, this)) {
                getActivePlayer().mana -= card.getRequiredMana();
                getActivePlayer().getHand().put(handMapKey, null);
                card.apply(cell);
                GameState gameState;
                if (card instanceof Spell) {
                    gameState = new UseSpell();
                }else {
                    gameState = new PutMinion((Warrior) card);
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
        TurnEnd turnEnd = new TurnEnd();
        iterateAllEffects();
        iterateAllTriggers(turnEnd);
        turn ++;
        getActivePlayer().mana = Constant.GameConstants.getTurnMana(turn);
        getActivePlayer().ableToReplaceCard = true;
        timer.restart();
    }
}