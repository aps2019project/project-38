package model.actions;

import model.Cell;
import model.Constant;
import model.cards.Warrior;
import model.effects.Attacked;
import model.effects.Flying;
import model.effects.Moved;
import model.exceptions.NotEnoughConditions;
import model.gamestate.MoveState;

public class Move {
    public static void doIt(Cell originCell, Cell targetCell) throws NotEnoughConditions {
        Game game = originCell.getBoard().getGame();
        Warrior warrior = originCell.getWarrior();
        int manhattanDistance = game.getBoard().getManhattanDistance(originCell, targetCell);
        try {
            checkWarriorEffectsForMove(warrior, manhattanDistance);

            MoveState moveState = new MoveState(warrior, originCell, targetCell);
            game.iterateAllTriggersCheck(moveState);
            if (!moveState.canceled) {
                originCell.setWarrior(null);
                targetCell.setWarrior(warrior);
                warrior.setCell(targetCell);
                warrior.addEffect(new Moved());
                moveState.pending = false;
                game.iterateAllTriggersCheck(moveState);
                System.err.println("(" + originCell.getRow() + "," + originCell.getColumn() + ")(" + targetCell.getRow() + "," + targetCell.getColumn() + ")");
            }else {
                throw new NotEnoughConditions("Something prevented your move!");
            }
        } catch (NotEnoughConditions notEnoughConditions) {
            throw notEnoughConditions;
        }
    }

    private static void checkWarriorEffectsForMove(Warrior warrior, int manhattanDistance) throws NotEnoughConditions {
        boolean result = warrior.getEffects().stream().noneMatch
                (effect -> effect instanceof Attacked || effect instanceof Moved);
        if (result) {
            if (manhattanDistance <= Constant.WarriorConstants.maxMove) {
//                return true;
            } else {
                if (warrior.getEffects().stream().anyMatch(a -> a instanceof Flying)) {
//                    return true;
                } else {
                    throw new NotEnoughConditions("Target cell is out of move range");
                }
            }
        } else {
            throw new NotEnoughConditions("This unit has either attacked or moved already");
//            return false;
        }
    }
}
