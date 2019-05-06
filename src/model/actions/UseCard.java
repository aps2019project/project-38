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
    public static boolean useCard(int handMapKey, Cell cell) {
        boolean didSth = false;
        Game game = cell.getBoard().getGame();
        Card card = game.getActivePlayer().getHand().get(handMapKey);
        GameState gameState;
        if (card instanceof Spell) {
            UseSpellState useSpellState = new UseSpellState(cell, (Spell) card);
            game.iterateAllTriggersCheck(useSpellState);
            if (useSpellState.canceled) return false;
            useSpellState.pending = false;
            gameState = useSpellState;
        } else {
            if (cell.getWarrior() != null) return false;
            gameState = new PutMinionState((Warrior) card);
        }
        if (game.getActivePlayer().mana >= card.getRequiredMana()) {
            if (card.apply(cell)) {
                game.getActivePlayer().mana -= card.getRequiredMana();
                game.getActivePlayer().getHand().put(handMapKey, null);
                game.getActivePlayer().getUsedCards().add(card);
                game.iterateAllTriggersCheck(gameState);
                didSth = true;
            }
        }
        return didSth;
    }

    public static void useHeroPower(HeroPower heroPower, Cell cell) {
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

    public static void useCollectible(Spell spell, Cell cell) {
        Game game = cell.getBoard().getGame();
        UseSpellState useSpellState = new UseSpellState(cell, spell);
        game.iterateAllTriggersCheck(useSpellState);
        if (useSpellState.canceled) return;
        useSpellState.pending = false;
        if (spell.apply(cell)) {
            game.getColletableItems().remove(spell);
            game.iterateAllTriggersCheck(useSpellState);
        }
    }
}
