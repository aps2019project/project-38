package model.actions.gameaction;

import model.Cell;
import model.Game;
import model.cards.warriors.Warrior;
import model.effects.Effect;
import model.effects.Melee;
import model.effects.Ranged;

import java.util.ArrayList;
import java.util.stream.Stream;

public abstract class Attack {
    public static void doIt(Game game, Cell attackerCell, Cell defenderCell) {
        try { //for catching null pointer Exception
            Warrior attacker = attackerCell.getWarrior();
            Warrior defender = defenderCell.getWarrior();
            ArrayList<Warrior> warriors = game.getActivePlayer().getWarriors();
            int jumperManhattanDistance = game.getBoard().getJumperManhattanDistance(attackerCell, defenderCell);
            if (warriors.contains(attacker) && !warriors.contains(defender) &&
                    checkWarriorsEffectsForAttack(game, attackerCell, defenderCell, jumperManhattanDistance)) {
            }
        }
        catch (Exception ignored) {}
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
