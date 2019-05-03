package model.targets.triggertargets;

import model.Board;
import model.Cell;
import model.Game;
import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class WithinDistanceGetter implements TriggerTarget {
    private boolean friendMod;
    private int distance;

    public WithinDistanceGetter(boolean friendMod, int distance) {
        this.friendMod = friendMod;
        this.distance = distance;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior)triggerOwner;
        Board board = warrior.getCell().getBoard();
        Game game = board.getGame();
        return board.getCellWithinDistance(warrior.getCell(),distance).stream().map(Cell::getWarrior)
                .filter(Objects::nonNull)
                .filter(warrior1 -> friendMod == game.getWarriorsPlayer(warrior).equals(game.getWarriorsPlayer(warrior1)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
