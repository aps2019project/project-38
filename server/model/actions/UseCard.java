package model.actions;

import model.Board;
import model.Cell;
import model.Game;
import model.cards.Card;
import model.cards.HeroPower;
import model.cards.Spell;
import model.cards.Warrior;
import model.exceptions.NotEnoughConditions;
import model.gamestate.GameState;
import model.gamestate.PutMinionState;
import model.gamestate.UseSpellState;

public class UseCard {
    public static void useCard(int handMapKey, Cell cell) throws NotEnoughConditions {//todo badana
        Game game = cell.getBoard().getGame();
        Card card = game.getActivePlayer().getHand().get(handMapKey);
        GameState gameState;
        if (card instanceof Spell) {
            UseSpellState useSpellState = new UseSpellState(cell, (Spell) card);
            game.iterateAllTriggersCheck(useSpellState);
            if (useSpellState.canceled) {
                throw new NotEnoughConditions("Something prevented you from using your card");
            }
            useSpellState.pending = false;
            gameState = useSpellState;
        } else {
            Warrior warrior = (Warrior) card;
            if (cell.getWarrior() != null) {
                throw new NotEnoughConditions("You can't put a minion on another minion");
            }
            isOnTerritory(game.getBoard(), cell);

            gameState = new PutMinionState(warrior);
        }
        if (game.getActivePlayer().getMana()>= card.getRequiredMana()) {
            card.apply(cell);

            if (gameState instanceof PutMinionState) {
//                ((PutMinionState) gameState).getWarrior().getEffects().add(new Attacked());todo for test only
            }
            game.getActivePlayer().addMana(-card.getRequiredMana());
            game.getActivePlayer().getHand().put(handMapKey, null);
            game.getActivePlayer().getUsedCards().add(card);

            ArenaController.ac.transferToGraveYard(card,game.getActivePlayerIndex()+1);

            game.iterateAllTriggersCheck(gameState);

            System.err.println(card.getName() + " (" + cell.getRow() + "," + cell.getColumn() + ")");
        } else {
            throw new NotEnoughConditions("Not enough mana");
        }
    }

    static void isOnTerritory(Board board, Cell cell) throws NotEnoughConditions {
        if (board.getEightAdjacent(cell).stream().noneMatch(cell1 -> board.getGame().getActivePlayer().getWarriors().contains(cell1.getWarrior()))) {
//            throw new NotEnoughConditions("You should put it near your existing minions"); todo for test only
        }
    }

    public static void useHeroPower(HeroPower heroPower, Cell cell) throws NotEnoughConditions {
        Game game = cell.getBoard().getGame();
        UseSpellState useSpellState = new UseSpellState(cell, heroPower);
        game.iterateAllTriggersCheck(useSpellState);
        if (useSpellState.canceled) {
            throw new NotEnoughConditions("Something prevented you from using your hero's special power!");
        }
        useSpellState.pending = false;
        if (game.getActivePlayer().getMana()>= heroPower.getRequiredMana()) {
            heroPower.apply(cell);
            game.getActivePlayer().addMana(-heroPower.getRequiredMana());
            game.iterateAllTriggersCheck(useSpellState);
        } else {
            throw new NotEnoughConditions("Not enough mana");
        }
    }

    public static void useCollectible(Spell spell, Cell cell) throws NotEnoughConditions {
        Game game = cell.getBoard().getGame();
        UseSpellState useSpellState = new UseSpellState(cell, spell);
        game.iterateAllTriggersCheck(useSpellState);
        if (useSpellState.canceled) {
            throw new NotEnoughConditions("Something prevented you from using collectible");
        }
        useSpellState.pending = false;

        spell.apply(cell);
        game.getActivePlayer().getCollectibleItems().remove(spell);
        game.iterateAllTriggersCheck(useSpellState);
    }
}
