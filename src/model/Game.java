package model;

import model.actions.*;
import model.actions.Killer;
import model.cards.*;
import model.effects.Dispelablity;
import model.effects.Effect;
import model.exceptions.NotEnoughConditions;
import model.gamestate.*;
import model.gamemodes.GameMode;
import model.player.AIPlayer;
import model.player.HumanPlayer;
import model.player.Player;
import model.triggers.CollectibleMine;
import model.triggers.Trigger;
import view.fxmlControllers.ArenaController;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Game implements Serializable {
    GameMode gameMode;
    public int turn;
    Player[] players = new Player[2];
    private Board board = new Board(this);
    //    public Timer timer = new Timer(Constant.GameConstants.turnTime, ignored -> endTurn());
    private SelectionManager selectionManager = new SelectionManager(this);
    public int prize;

    public Game(GameMode gameMode, Account accountOne, Account accountTwo) {
        this.gameMode = gameMode;
        int randomIndex = (new Random(System.currentTimeMillis())).nextInt(2);
        this.players[randomIndex] = new HumanPlayer(accountOne, accountOne.getCollection().getMainDeck());
        this.players[(randomIndex + 1) % 2] = new HumanPlayer(accountTwo, accountTwo.getCollection().getMainDeck());
    }

    public Game(GameMode gameMode, Account account, Deck aIDeck) {
        this.gameMode = gameMode;
        int randomIndex = /*(new Random(System.currentTimeMillis())).nextInt(2)*/0;//todo test only

        players[randomIndex] = new HumanPlayer(account, account.getCollection().getMainDeck());
        players[(randomIndex + 1) % 2] = new AIPlayer(aIDeck);
    }

    public void initialiseGameFields() {
        {
            turn = 0;
            getActivePlayer().setMana(Constant.GameConstants.getTurnMana(turn));
            getActivePlayer().ableToReplaceCard = true;
        }
        {
            players[0].getWarriors().add(players[0].getMainDeck().getHero().deepCopy());
            players[1].getWarriors().add(players[1].getMainDeck().getHero().deepCopy());
            putWarriorInCell(board.getCell(Constant.GameConstants.boardRow / 2,
                    0), players[0].getWarriors().get(0));
            putWarriorInCell(board.getCell(Constant.GameConstants.boardRow / 2,
                    Constant.GameConstants.boardColumn - 1), players[1].getWarriors().get(0));
            ArenaController.ac.put(Constant.GameConstants.boardRow / 2,0,players[0].getWarriors().get(0).getName());
            ArenaController.ac.put(Constant.GameConstants.boardRow / 2,Constant.GameConstants.boardColumn - 1,players[1].getWarriors().get(0).getName());
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
        {
            CollectibleMine c1 = new CollectibleMine(-1, Dispelablity.UNDISPELLABLE, CardFactory.getAllBuiltItems().get(7).deepCopy());
            board.getCell(2, 2).addTrigger(c1);
        }
        startTurn();
//        timer.start();
    }

    private void useUsableItem(Spell spell) {
        for (int i = 0; i < Constant.GameConstants.boardRow; i++) {
            for (int j = 0; j < Constant.GameConstants.boardColumn; j++) {
                try {
                    spell.apply(board.getCell(i, j));
                    return;
                } catch (NotEnoughConditions notEnoughConditions) {
                    //no problem we're brute forcing!
                }
            }
        }

    }

    private void initialisePlayerHand(Player player) {
        Random random = new Random(System.currentTimeMillis());
        for (Map.Entry<Integer, Card> entry : player.getHand().entrySet()) {
            entry.setValue(Card.getAllCards().get(player.getMainDeck().getCardIDs()
                    .get(random.nextInt(player.getMainDeck().getCardIDs().size()))).deepCopy());
        }
    }

    private void putWarriorInCell(Cell cell, Warrior warrior) {
        cell.setWarrior(warrior);
        warrior.setCell(cell);
    }

    public Player getActivePlayer() {
        return players[turn % 2];
    }

    public int getActivePlayerIndex() {
        return turn % 2;
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

    public SelectionManager getSelectionManager() {
        return selectionManager;
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
    }

    private void iteratePlayerTriggers(Player player, GameState gameState) {
        for (Warrior warrior : player.getWarriors()) {
            UUID id = UUID.randomUUID();
            warrior.setLock(id + "pti", true);
            for (Trigger trigger : warrior.getTriggers()) {
                trigger.check(gameState, warrior);
            }
            warrior.setLock(id + "pti", false);
        }
    }

    public void iterateAndExpireAllTriggers() {
        iterateAndExpirePlayerTriggers(players[0]);
        iterateAndExpirePlayerTriggers(players[1]);
        board.iterateAndExpireBoardTriggers();
    }

    private void iterateAndExpirePlayerTriggers(Player player) {
        for (Warrior warrior : player.getWarriors()) {
            UUID id = UUID.randomUUID();
            warrior.setLock(id + "pte", true);
            for (Trigger trigger : warrior.getTriggers()) {
                if (trigger.duration > 0) {
                    trigger.duration--;
                    if (trigger.duration == 0) {
                        warrior.removeTrigger(trigger);
                    }
                }
            }
            warrior.setLock(id + "pte", false);
        }
    }

    public void iterateAndExpireAllEffects() {
        iterateAndExpirePlayerEffects(players[0]);
        iterateAndExpirePlayerEffects(players[1]);
        board.iterateAndExpireBoardEffects();
    }

    private void iterateAndExpirePlayerEffects(Player player) {
        for (Warrior warrior : player.getWarriors()) {
            UUID id = UUID.randomUUID();
            warrior.setLock(id + "pee", true);
            for (Effect effect : warrior.getEffects()) {
                if (effect.duration > 0) {
                    effect.duration--;
                    if (effect.duration == 0) {
                        warrior.removeEffect(effect);
                    }
                }
            }
            warrior.setLock(id + "pee", false);
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
        killPlayerDiedWarriors(players[0]);
        killPlayerDiedWarriors(players[1]);
        gameMode.checkGameEnd(this);
        if (gameMode.winner != null) {
            endGame();
        }
    }

    private void killPlayerDiedWarriors(Player player) {
        for (int i = 0; i < player.getWarriors().size(); i++) {
            if (player.getWarriors().get(i).getHp() <= 0) {
                Killer.kill(player.getWarriors().get(i));
            }
        }
    }

    public void endGame() {
        //add game to history
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                ((HumanPlayer) player).getAccount().putGameInHistory(getOtherPlayer(player).username, gameMode.winner.equals(player));
            }
        }

        //give prize
        if (gameMode.winner instanceof HumanPlayer) {
            ((HumanPlayer) gameMode.winner).getAccount().derrick += prize;
        }

        //and update the graphics
        ArenaController.ac.endGame(gameMode.winner);
    }

    public void move(Cell originCell, Cell targetCell) throws NotEnoughConditions {
//        if (getActivePlayer().getWarriors().contains(originCell.getWarrior()) && targetCell.getWarrior() == null) {
        try {
            Move.doIt(originCell, targetCell);
            ArenaController.ac.move(originCell.getRow(), originCell.getColumn(), targetCell.getRow(), targetCell.getColumn());
        } finally {
            checkGameEndAndThenKillAllDiedWarriors();
        }
//        } else {
//            throw new NotEnoughConditions("Move more carefully!");
//        }
    }

    public void comboAttack(ArrayList<Cell> attackersCell, Cell defenderCell) throws NotEnoughConditions {
//        if (defenderCell.getWarrior() == null) return false;
//        for (Cell cell : attackersCell) {
//            if (cell.getWarrior() == null) {
//                return false;
//            }
//        }
//        for (int i = 0; i < attackersCell.size() - 1; i++) {
//            if (getWarriorsPlayer(attackersCell.get(i).getWarrior()) !=
//                    getWarriorsPlayer(attackersCell.get(i + 1).getWarrior())) {
//                return false;
//            }
//        }
//        if (defenderCell.getWarrior() == attackersCell.get(0).getWarrior()) {
//            return false;
//        }
        try {
            ComboAttack.doIt(attackersCell, defenderCell);

            for (Cell cell : attackersCell) {
                ArenaController.ac.attack(cell.getRow(), cell.getColumn(), defenderCell.getRow(), defenderCell.getColumn());
            }
        } finally {
            checkGameEndAndThenKillAllDiedWarriors();
        }
    }

    public void attack(Cell attackerCell, Cell defenderCell) throws NotEnoughConditions {
//        ArrayList<Warrior> activePlayerWarriors = getActivePlayer().getWarriors();
//        if (defenderCell.getWarrior() != null && activePlayerWarriors.contains(attackerCell.getWarrior()) &&
//                !activePlayerWarriors.contains(defenderCell.getWarrior())) {
        try {
            Attack.doIt(attackerCell, defenderCell, false);
            ArenaController.ac.attack(attackerCell.getRow(), attackerCell.getColumn(), defenderCell.getRow(), defenderCell.getColumn());
        } finally {
            checkGameEndAndThenKillAllDiedWarriors();
        }
//        }
    }

    public void replaceCard(int handMapKey) throws NotEnoughConditions {
//        if (getActivePlayer().getHand().get(handMapKey) != null) {
        try {
            ReplaceCard.doIt(this, handMapKey);
        } finally {
            checkGameEndAndThenKillAllDiedWarriors();
        }
//        }
    }

    public void useCard(int handMapKey, Cell cell) throws NotEnoughConditions {
//        if (getActivePlayer().getHand().get(handMapKey) != null) {
        try {
            UseCard.useCard(handMapKey, cell);
            ArenaController.ac.useCard(handMapKey);
        } finally {
            checkGameEndAndThenKillAllDiedWarriors();
        }
//        }
    }

    public void useSpecialPower(Cell cell) throws NotEnoughConditions {
        if (getActivePlayer().getPlayerHero().getPower().coolDownRemaining == 0) {
            try {
                UseCard.useHeroPower(getActivePlayer().getPlayerHero().getPower(), cell);

                Cell heroCell = getActivePlayer().getPlayerHero().getCell();
                ArenaController.ac.cast(heroCell.getRow(), heroCell.getColumn());
            } finally {
                checkGameEndAndThenKillAllDiedWarriors();
            }
        } else {
            throw new NotEnoughConditions("It's on cooldown");
        }
    }

    public void useCollectible(String spellName, Cell cell) throws NotEnoughConditions {
        try {
            Spell spell=null;
            int index = -1;

            ArrayList<Spell> collectibleItems = getActivePlayer().getCollectibleItems();
            for (int i = 0; i < collectibleItems.size(); i++) {
                Spell item = collectibleItems.get(i);
                if(item.getName().equals(spellName)){
                    spell = item;
                    index = i;
                    break;
                }
            }
            UseCard.useCollectible(spell, cell);
            ArenaController.ac.useCollectibleItem(index,getActivePlayerIndex()+1);
        } finally {
            checkGameEndAndThenKillAllDiedWarriors();
        }
    }

    public void endTurn() {
        EndTurn.doIt(this);
        checkGameEndAndThenKillAllDiedWarriors();

        if (gameMode.winner == null)//why a condition? because if the game end because of endTurn the flow returns here and starts a new turn.
            startTurn();
    }

    private void startTurn() {
        StartTurn.doIt(this);
        checkGameEndAndThenKillAllDiedWarriors();

        //todo this part updates the ui for the new player. in the networking this should implemented somewhere else.
        ArenaController.ac.setCoolDown(getActivePlayer().getPlayerHero().getPower().coolDownRemaining, getPlayerNumber(getActivePlayer()) + 1);
        ArenaController.ac.setActivePlayer(getPlayerNumber(getActivePlayer()) + 1);


        HashMap<Integer, String> handMap = (HashMap<Integer, String>) getActivePlayer().getHand().entrySet().stream()
                .filter(integerCardEntry -> integerCardEntry.getValue() != null)
                .collect(Collectors.toMap((Map.Entry<Integer, Card> o) -> o.getKey() + 1
                        , (Map.Entry<Integer, Card> o) -> o.getValue().getName()));
        handMap.put(0, getActivePlayer().getNextCard().getName());
        ArenaController.ac.buildPlayerHand(handMap, getPlayerNumber(getActivePlayer()) + 1);

        //todo this is an unwanted recurse sol: a "your turn" field in player that ai waits on
        if (getActivePlayer() instanceof AIPlayer) {
            ((AIPlayer) getActivePlayer()).doSomething();
        }
    }
}