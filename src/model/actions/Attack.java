package model.actions;

import model.Cell;
import model.Game;
import model.cards.Warrior;
import model.effects.*;
import model.gamestate.AttackState;

import java.util.stream.Stream;

public class Attack {
    public static boolean doIt(Cell attackerCell, Cell defenderCell) {
        Game game = attackerCell.getBoard().getGame();
        Warrior attacker = attackerCell.getWarrior();
        Warrior defender = defenderCell.getWarrior();
        int jumperManhattanDistance = game.getBoard().getJumperManhattanDistance(attackerCell, defenderCell);
        if (checkWarriorsEffectsForAttack(game, attackerCell, defenderCell, jumperManhattanDistance)) {
            AttackState attackState = new AttackState(attacker, defender, attacker.getAp());
            game.iterateAllTriggersCheck(attackState);
            if (!attackState.canceled) {
                attacker.getEffects().add(new Attacked());
                defender.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -1 * attackState.ap));
                attackState.pending = false;
                game.iterateAllTriggersCheck(attackState);
            }
            return !attackState.canceled;
        }
        return false;
    }

    static boolean checkWarriorsEffectsForAttack
            (Game game, Cell attackerCell, Cell defenderCell, int JumperManhattanDistance) {
        Warrior attacker = attackerCell.getWarrior();
        if (game.getBoard().getEightAdjacent(attackerCell).contains(defenderCell)) {
            if (attacker.getEffects().stream().anyMatch(effect -> effect instanceof Melee)) {
                return true;
            }
        }
        else if (attacker.getEffects().stream().anyMatch(effect -> effect instanceof Ranged)) {
            return attacker.getEffects().stream().filter(effect -> effect instanceof Ranged)
                    .anyMatch( effect -> ((Ranged)effect).getRange() <= JumperManhattanDistance);
        }
        return false;
    }
}
