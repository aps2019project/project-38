package model.actions.gameaction;

import model.Cell;
import model.Game;
import model.cards.warriors.Warrior;
import model.effects.*;
import model.gamestate.AttackState;

import java.util.stream.Stream;

public abstract class Attack {
    public static void doIt(Cell attackerCell, Cell defenderCell) {
        Game game = attackerCell.getBoard().getGame();
        Warrior attacker = attackerCell.getWarrior();
        Warrior defender = defenderCell.getWarrior();
        int jumperManhattanDistance = game.getBoard().getJumperManhattanDistance(attackerCell, defenderCell);
        if (checkWarriorsEffectsForAttack(game, attackerCell, defenderCell, jumperManhattanDistance)) {
            AttackState attackState = new AttackState(attacker, defender, attacker.getAp());
            game.iterateAllTriggers(attackState);
            if (!attackState.canceled) {
                defender.getEffects().add(new HP(-1, Dispelablity.UNDISPELLABLE, -1 * attackState.ap));
                attackState.pending = false;
                game.iterateAllTriggers(attackState);
            }
        }
    }

    private static boolean checkWarriorsEffectsForAttack
            (Game game, Cell attackerCell, Cell defenderCell, int JumperManhattanDistance) {
        Warrior attacker = attackerCell.getWarrior();
        Stream<Effect> stream = attacker.getEffects().stream();
        if (game.getBoard().getEightAdjacent(attackerCell).contains(defenderCell)) {
            if (stream.anyMatch(effect -> effect instanceof Melee)) {
                return true;
            }
        }
        else if (stream.anyMatch(effect -> effect instanceof Ranged)) {
            return stream.filter(effect -> effect instanceof Ranged)
                    .anyMatch( effect -> ((Ranged)effect).getRange() <= JumperManhattanDistance);
        }
        return false;
    }
}
