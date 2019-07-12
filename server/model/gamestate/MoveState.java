package model.gamestate;

import model.Cell;
import model.cards.Warrior;

public class MoveState extends GameState {
    public boolean pending = true;
    private Warrior warrior;
    private Cell originCell;
    private Cell targetCell;
    public boolean canceled=false;

    public MoveState(Warrior warrior, Cell originCell, Cell targetCell) {
        this.warrior = warrior;
        this.originCell = originCell;
        this.targetCell = targetCell;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public Cell getOriginCell() {
        return originCell;
    }

    public Cell getTargetCell() {
        return targetCell;
    }
}
