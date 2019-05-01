package model.actions.gameactions;

import model.Cell;
import model.Game;
import model.cards.Card;
import model.cards.spells.Spell;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;
import model.gamestate.PutMinionState;
import model.gamestate.UseSpellState;

public abstract class UseCard {
    public static void doIt(int handMapKey, Cell cell) {
        Game game = cell.getBoard().getGame();
        Card card = game.getActivePlayer().getHand().get(handMapKey);
        GameState gameState;
        if (card instanceof Spell) {
            UseSpellState useSpellState = new UseSpellState(cell, (Spell)card);
            game.iterateAllTriggers(useSpellState);
            if (useSpellState.canceled) return;
            useSpellState.pending = false;
            gameState = useSpellState;
        }
        else {
            if (cell.getWarrior() != null) return;
            gameState = new PutMinionState((Warrior)card);
        }
        if (game.getActivePlayer().mana >= card.getRequiredMana()) {
            game.getActivePlayer().mana -= card.getRequiredMana();
            game.getActivePlayer().getHand().put(handMapKey, null);
            card.apply(cell);
            game.iterateAllTriggers(gameState);
        }
    }
}
