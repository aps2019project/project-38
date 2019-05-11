package model;

import model.actions.*;
import model.actions.Killer;
import model.cards.Card;
import model.cards.HeroPower;
import model.cards.Spell;
import model.cards.Warrior;
import model.effects.Effect;
import model.gamestate.*;
import model.gamemodes.GameMode;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import model.triggers.Trigger;

import java.io.Serializable;
import java.util.*;


public class Game implements Serializable{
    GameMode gameMode;
    public int turn;
    Player[] players = new Player[2];
    private Board board = new Board(this);
    //    public Timer timer = new Timer(Constant.GameConstants.turnTime, ignored -> endTurn());
    private ArrayList<Spell> collectibleItems = new ArrayList<>();
    private Selectable selectedThings = new Selectable();
    public int prize;

//    public HashMap<Trigger,QualityHaver> triggAddBuffer = new HashMap<>();
//    public HashMap<Effect,QualityHaver> effAddBuffer = new HashMap<>();
//    public HashMap<Effect,QualityHaver> effRemoveBuffer = new HashMap<>();
//    public HashMap<Trigger,QualityHaver> triggRemoveBuffer = new HashMap<>();
//    private boolean killMod=false;

    public Game(GameMode gameMode, Account accountOne, Account accountTwo) {
        this.gameMode = gameMode;
        int randomIndex = (new Random(System.currentTimeMillis())).nextInt(2);
        this.players[randomIndex] = new HumanPlayer(accountOne, accountOne.getCollection().getMainDeck());
        this.players[(randomIndex + 1) % 2] = new HumanPlayer(accountTwo, accountTwo.getCollection().getMainDeck());
        initialiseGameFields();
    }

    public Game(GameMode gameMode, Account account, Deck aIDeck) {
        this.gameMode = gameMode;
        int randomIndex = (new Random(System.currentTimeMillis())).nextInt(2);
        players[randomIndex] = new HumanPlayer(account, account.getCollection().getMainDeck());
        players[(randomIndex + 1) % 2] = new AIPlayer(aIDeck);
        initialiseGameFields();
    }

    private void initialiseGameFields() {
        {
            turn = 0;
            getActivePlayer().mana = Constant.GameConstants.getTurnMana(turn);
            getActivePlayer().ableToReplaceCard = true;
        }
        {
            players[0].getWarriors().add(players[0].getMainDeck().getHero().deepCopy());
            players[1].getWarriors().add(players[1].getMainDeck().getHero().deepCopy());
            putWarriorInCell(board.getCell(Constant.GameConstants.boardRow / 2,
                    0), players[0].getWarriors().get(0));
            putWarriorInCell(board.getCell(Constant.GameConstants.boardRow / 2,
                    Constant.GameConstants.boardColumn - 1), players[1].getWarriors().get(0));
        }
        {
            initialisePlayerHand(players[0]);
            initialisePlayerHand(players[1]);
        }
        {
            turn = 0;
            useUsableItem(players[0].getMainDeck().getItem());
            turn = 1;
            useUsableItem(players[1].getMainDeck().getItem());
            turn = 0;
        }
        startTurn();
//        timer.start();
    }

    private void useUsableItem(Spell spell) {
        for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
            for (int j = 0; j < Constant.GameConstants.boardColumn; j++) {
                if (spell.apply(board.getCell(i, j))) {
                    return;
                }
            }
        }
    }

//    private void flushBuffers(){
//        for (Map.Entry<Trigger, QualityHaver> entry : triggAddBuffer.entrySet()) {
//            entry.getValue().getTriggers().add(entry.getKey());
//        }
//        for (Map.Entry<Effect, QualityHaver> entry : effAddBuffer.entrySet()) {
//            entry.getValue().getEffects().add(entry.getKey());
//        }
//        for (Map.Entry<Trigger, QualityHaver> entry : triggRemoveBuffer.entrySet()) {
//            entry.getValue().getTriggers().remove(entry.getKey());
//        }
//        for (Map.Entry<Effect, QualityHaver> entry : effRemoveBuffer.entrySet()) {
//            entry.getValue().getEffects().remove(entry.getKey());
//        }
//        triggAddBuffer = new HashMap<>();
//        effAddBuffer = new HashMap<>();
//        triggRemoveBuffer = new HashMap<>();
//        effRemoveBuffer = new HashMap<>();
//    }

    private void initialisePlayerHand(Player player) {
        Random random = new Random(System.currentTimeMillis());
        for (Map.Entry<Integer, Card> entry : player.getHand().entrySet()) {
            entry.setValue(Card.getAllCards().get(player.getMainDeck().getCardIDs()
                    .get(random.nextInt(player.getMainDeck().getCardIDs().size()))));
        }
    }

    private void putWarriorInCell(Cell cell, Warrior warrior) {
        cell.setWarrior(warrior);
        warrior.setCell(cell);
    }

    public Player getActivePlayer() {
        return players[turn % 2];
    }

    public int getPlayerNumber(Player player) {
        return player == players[0] ? 0 : 1;
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
        return getWarriorsPlayer(warrior) == players[0] ? players[1] : players[0];
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Selectable getSelectedThings() {
        return selectedThings;
    }

    public ArrayList<Spell> getCollectibleItems() {
        return collectibleItems;
    }

    public void decreaseSpecialPowerCoolDown() {
        HeroPower heroPower = getActivePlayer().getPlayerHero().getPower();
        if (heroPower != null) {
            if (heroPower.coolDownRemaining > 0) {
                heroPower.coolDownRemaining--;
            }
        }
    }

    public void iterateAllTriggersCheck(GameState gameState) {
        board.iterateBoardTriggers(gameState);
        iteratePlayerTriggers(players[0], gameState);
        iteratePlayerTriggers(players[1], gameState);
//        if(!killMod){
//            flushBuffers();
//        }
    }

    private void iteratePlayerTriggers(Player player, GameState gameState) {
        for (Warrior warrior : player.getWarriors()) {
            UUID id = UUID.randomUUID();
            warrior.setLock(id+"pti",true);
            for (Trigger trigger : warrior.getTriggers()) {
                trigger.check(gameState, warrior);
            }
            warrior.setLock(id+"pti",false);
        }
    }

    public void iterateAndExpireAllTriggers() {
        iterateAndExpirePlayerTriggers(players[0]);
        iterateAndExpirePlayerTriggers(players[1]);
        board.iterateAndExpireBoardTriggers();
    }

    private void iterateAndExpirePlayerTriggers(Player player) {
        for (Warrior warrior : player.getWarriors()) {
//            for (Iterator<Trigger> iterator = warrior.getTriggers().iterator(); iterator.hasNext(); ) {
//                Trigger trigger = iterator.next();
//                if (trigger.duration > 0) {
//                    trigger.duration--;
//                    if (trigger.duration == 0) {
//                        iterator.remove();
//                    }
//                }
//            }
            UUID id = UUID.randomUUID();
            warrior.setLock(id+"pte",true);
            for (Trigger trigger : warrior.getTriggers()) {
                if(trigger.duration > 0){
                    trigger.duration--;
                    if(trigger.duration==0){
                        warrior.removeTrigger(trigger);
                    }
                }
            }
            warrior.setLock(id+"pte",false);
        }
    }

    public void iterateAndExpireAllEffects() {
        iterateAndExpirePlayerEffects(players[0]);
        iterateAndExpirePlayerEffects(players[1]);
        board.iterateAndExpireBoardEffects();
    }

    private void iterateAndExpirePlayerEffects(Player player) {
        for (Warrior warrior : player.getWarriors()) {
//            for (Iterator<Effect> iterator = warrior.getEffects().iterator(); iterator.hasNext(); ) {
//                Effect effect = iterator.next();
//                if (effect.duration > 0) {
//                    effect.duration--;
//                    if (effect.duration == 0) {
//                        iterator.remove();
//                    }
//                }
//            }
            UUID id = UUID.randomUUID();
            warrior.setLock(id+"pee",true);
            for (Effect effect : warrior.getEffects()) {
                if(effect.duration>0){
                    effect.duration--;
                    if(effect.duration==0){
                        warrior.removeEffect(effect);
                    }
                }
            }
            warrior.setLock(id+"pee",false);
        }
    }

    public void addNextCardToPlayerHand(Player player) {
        for (Map.Entry<Integer, Card> entry : player.getHand().entrySet()) {
            if (entry.getValue() == null) {
                entry.setValue(player.getNextCard());
                player.initializeNextCard();
                break;
            }
        }
    }

    private void checkGameEndAndThenKillAllDiedWarriors() {
//        killMod = true;
        gameMode.checkGameEnd(this);//todo
        killPlayerDiedWarriors(players[0]);
        killPlayerDiedWarriors(players[1]);
//        killMod = false;
//        flushBuffers();
    }

    private void killPlayerDiedWarriors(Player player) {
        for (int i = 0; i < player.getWarriors().size(); i++) {
            if (player.getWarriors().get(i).getHp() <= 0) {
                Killer.kill(player.getWarriors().get(i));
            }
        }
    }

    public boolean move(Cell originCell, Cell targetCell) {
        boolean isDone = false;
        if (getActivePlayer().getWarriors().contains(originCell.getWarrior()) &&
                targetCell.getWarrior() == null) {
            isDone = Move.doIt(originCell, targetCell);
            checkGameEndAndThenKillAllDiedWarriors();
        }
        return isDone;
    }

    public boolean comboAttack(ArrayList<Cell> attackersCell, Cell defenderCell) {
        boolean isDone;
        if (defenderCell.getWarrior() == null) return false;
        for (Cell cell : attackersCell) {
            if (cell.getWarrior() == null) {
                return false;
            }
        }
        for (int i = 0; i < attackersCell.size() - 1; i++) {
            if (getWarriorsPlayer(attackersCell.get(i).getWarrior()) !=
                    getWarriorsPlayer(attackersCell.get(i + 1).getWarrior())) {
                return false;
            }
        }
        if (defenderCell.getWarrior() == attackersCell.get(0).getWarrior()) {
            return false;
        }
        isDone = ComboAttack.doIt(attackersCell, defenderCell);
        checkGameEndAndThenKillAllDiedWarriors();
        return isDone;
    }

    public boolean attack(Cell attackerCell, Cell defenderCell) {
        boolean isDone = false;
        ArrayList<Warrior> activePlayerWarriors = getActivePlayer().getWarriors();
        if (defenderCell.getWarrior() != null && activePlayerWarriors.contains(attackerCell.getWarrior()) &&
                !activePlayerWarriors.contains(defenderCell.getWarrior())) {
            isDone = Attack.doIt(attackerCell, defenderCell,false);
            checkGameEndAndThenKillAllDiedWarriors();
        }
        return isDone;
    }

    public boolean replaceCard(int handMapKey) {
        boolean isDone = false;
        if (getActivePlayer().getHand().get(handMapKey) != null) {
            isDone = ReplaceCard.doIt(this, handMapKey);
            checkGameEndAndThenKillAllDiedWarriors();
        }
        return isDone;
    }

    public boolean useCard(int handMapKey, Cell cell) {
        boolean isDone = false;
        if (getActivePlayer().getHand().get(handMapKey) != null) {
            isDone = UseCard.useCard(handMapKey, cell);
            checkGameEndAndThenKillAllDiedWarriors();
        }
        return isDone;
    }

    public boolean useSpecialPower(Cell cell) {
        boolean isDone = false;
        if (getActivePlayer().getPlayerHero().getPower().coolDownRemaining == 0) {
            isDone = UseCard.useHeroPower(getActivePlayer().getPlayerHero().getPower(), cell);
            checkGameEndAndThenKillAllDiedWarriors();
        }
        return isDone;
    }

    public boolean useCollectible(Spell spell, Cell cell) {
        boolean isDone;
        isDone = UseCard.useCollectible(spell, cell);
        checkGameEndAndThenKillAllDiedWarriors();
        return isDone;
    }

    public void endTurn() {
        EndTurn.doIt(this);
        checkGameEndAndThenKillAllDiedWarriors();
        startTurn();
    }

    private void startTurn() {
        StartTurn.doIt(this);
        checkGameEndAndThenKillAllDiedWarriors();
    }
}

