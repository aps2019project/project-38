package model.actions;

import model.Cell;
import model.Constant;
import model.Game;
import model.cards.Warrior;
import model.effects.Attacked;
import model.effects.Flying;
import model.effects.Moved;
import model.gamestate.MoveState;

public class Move {
    public static void doIt(Cell originCell, Cell targetCell) {
        Game game = originCell.getBoard().getGame();
        Warrior warrior = originCell.getWarrior();
        int manhattanDistance = game.getBoard().getManhattanDistance(originCell, targetCell);
        if (checkWarriorEffectsForMove(warrior, manhattanDistance)) {
            MoveState moveState = new MoveState(warrior, originCell, targetCell);
            game.iterateAllTriggersCheck(moveState);
            if (!moveState.canceled) {
                originCell.setWarrior(null);
                targetCell.setWarrior(warrior);
                warrior.setCell(targetCell);
                warrior.getEffects().add(new Moved());
                moveState.pending = false;
                game.iterateAllTriggersCheck(moveState);
            }
        }
    }

    private static boolean checkWarriorEffectsForMove (Warrior warrior, int manhattanDistance) {
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
}
