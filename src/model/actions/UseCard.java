package model.actions;

import model.Cell;
import model.Game;
import model.cards.Card;
import model.cards.HeroPower;
import model.cards.Spell;
import model.cards.Warrior;
import model.gamestate.GameState;
import model.gamestate.PutMinionState;
import model.gamestate.UseSpellState;

public class UseCard {
    public static void doIt(int handMapKey, Cell cell) {
        Game game = cell.getBoard().getGame();
        Card card = game.getActivePlayer().getHand().get(handMapKey);
        GameState gameState;
        if (card instanceof Spell) {
            UseSpellState useSpellState = new UseSpellState(cell, (Spell) card);
            game.iterateAllTriggersCheck(useSpellState);
            if (useSpellState.canceled) return;
            useSpellState.pending = false;
            gameState = useSpellState;
        } else {
            if (cell.getWarrior() != null) return;
            gameState = new PutMinionState((Warrior) card);
            cell.setWarrior((Warrior) card);
        }
        if (game.getActivePlayer().mana >= card.getRequiredMana()) {
            if (card.apply(cell)) {
                game.getActivePlayer().mana -= card.getRequiredMana();
                game.getActivePlayer().getHand().put(handMapKey, null);
                game.getActivePlayer().getUsedCards().add(card);
                game.iterateAllTriggersCheck(gameState);
            }
        }
    }

    public static void doIt(HeroPower heroPower, Cell cell) {
        Game game = cell.getBoard().getGame();
        UseSpellState useSpellState = new UseSpellState(cell, heroPower);
        game.iterateAllTriggersCheck(useSpellState);
        if (useSpellState.canceled) return;
        useSpellState.pending = false;
        if (game.getActivePlayer().mana >= heroPower.getRequiredMana()) {
            if (heroPower.apply(cell)) {
                game.getActivePlayer().mana -= heroPower.getRequiredMana();
                game.iterateAllTriggersCheck(useSpellState);
            }
        }
    }
}
