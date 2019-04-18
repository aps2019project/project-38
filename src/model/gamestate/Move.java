package model.gamestate;

import model.Cell;
import model.cards.warriors.Warrior;

public class Move extends GameState {
    private Warrior warrior;
    private Cell originCell;
    private Cell destinationCell;

    public Move(Warrior warrior, Cell originCell, Cell destinationCell) {
        this.warrior = warrior;
        this.originCell = originCell;
        this.destinationCell = destinationCell;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public Cell getOriginCell() {
        return originCell;
    }

    public Cell getDestinationCell() {
        return destinationCell;
    }
}
