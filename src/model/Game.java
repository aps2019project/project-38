package model;


import model.actions.*;
import model.actions.Killer;
import model.cards.Card;
import model.cards.Spell;
import model.cards.Warrior;
import model.effects.Effect;
import model.gamestate.*;
import model.gamemoods.GameMood;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import model.triggers.Trigger;

import javax.swing.Timer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class Game {
    GameMood gameMood;
    public int turn;
    Player[] players = new Player[2];
    private Board board = new Board(this);
    //    public Timer timer = new Timer(Constant.GameConstants.turnTime, ignored -> endTurn());
    ArrayList<Spell> colletableItems = new ArrayList<>();


    public Game(GameMood gameMood, Account accountOne, Account accountTwo) {
        this.gameMood = gameMood;
        int randomIndex = (new Random(2)).nextInt();
        this.players[randomIndex] = new HumanPlayer(accountOne, accountOne.getCollection().getMainDeck());
        this.players[(randomIndex + 1) % 2] = new HumanPlayer(accountTwo, accountTwo.getCollection().getMainDeck());
        initialiseGameFields();
    }

    public Game(GameMood gameMood, Account account, Deck aIDeck) {//todo to not getting ai deck
        this.gameMood = gameMood;
        int randomIndex = (new Random(2)).nextInt();
        players[randomIndex] = new HumanPlayer(account, account.getCollection().getMainDeck());
        players[(randomIndex + 1) % 2] = new AIPlayer(aIDeck);
        initialiseGameFields();
    }

    private void initialiseGameFields() {
        turn = 0;
        getActivePlayer().mana = Constant.GameConstants.getTurnMana(turn);
        getActivePlayer().ableToReplaceCard = true;
        board.getCell(Constant.GameConstants.boardRow / 2 + 1, 0)
                .setWarrior(players[0].getWarriors().get(0));
        board.getCell(Constant.GameConstants.boardRow / 2 + 1, Constant.GameConstants.boardColumn - 1)
                .setWarrior(players[1].getWarriors().get(0));
        initialisePlayerHand(players[0]);
        initialisePlayerHand(players[1]);

        useUsableItem(players[0].getMainDeck().getItem());
        useUsableItem(players[1].getMainDeck().getItem());

        startTurn();
//        timer.start();
    }

    private void useUsableItem(Spell spell){
        for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
            for (int j = 0; j < Constant.GameConstants.boardColumn; j++) {
                if(spell.apply(board.getCell(i,j))) {
                    return;
                }
            }
        }
    }

    private void initialisePlayerHand(Player player) {
        Random random = new Random(player.getMainDeck().getCardIDs().size());
        for (Map.Entry<Integer, Card> entry : player.getHand().entrySet()) {
            entry.setValue(Card.getAllCards().get(player.getMainDeck().getCardIDs().get(random.nextInt())));
        }
    }

    public Player getActivePlayer() {
        return players[turn % 2];
    }

    public Player getWarriorsPlayer(Warrior warrior) {
        if (players[0].getWarriors().contains(warrior)) {
            return players[0];
        }
        return players[1];
    }

    public Player getOtherPlayer(Player player) {
        return player != players[0] ? players[0] : players[1];
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

    public GameMood getGameMood() {
        return gameMood;
    }

    public void iterateAllTriggersCheck(GameState gameState) {
        board.iterateBoardTriggers(gameState);
        iteratePlayerTriggers(players[0], gameState);
        iteratePlayerTriggers(players[1], gameState);
        //apply buffer here if not in kill mod
    }

    private void iteratePlayerTriggers(Player player, GameState gameState) {
        for (Warrior warrior : player.getWarriors()) {
            for (Trigger trigger : warrior.getTriggers()) {
                trigger.check(gameState, warrior);
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
            for (Iterator<Trigger> iterator = warrior.getTriggers().iterator(); iterator.hasNext(); ) {
                Trigger trigger = iterator.next();
                if (trigger.duration > 0) {
                    trigger.duration--;
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
            for (Iterator<Effect> iterator = warrior.getEffects().iterator(); iterator.hasNext(); ) {
                Effect effect = iterator.next();
                if (effect.duration > 0) {
                    effect.duration--;
                    if (effect.duration == 0) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void addNewCardToPlayerHand(Player player){
        for (Map.Entry<Integer, Card> entry : player.getHand().entrySet()) {
            if (entry.getValue() == null) {
                int randomIndex = (new Random(player.getMainDeck().getCardIDs().size())).nextInt();
                entry.setValue(Card.getAllCards().get(player.getMainDeck().getCardIDs().get(randomIndex)).deepCopy());
                break;
            }
        }
    }

    private void checkGameEndAndThenKillAllDiedWarriors() {
        //kill mod: ON
        gameMood.checkGameEnd(this);//todo
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
            checkGameEndAndThenKillAllDiedWarriors();
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
        checkGameEndAndThenKillAllDiedWarriors();

    }

    public void attack(Cell attackerCell, Cell defenderCell) {
        ArrayList<Warrior> activePlayerWarriors = getActivePlayer().getWarriors();
        if (activePlayerWarriors.contains(attackerCell.getWarrior()) &&
                !activePlayerWarriors.contains(defenderCell.getWarrior())) {
            Attack.doIt(attackerCell, defenderCell);
            checkGameEndAndThenKillAllDiedWarriors();
        }
    }

    public void replaceCard(int handMapKey) {
        if (getActivePlayer().getHand().get(handMapKey) != null) {
            ReplaceCard.doIt(this, handMapKey);
            checkGameEndAndThenKillAllDiedWarriors();
        }
    }

    public void useCard(int handMapKey, Cell cell) {
        if (getActivePlayer().getHand().get(handMapKey) != null) {
            UseCard.doIt(handMapKey, cell);
            checkGameEndAndThenKillAllDiedWarriors();
        }
    }

    public void endTurn() throws IOException, ClassNotFoundException {
        EndTurn.doIt(this);
        checkGameEndAndThenKillAllDiedWarriors();
        startTurn();
    }

    private void startTurn() {
        StartTurn.doIt(this);
        checkGameEndAndThenKillAllDiedWarriors();
    }
}

class Selectable {
    private ArrayList<Cell> warriorsCell = new ArrayList<>();
    public Integer cardHandIndex;
    public boolean specialPowerIsSelected;
    public Spell collectableItem;

    public void seletWarrior(Cell cell) {
        Game game = cell.getBoard().getGame();
        if (game.getActivePlayer().getWarriors().contains(cell.getWarrior())) {
            cardHandIndex = null;
            specialPowerIsSelected = false;
            collectableItem = null;
            warriorsCell.add(cell);
        } else {
            //todo
        }
    }

    public void selectCard(Game game, int cardHandIndex) {
        if (game.getActivePlayer().getHand().get(cardHandIndex) != null) {
            warriorsCell = new ArrayList<>();
            specialPowerIsSelected = false;
            collectableItem = null;
            this.cardHandIndex = cardHandIndex;
        } else {
            //todo
        }
    }

    public void selectSpecialPower(Game game) {
        warriorsCell = new ArrayList<>();
        cardHandIndex = null;
        collectableItem = null;
        specialPowerIsSelected = true;
    }

    public void selectColletableItem(Spell collectableItem) {
        this.collectableItem = collectableItem;
    }

    public void deselectAll() {
        warriorsCell = new ArrayList<>();
        cardHandIndex = null;
        specialPowerIsSelected = false;
        collectableItem = null;
    }
}